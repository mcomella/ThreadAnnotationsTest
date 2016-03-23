package xyz.mcomella.threadannotationstest;

import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

class AnnotationsOnAsyncTask {
    @UiThread
    void onUiThread() {}

    @WorkerThread
    void onWorkerThread() {}

    class Task extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            onUiThread(); // correct: displays warning.
            onWorkerThread(); // correct: no warning.
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            onUiThread(); // correct: no warning.
            onWorkerThread(); // correct: displays warning.
        }
    }
}
