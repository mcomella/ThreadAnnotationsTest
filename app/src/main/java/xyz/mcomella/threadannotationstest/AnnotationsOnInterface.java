package xyz.mcomella.threadannotationstest;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

class AnnotationsOnInterface {
    @UiThread
    void onUiThread() {}

    @WorkerThread
    void onWorkerThread() {}

    interface Methods {
        @UiThread
        void interfaceOnUiThread();
    }

    class MethodsImpl implements Methods {
        @Override
        public void interfaceOnUiThread() {
            onUiThread(); // correct: no warning.
            onWorkerThread(); // correct: displays warning.
        }
    }

    @WorkerThread
    void testInterfaceAnnotations(Methods iface, MethodsImpl impl) {
        // The @UiThread annotation is correctly inherited for a instance
        // of both the interface and the implementing class when the
        // annotation is declared in the interface.
        iface.interfaceOnUiThread(); // correct: displays warning.
        impl.interfaceOnUiThread(); // correct: displays warning.
    }

    @UiThread
    interface AllUiThread {
        void interfaceOnUiThread();
    }

    class AllUiThreadImpl implements AllUiThread {
        @Override
        public void interfaceOnUiThread() {
            // The @UiThread annotation can be placed
            // on a class to annotate all methods.
            onWorkerThread(); // correct: displays warning.
        }
    }

}
