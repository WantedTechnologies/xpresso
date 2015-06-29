package com.wantedtech.common.xpresso.web.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;

public class WebService {
	
	Object businessLogicObject;
	HttpServer server;
	
	public class HappyHttpHandler implements HttpHandler {
	    @Override
	    public void handle(HttpExchange exchange) throws IOException {
	        @SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>)exchange.getAttribute("parameters");
	        //now you can use the params
	        
	        HttpContext context = exchange.getHttpContext();
	        
	        String path = context.getPath();
	        
	        list<String> pathSplit = x.String(path).split("/");
	        
	        if (!(x.len(pathSplit) == 3 && pathSplit.get(0).equals(""))) {
	        	respond(exchange, "Error: the path to the web service has to be composed of two parts: /objectName/methodName. I got: " + path);
	        }

	        if (!classExists(pathSplit.get(1))) {
	        	respond(exchange, "Error: could not find the class " + pathSplit.get(1));
	        }
	        
	        if (!methodExists(pathSplit.get(2))) {
	        	respond(exchange, "Error: could not find the method " + pathSplit.get(2) + " in the class " + pathSplit.get(1));
	        }
	        
	        tuple2<Method,list<String>> t = getMethodWithParams(pathSplit.get(2), x.list(params.keySet()));
	        
	        if (t == null) {
	        	String err = "@ExposeAs(\"" + x.String("\"), @ExposeAs(\"").join(params.keySet()) + "\")";
		        respond(exchange, "Error: could not find the method " + pathSplit.get(2) + " in the class " + pathSplit.get(1) + " with exposed parameters " + params.keySet() + ". May be you forgot to add " + err + " annotation(s) to each parameter of the method " + pathSplit.get(2) + " of the class " + businessLogicObject.getClass().getSimpleName() +  "?" );	        	
	        } else {
	        	try {
	        		Object[] casted = castAll(params,t.key,t.value);
	        		Method m = t.key;
	        		m.setAccessible(true);
	        		if (x.len(casted) == 1) {
		        		respond(exchange,x.Json(m.invoke(businessLogicObject, casted[0])).toString());	        			
	        		} else {
	        			respond(exchange,x.Json(m.invoke(businessLogicObject, casted)).toString());
	        		}
	
	        	} catch (IllegalArgumentException e) {
	        		respond(exchange, e.toString());
	        	} catch (IllegalAccessException e) {
	        		respond(exchange, e.toString());
	        	} catch (InvocationTargetException e) {
	        		e.printStackTrace();
        			respond(exchange, e.toString());
	        	} catch (ClassCastException e) {
	        		respond(exchange, e.toString());
	        	}
	        }
	    }
	}
	
	public WebService(Object businessLogicObject, int port) throws IOException {
		this.businessLogicObject = businessLogicObject;
		String className = businessLogicObject.getClass().getSimpleName();
	    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		for (Method m : businessLogicObject.getClass().getMethods()) {
		    HttpContext context = server.createContext("/" + className + "/" + m.getName(), new HappyHttpHandler());
		    context.getFilters().add(new ParameterFilter());
		}
	    server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
	    this.server = server;
	}
	
	public WebService setMaximumConcurrentThreads(int max) {
		server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(max));
		return this;
	}
	
	public WebService start() {
		server.start();
		return this;
	}
	
	public WebService stop() {
		server.stop(10);
		return this;
	}
	
	private tuple2<Method,list<String>> getMethodWithParams(String methodName, list<String> paramNames) {
		Paranamer paranamer = new AnnotationParanamer();
		for (Method m : businessLogicObject.getClass().getMethods()) {
			if (m.getName().equals(methodName)) {
				String[] parameterNames = paranamer.lookupParameterNames(m, false);
				if(x.set(paramNames).equals(x.set(parameterNames))) return x.tuple2(m,x.list(parameterNames));				
			}

		}
		return null;
	}
	
	private boolean classExists(String className) {
		return businessLogicObject.getClass().getSimpleName().equals(className);
	}
	
	private boolean methodExists(String methodName) {
		for (Method m : businessLogicObject.getClass().getMethods()) {
			if (m.getName().equals(methodName)) return true;
		}
		return false;
	}
	
	private void respond(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
	
	private Object[] castAll(Map<String,Object> arguments, Method m, list<String> namedParams) throws ClassCastException{
		Object[] result = new Object[x.len(namedParams)];
		for (int i : x.count(namedParams)) {
			try {
				result[i] = cast(arguments.get(namedParams.get(i)),m.getParameterTypes()[i]);	
			} catch (ClassCastException e) {
				throw e;
			}
		}
		return result;
	}
	
	private Object cast(Object value, Class<?> cls) throws ClassCastException{
		
		if (cls == String.class) {
			return value.toString();
		}
		
		if (cls == boolean.class) {
			try {
				return (boolean)Boolean.parseBoolean(value.toString());
			} catch (Exception e0) {
				return value.toString().equals("0") || value.toString().equals("") ? false : true;
			}
		}
		
		if (cls == Boolean.class) {
			try {
				return (Boolean)Boolean.parseBoolean(value.toString());
			} catch (Exception e0) {
				return value.toString().equals("0") || value.toString().equals("") ? false : true;
			}
		}
		
		if (cls == int.class) {
			try {
				return (int)Integer.parseInt(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Integer.");
			}
		}
		
		if (cls == Integer.class) {
			try {
				return (Integer)Integer.parseInt(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Integer.");
			}
		}
		
		if (cls == float.class) {
			try {
				return (float)Float.parseFloat(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Float.");
			}
		}
		
		if (cls == Float.class) {
			try {
				return (Float)Float.parseFloat(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Float.");
			}
		}
		
		if (cls == double.class) {
			try {
				return (double)Double.parseDouble(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Double.");
			}
		}
		
		if (cls == Double.class) {
			try {
				return (Double)Double.parseDouble(value.toString());
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as Double.");
			}
		}
		
		if (cls == String[].class) {
			String[] result = new String[((List<?>)value).size()];
			for (int i : x.count(((List<?>)value))) {
				result[i] = ((List<?>)value).toString();
			}
			return result;
		}
				
		if (cls == boolean[].class) {
			try {
				boolean[] result = new boolean[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (boolean)Boolean.parseBoolean(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as boolean[]");
			}
		}
		
		if (cls == Boolean[].class) {
			try {
				Boolean[] result = new Boolean[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (Boolean)Boolean.parseBoolean(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as boolean[]");
			}
		}
		
		if (cls == int[].class) {
			try {
				int[] result = new int[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (int)Integer.parseInt(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as int[]");
			}
		}
		
		if (cls == Integer[].class) {
			try {
				Integer[] result = new Integer[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (Integer)Integer.parseInt(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as int[]");
			}
		}
		
		if (cls == float[].class) {
			try {
				float[] result = new float[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (float)Float.parseFloat(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as float[]");
			}
		}
		
		if (cls == Float[].class) {
			try {
				Float[] result = new Float[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (Float)Float.parseFloat(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as float[]");
			}
		}
		
		if (cls == double[].class) {
			try {
				double[] result = new double[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (double)Double.parseDouble(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as double[]");
			}
		}
		
		if (cls == Double[].class) {
			try {
				Double[] result = new Double[((List<?>)value).size()];
				for (int i : x.count(((List<?>)value))) {
					result[i] = (Double)Double.parseDouble(((List<?>)value).get(i).toString());
				}
				return result;
			} catch (Exception e0) {
				throw new ClassCastException("Could not cast " + value.toString() + " as double[]");
			}
		}
		
		throw new ClassCastException("xpresso WebService only supports standard Java types as types of the web service parameters, inlcuding arrays of standard Java classes. The type " + cls.toString() + " of the input value " + value + " is not a standard Java type.");
	}
	
}
