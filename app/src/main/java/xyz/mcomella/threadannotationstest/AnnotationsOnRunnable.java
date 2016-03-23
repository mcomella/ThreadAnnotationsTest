package xyz.mcomella.threadannotationstest;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

public class AnnotationsOnRunnable {
    @WorkerThread
    void onWorkerThread() {}

    interface UiThreadRunnable extends Runnable {
        @UiThread
        @Override
        void run();
    }

    // TODO: test putting onto handler threads & such
    void testRunnableAnnotations(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The UIThread is not inferred.
                onWorkerThread(); // UNEXPECTED: no warning
            }
        });

        activity.runOnUiThread(new UiThreadRunnable() {
            @Override
            public void run() {
                // But we can annotate our own interface to
                // get the inference I'd expect.
                onWorkerThread(); // correct: displays warning.
            }
        });
    }
}
