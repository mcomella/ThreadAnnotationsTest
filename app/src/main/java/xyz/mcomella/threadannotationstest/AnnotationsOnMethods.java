package xyz.mcomella.threadannotationstest;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.widget.TextView;

public class AnnotationsOnMethods {
    @UiThread
    void onUiThread() {}

    @WorkerThread
    void onWorkerThread() {}

    void unannotatedCallsUiThread() {
        onUiThread();
    }

    @WorkerThread
    void testMethodAnnotations(TextView textView) {
        textView.setText(""); // correct: displays warning.
        onUiThread(); // correct: displays warning.

        // UNEXPECTED: the following method does not display a warning.
        // I expect it to because it calls to a @UiThread annotated method.
        unannotatedCallsUiThread();
    }
}
