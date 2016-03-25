package xyz.mcomella.threadannotationstest;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnnotationsOnRunnable {
    @WorkerThread
    void onWorkerThread() {}

    interface UiThreadRunnable extends Runnable {
        @UiThread
        @Override
        void run();
    }

    void testUiThreadRunnableAnnotations(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // The UIThread is not inferred.
                onWorkerThread(); // UNEXPECTED: no warning
            }
        });

        activity.runOnUiThread(new Runnable() {
            @UiThread // Redundant to `runOnUiThread`
            @Override
            public void run() {
                // We can explicitly annotate.
                onWorkerThread(); // correct: displays warning.
            }
        });

        activity.runOnUiThread(new UiThreadRunnable() { // `UiThreadRunnable` redundant
            @Override                                   //  to`run `runOnUiThread`
            public void run() {
                // Or use a type that contains the annotation.
                onWorkerThread(); // correct: displays warning.
            }
        });
    }

    @UiThread
    void onUiThread() {}

    void testWorkerThreadRunnableAnnotations() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // Similarly, a worker thread is not inferred.
                onUiThread(); // UNEXPECTED: no warning.
            }
        })
    }
}
