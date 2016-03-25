package xyz.mcomella.threadannotationstest;

import android.os.AsyncTask;
import android.support.annotation.WorkerThread;

import org.json.JSONArray;
import org.json.JSONObject;

public class AnnotationsRealWorldExample {
    private JSONObject appState;

    class TestAsyncTask extends AsyncTask<Integer, Integer, Integer> {

    }

    @WorkerThread
    void storeJSONObject(JSONObject obj) {
        /* ... */
    }

    void writeAppStateToDisk()
}
