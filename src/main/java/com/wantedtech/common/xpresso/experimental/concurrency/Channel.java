package com.wantedtech.common.xpresso.experimental.concurrency;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.ObjectFactory;
import com.wantedtech.common.xpresso.types.set;

public class Channel<T> implements Iterable<T>{

	private Class<T> type;
	private Queue<T> values = new LinkedList<T>();
	private int bufferSize = 0;
	private set<Goer<T>> registeredGoers = x.set();
	
	boolean readerReady = false;
	boolean valueReady = false;
	boolean putFinished = false;
	boolean closed = false;
	T value;
	
	public Channel(Class<T> type) {
		this.type = type;
	}
	
	public Channel(Class<T> type, int bufferSize) {
		this.type = type;
		this.bufferSize = bufferSize;
	}
	
	public Class<T> getType() {
		return type;
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	
	public ReceiveOnlyChannel<T> receiveOnly() {
		return new ReceiveOnlyChannel<T>(this);
	}
	
	public SendOnlyChannel<T> sendOnly() {
		return new SendOnlyChannel<T>(this);
	}
	
	public synchronized void send(T value) {
		if (closed) {
			throw new SendToClosedChannelException("Cannot send values to a closed channel.");
		}
		while (bufferSize == 0 && valueReady) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		putFinished = false;
		valueReady = true;
		this.value = value;
		notifyAll();
		while ((bufferSize == 0 && !readerReady) || (bufferSize > 0 && values.size() == bufferSize)) {
			try {
				wait();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
		if(bufferSize > 0) {
			values.add(value);	
		}
		putFinished = true;
		notifyAll();
	}
	
	public synchronized T receive() {
		readerReady = true;
		notifyAll();
		while ((bufferSize > 0 && values.size() == 0) || (bufferSize == 0 && !valueReady)) {
			if (closed) {
				return ObjectFactory.createValueOfType(type);
			}
			try {
				wait();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
		if (bufferSize > 0) {
			notifyAll();
			return values.remove();			
		} else {
			while (bufferSize == 0 && !putFinished) {
				try {
					wait();
				} catch (InterruptedException e) {

				}
			}
			readerReady = false;
			valueReady = false;
			notifyAll();
			return value;
		}
	}
	
	public synchronized void registerGoer(Goer<T> g) {
		registeredGoers.put(g);
		notifyAll();
	}

	public void close() {
		closed = true;
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				while ((bufferSize > 0 && values.size() == 0) || (bufferSize == 0 && !valueReady)) {
					boolean allFinished = true;
					for (Goer<T> g : registeredGoers) {
						if (!g.finished()) {
							allFinished = false;
							try {
								wait();
							} catch (InterruptedException e) {

							} catch (IllegalMonitorStateException e1) {
								
							}
						}
					}
					if (allFinished || closed) {
						return false;
					}
				}
				return (bufferSize > 0 && values.size() != 0) || (bufferSize == 0 && valueReady) || false;
			}
			
			@Override
			public T next() {
				return receive();
			}			
		};
	}
}
