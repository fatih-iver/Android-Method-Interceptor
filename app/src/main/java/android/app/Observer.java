package android.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.android.dx.stock.ProxyBuilder;
import com.example.R;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.android.AndroidClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.security.Key;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Observer {

    public static final String TAG = Observer.class.getSimpleName();

    @SuppressLint("ClickableViewAccessibility")
    public static void start(){

        Application application = null;

        try {
            application = (Application) Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication").invoke(null, (Object[]) null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.d(TAG, application.toString());


        try {

            TestClass proxy = ProxyBuilder.forClass(TestClass.class)
                    .dexCache(application.getCodeCacheDir())
                    .handler(new OInvocationHandler(new TestClass()))
                    .build();

            proxy.testMethod();

            Class classClass = Class.class;

            Field accessFlagsField = classClass.getDeclaredField("accessFlags");

            accessFlagsField.setAccessible(true);

            Class testFinalClassClass = TestFinalClass.class;

            accessFlagsField.setInt(testFinalClassClass, testFinalClassClass.getModifiers() & ~Modifier.FINAL);

            Class<? extends TestFinalClass> dynamicTestFinalClass = new ByteBuddy()
                    .subclass(TestFinalClass.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new GeneralInterceptor()).andThen(SuperMethodCall.INSTANCE))
                    .make()
                    .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                    .getLoaded();

            dynamicTestFinalClass.newInstance().testFinalClassMethod();


            Class motionEventClass = MotionEvent.class;

            accessFlagsField.setInt(motionEventClass, motionEventClass.getModifiers() & ~Modifier.FINAL);

            Class<? extends MotionEvent> dynamicMotionEvent = new ByteBuddy()
                    .subclass(MotionEvent.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new GeneralInterceptor()).andThen(SuperMethodCall.INSTANCE))
                    .make()
                    .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                    .getLoaded();


            Class keyEventClass = KeyEvent.class;

            int keyEventClassAccessFields = (int) accessFlagsField.get(keyEventClass);

            accessFlagsField.setInt(keyEventClass, keyEventClass.getModifiers() & ~Modifier.FINAL);

            Class<? extends KeyEvent> dynamicKeyEvent = new ByteBuddy()
                    .subclass(KeyEvent.class)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(new GeneralInterceptor()).andThen(SuperMethodCall.INSTANCE))
                    .make()
                    .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                    .getLoaded();

            Class activityThreadClass = Class.forName("android.app.ActivityThread");

            Field sCurrentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");

            sCurrentActivityThreadField.setAccessible(true);

            Object activityThreadObject = sCurrentActivityThreadField.get(new Object());

            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");

            mInstrumentationField.setAccessible(true);

            Instrumentation instrumentation = (Instrumentation) mInstrumentationField.get(activityThreadObject);

            //Object instrumentationObject = InstrumentationProxy.newInstance(instrumentation);

            OInstrumentation oInstrumentation = new OInstrumentation(instrumentation);

            Instrumentation instrumentation1 = (Instrumentation) oInstrumentation;

            mInstrumentationField.set(activityThreadObject, instrumentation1);


            Log.d(TAG, instrumentation.toString());

            View rootView = LayoutInflater.from(application).inflate(R.layout.activity_main, null);

            Class viewClass = Class.forName(View.class.getName());

            Field mListenerInfoField = viewClass.getDeclaredField("mListenerInfo");

            mListenerInfoField.setAccessible(true);

            Class ListenerInfoClass = Class.forName(View.class.getName() + "$" + "ListenerInfo");

            Field mOnTouchListenerField = ListenerInfoClass.getDeclaredField("mOnTouchListener");

            mOnTouchListenerField.setAccessible(true);


            View.OnTouchListener onTouchListener;

            if(mListenerInfoField.get(rootView) == null || mOnTouchListenerField.get(mListenerInfoField.get(rootView)) == null){
                rootView.setOnTouchListener((v, event) -> false);
            }

            onTouchListener = (View.OnTouchListener) mOnTouchListenerField.get(mListenerInfoField.get(rootView));

            View.OnTouchListener onTouchListener1 = (View.OnTouchListener) OnTouchListenerProxy.newInstance(onTouchListener);

            onTouchListener1.onTouch(rootView, MotionEvent.obtain(0,0,0,0,0,0));
        }
        catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
        }

        Class<?> dynamicType1 = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                .getLoaded();

        Class<? extends java.util.function.Function> dynamicType2 = new ByteBuddy()
                .subclass(java.util.function.Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                .getLoaded();

        Class<?> dynamicTestClass = new ByteBuddy()
                .subclass(TestClass.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(new GeneralInterceptor()).andThen(SuperMethodCall.INSTANCE))
                .make()
                .load(application.getClassLoader(), new AndroidClassLoadingStrategy.Wrapping(application.getCodeCacheDir()))
                .getLoaded();

        try {
            ((TestClass) dynamicTestClass.newInstance()).testMethod();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }


        try {
            assertThat(dynamicType1.newInstance().toString(), is("Hello World!"));
            assertThat((String) dynamicType2.newInstance().apply("Byte Buddy"), is("Hello from Byte Buddy"));

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            Log.d("abc", e.getMessage());
        }



    }
}
