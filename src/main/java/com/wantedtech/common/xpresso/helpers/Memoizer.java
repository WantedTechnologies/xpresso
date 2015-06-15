package com.wantedtech.common.xpresso.helpers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Memoizer implements InvocationHandler {
  public static Object memoize(Object object) {
      return Proxy.newProxyInstance(
        object.getClass().getClassLoader(),
        object.getClass().getInterfaces(),
        new Memoizer(object)
      );
  }

  private Object object;
  private Map caches = new HashMap();

  private Memoizer(Object object) {
      this.object = object;
  }

  public Object invoke(Object proxy, Method method,
  Object[] args) throws Throwable {

      if (method.getReturnType().equals(Void.TYPE)) {
          // Don't cache void methods
          return invoke(method, args);
      } else {
          Map cache    = getCache(method);
          List key     = Arrays.asList(args);
          Object value = cache.get(key);

          if (value == null && !cache.containsKey(key)) {
              value = invoke(method, args);
              cache.put(key, value);
          }
          return value;
      }
  }

  private Object invoke(Method method, Object[] args)
  throws Throwable {
      try {
          return method.invoke(object, args);
      } catch (InvocationTargetException e) {
          throw e.getTargetException();
      }
  }

  private synchronized Map getCache(Method m) {
      Map cache = (Map) caches.get(m);
      if (cache == null) {
          cache = Collections.synchronizedMap(
            new HashMap()
          );
          caches.put(m, cache);
      }
      return cache;
  }
}