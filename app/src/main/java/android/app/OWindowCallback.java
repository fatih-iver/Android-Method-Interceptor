package android.app;

import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ContentFrameLayout;

import com.android.dx.stock.ProxyBuilder;

import java.io.IOException;

public class OWindowCallback implements Window.Callback {

    private static final String TAG = OWindowCallback.class.getSimpleName();

    private Window window;
    private Window.Callback callback;

    public OWindowCallback(Window window) {
        this.window = window;
        this.callback = window.getCallback();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent");

        return callback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyShortcutEvent");
        return callback.dispatchKeyShortcutEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent");

        /*
        MotionEvent motionEventProxy = null;
        try {
            motionEventProxy = ProxyBuilder.forClass(MotionEvent.class)
                    .dexCache(window.getContext().getCodeCacheDir())
                    .handler(new OInvocationHandler(event))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return callback.dispatchTouchEvent(motionEventProxy == null ? event : motionEventProxy);
        */
        return callback.dispatchTouchEvent(event);

    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTrackballEvent");
        return callback.dispatchTrackballEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        Log.d(TAG, "dispatchGenericMotionEvent");
        return callback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "dispatchPopulateAccessibilityEvent");
        return callback.dispatchPopulateAccessibilityEvent(event);
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        Log.d(TAG, "onCreatePanelView");
        return callback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        Log.d(TAG, "onCreatePanelMenu");
        return callback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, @Nullable View view, @NonNull Menu menu) {
        Log.d(TAG, "onPreparePanel");
        return callback.onPreparePanel(featureId, view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, @NonNull Menu menu) {
        Log.d(TAG, "onMenuOpened");
        return callback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, @NonNull MenuItem item) {
        Log.d(TAG, "onMenuItemSelected");
        return callback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        Log.d(TAG, "onWindowAttributesChanged");
        callback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        Log.d(TAG, "onContentChanged");
        callback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d(TAG, "onWindowFocusChanged");
        callback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        callback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow");
        callback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, @NonNull Menu menu) {
        Log.d(TAG, "onPanelClosed");
        callback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        Log.d(TAG, "onSearchRequested");
        return callback.onSearchRequested();
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        Log.d(TAG, "onSearchRequested");
        return callback.onSearchRequested(searchEvent);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        Log.d(TAG, "onWindowStartingActionMode");

        return this.callback.onWindowStartingActionMode(callback);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        Log.d(TAG, "onWindowStartingActionMode");
        return this.callback.onWindowStartingActionMode(callback, type);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        Log.d(TAG, "onActionModeStarted");
        callback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        Log.d(TAG, "onActionModeFinished");
        callback.onActionModeFinished(mode);
    }
}
