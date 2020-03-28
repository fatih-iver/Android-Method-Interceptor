package android.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OnTouchListenerProxy implements InvocationHandler {

    private Object object;

    public static Object newInstance(Object object) {
        return java.lang.reflect.Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new OnTouchListenerProxy(object));
    }

    private OnTouchListenerProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("before method " + method.getName());
            result = method.invoke(object, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " +
                    e.getMessage());
        } finally {
            System.out.println("after method " + method.getName());
        }
        return result;
    }
}
