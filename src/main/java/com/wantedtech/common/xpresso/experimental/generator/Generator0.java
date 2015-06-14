package com.wantedtech.common.xpresso.experimental.generator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuple;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public abstract class Generator0<T> implements Iterable<T>, AutoCloseable {

	static ThreadGroup THREAD_GROUP;

	Thread producer;
	private boolean hasFinished;
	private T nextItem;
	private boolean nextItemAvailable;
	private boolean isStillNeeded = true;
	Generator0<T> thisGeneratoObject = this;
	
	public Object[] params;
	
	
	public Generator0<T> input(Object... params){
		this.params = params;
		return this;
	}
	
	public Generator0(boolean bool){
	}
	
	public Generator0(){
	}
	
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
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		
	}
	
	protected synchronized void yield(T element) {
		if (!isStillNeeded) {
			throw new NoSuchElementException();
		}
		nextItem = element;
		setAvaliable(true);
		//x.print("gggg");
		while (nextItemAvailable && isStillNeeded) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			//x.print(nextItemAvailable, nextItem, producer.isAlive());
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
						Class<?>[] classes = new Class<?>[x.len(params)];
						
						for (int idx : x.count(params)) {
							Class<?> cls = params[idx].getClass();
							if (cls.equals(Integer.class)) {
								cls = int.class;
							} else if (cls.equals(Float.class)) {
								cls = float.class;
							} else if (cls.equals(Double.class)) {
								cls = double.class;
							} else if (cls.equals(Boolean.class)) {
								cls = boolean.class;
							}
							classes[idx] = cls;
						}
						Method m = thisGeneratoObject.getClass().getMethod("generator", classes);
						m.setAccessible(true);
						m.invoke(thisGeneratoObject, params);

				} catch (Exception e) {
					//e.printStackTrace();
					isStillNeeded = false;
				}
				//x.print("finished");
				hasFinished = true;
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
}
