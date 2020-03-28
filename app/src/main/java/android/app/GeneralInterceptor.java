package android.app;


import android.util.Log;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class GeneralInterceptor {

    public static final String TAG = GeneralInterceptor.class.getSimpleName();

    @RuntimeType
    public void intercept(@SuperCall Callable<?> callable, @Origin Method method, @AllArguments Object[] allArguments) {
        Log.d(TAG, callable.toString() + " " + method.toString() + " " + allArguments.toString());
    }
}