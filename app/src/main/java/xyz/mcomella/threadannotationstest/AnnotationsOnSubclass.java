package xyz.mcomella.threadannotationstest;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

class AnnotationsOnSubclass {
    @UiThread
    void onUiThread() {}

    @UiThread
    void onUiThreadUntilOverriden() {}

    void annotatedInSubclass() {}

    static class ActualSubclass extends AnnotationsOnSubclass {
        @Override
        void onUiThread() {}

        @WorkerThread
        @Override
        void onUiThreadUntilOverriden() {}

        @UiThread
        @Override
        void annotatedInSubclass() {}

        @WorkerThread
        void testSubclassMethodAnnotations() {
            // The subclass correctly inherits the thread annotation
            // even if the annotated method is overridden.
            onUiThread(); // correct: displays warning.

            // However, the annotation cannot be overridden by the subclass.
            onUiThreadUntilOverriden(); // arguable: displays warning.

            // But if the annotation was never declared, the subclass can assign one.
            annotatedInSubclass(); // correct: displays warning.
        }
    }
}
