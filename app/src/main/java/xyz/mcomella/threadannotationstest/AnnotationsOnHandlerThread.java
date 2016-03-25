package xyz.mcomella.threadannotationstest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

public class AnnotationsOnHandlerThread {
    @UiThread
    void onUiThread() {}

    @WorkerThread
    void onWorkerThread() {}

    void testAnnotationsOnHandlerThread() {
        new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                // Main thread looper is not inferred.
                onWorkerThread(); // UNEXPECTED: no warning.
            }
        };

        new Handler(Looper.getMainLooper()) {
            @UiThread // redundant to `Looper.getMainLooper()`
            @Override
            public void handleMessage(Message msg) {
                // But we can be explicit, however.
                onWorkerThread(); // expected: displays warning.
            }
        };

        HandlerThread handlerThread = new HandlerThread("onWorkerThread");
        new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // Similarly, the worker thread is not inferred.
                // But we could be explicit if we wanted to.
                onUiThread(); // UNEXPECTED: no warning.
            }
        }

        // We can also create a Handler subclass with the Override.
        // However, the annotation in that class is redundant to
        // the looper passed in.
        new UiThreadHandler(Looper.getMainLooper());
    }

    class UiThreadHandler extends Handler {
        public UiThreadHandler(Looper looper) {
            super(looper);
        }

        @UiThread
        @Override
        public void handleMessage(Message msg) {}
    }
}
