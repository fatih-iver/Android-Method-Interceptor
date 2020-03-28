package android.app;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OInvocationHandler implements InvocationHandler {

    public static final String TAG = OInvocationHandler.class.getSimpleName();

    private Object object;

    public OInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        Log.d(TAG, "before method: " + method.getName());
        try {
            result = method.invoke(object, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }
        Log.d(TAG, "after method: " + method.getName());
        return result;
    }
}
