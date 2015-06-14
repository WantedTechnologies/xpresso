package com.wantedtech.common.xpresso.experimental.generator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.tuple;

public class Generator<T> implements Iterable<T>, AutoCloseable {

	static ThreadGroup THREAD_GROUP;

	Thread producer;
	private boolean hasFinished;
	private T nextItem;
	private boolean nextItemAvailable;
	private boolean isStillNeeded = true;
	Generator<T> thisGeneratoObject = this;
	
	public Object[] params;
	
	
	public Generator<T> input(Object... params){
		this.params = params;
		return this;
	}
	
	public Generator(boolean bool){
	}
	
	public Generator(){
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			public boolean hasNext() {
				if (producer == null) {
					startProducer();
				}
				waitForNext();
				return !hasFinished;
			}

			public T next() {
				if (producer == null) {
					startProducer();
					waitForNext();
				}
				if (hasFinished) {
					throw new NoSuchElementException();
				}
				nextItemAvailable = false;
				return nextItem;
			}

			private void waitForNext() {
				
				while (isStillNeeded) {
					x.print("still running");
					if (nextItemAvailable || hasFinished) {
						break;
					}
				}
				
			}
		};
	}

	protected void yield(T element) {
		x.print("yields");
		if (!isStillNeeded) {
			throw new NoSuchElementException();
		}
		nextItem = element;
		nextItemAvailable = true;
		while (nextItemAvailable && isStillNeeded) {
			x.print("still running2");
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
						
						for (tuple item : x.enumerate(params)) {
							item.name("idx","obj");
							Class<?> cls = params[item.get("idx", Integer.class)].getClass();
							if (cls.equals(Integer.class)) {
								cls = int.class;
							} else if (cls.equals(Float.class)) {
								cls = float.class;
							} else if (cls.equals(Double.class)) {
								cls = double.class;
							} else if (cls.equals(Boolean.class)) {
								cls = boolean.class;
							}
							classes[item.get("idx", Integer.class)] = cls;
						}
						Method m = thisGeneratoObject.getClass().getMethod("generator", classes);
						m.setAccessible(true);
						m.invoke(thisGeneratoObject, params);

				} catch (Exception e) {
					//e.printStackTrace();
					isStillNeeded = false;
				}
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
