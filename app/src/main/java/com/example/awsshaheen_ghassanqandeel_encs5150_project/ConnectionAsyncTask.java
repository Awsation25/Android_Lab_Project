package com.example.awsshaheen_ghassanqandeel_encs5150_project;


import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    // there our api
    //https://mocki.io/v1/e0b8e1f6-1264-4f67-b1ae-3d71fee0131b

    private Context context;
    DataBaseHelper dataBaseHelper= DataBaseHelper.getInstance(context);

    public ConnectionAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        List<Task> tasks = TaskJSONParser.getObjectFromJason(s);

        tasks.forEach(task ->dataBaseHelper.insertTask(task));


    }

}
