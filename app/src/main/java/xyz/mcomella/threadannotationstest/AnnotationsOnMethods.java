package xyz.mcomella.threadannotationstest;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.widget.TextView;

public class AnnotationsOnMethods {
    @UiThread
    void onUiThread() {}

    void unannotatedCallsUiThread() {
        onUiThread();
    }

    @WorkerThread
    void testMethodAnnotations(TextView textView) {
        textView.setText(""); // correct: displays warning.
        onUiThread(); // correct: displays warning.

        // UNEXPECTED: the following method does not display a warning.
        // I expect it to because it calls a @UiThread annotated method.
        //
        // We can explicitly annotate this method too, but this method
        // should only be on the UiThread because it calls a method
        // annotated for the UiThread and thus the annotation would be
        // redundant.
        unannotatedCallsUiThread();
    }
}
