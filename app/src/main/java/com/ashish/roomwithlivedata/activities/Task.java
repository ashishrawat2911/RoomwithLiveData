package com.ashish.roomwithlivedata.activities;

import android.os.AsyncTask;

public class Task extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
