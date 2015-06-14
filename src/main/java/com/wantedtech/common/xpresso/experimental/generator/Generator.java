package com.wantedtech.common.xpresso.experimental.generator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.wantedtech.common.xpresso.x;

public abstract class Generator<T> implements Iterable<T>, AutoCloseable {

	static ThreadGroup THREAD_GROUP;

	Thread producer;
	private boolean hasFinished;
	private T nextItem;
	private boolean nextItemAvailable;
	private boolean isStillNeeded = true;
	
	public Generator(boolean bool){
	}
	
	public Generator(){
	}
	
	public abstract void generate();
	
	private synchronized void setAvaliable(boolean flag) {
		nextItemAvailable = flag;
		notifyAll();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			public boolean hasNext() {
				//x.print("has_in1");
				if (producer == null) {
					startProducer();
				}
				//x.print("has_in2");
				waitForNext();
				//x.print("has_out" + hasFinished);
				return !hasFinished;
			}

			public T next() {
				//x.print("next_in");
				if (producer == null) {
					startProducer();
					waitForNext();
				}
				if (hasFinished) {
					throw new NoSuchElementException();
				}
				setAvaliable(false);
				//x.print("next_out");
				return nextItem;
			}
		};
	}

	private synchronized void waitForNext() {
		while (isStillNeeded && !nextItemAvailable && !hasFinished) {
			//x.print("I wait!");
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}
		
	}
	
	protected synchronized void yield(T element) {
		if (!isStillNeeded) {
			throw new NoSuchElementException();
		}
		nextItem = element;
		setAvaliable(true);
		while (nextItemAvailable && isStillNeeded) {
			//x.print("I wait2!");
			try {
				wait();
			} catch (InterruptedException e) {
				
			}
		} 
	}

	private void startProducer() {
		x.assertTrue(producer == null);
		
		if (THREAD_GROUP == null)
			THREAD_GROUP = new ThreadGroup("generatorfunctions");
		
		producer = new Thread(THREAD_GROUP, new Runnable() {
			@Override
			public void run() {
				try {
					//x.print("starting!");
					generate();
					//x.print("finished!");

				} catch (Exception e) {
					isStillNeeded = false;
				} finally {
					//x.print("finally");
					hasFinished = true;	
				}

			}
		});
		producer.setDaemon(true);
		producer.start();
	}
	
	@Override
	public void close() {
		try {
			
			isStillNeeded = false;
			producer = null;
			
		} catch (Exception e) {
			
		}
	}
	
	@Override
	protected void finalize() {
		try {
			//x.print("I finlize!");
			isStillNeeded = false;
		} catch (Exception e) {

		}
	}
	
}
