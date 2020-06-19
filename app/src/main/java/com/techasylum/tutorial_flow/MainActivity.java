package com.techasylum.tutorial_flow;
//asynctask impelemetation tutorial
// weekreference uses


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        txtResult=findViewById(R.id.txtResult);
    }


    public void startAsyncTask(View v) {

        ExampleAsyncTask task=new ExampleAsyncTask(this);
        task.execute(10);

    }

    public  class ExampleAsyncTask  extends AsyncTask<Integer,Integer,String>{
        private WeakReference<MainActivity> activityWeakReference;
        ExampleAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {

            for(int i=0;i<integers[0];i++ ){

                publishProgress((i*100)/integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "finished";
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Toast.makeText(activity, "result = "+s, Toast.LENGTH_SHORT).show();
            activity.progressBar.setProgress(0);
            activity.progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setProgress(values[0]);
            activity.txtResult.setText("Progress "+values[0]);
            Toast.makeText(activity, "update"+values[0], Toast.LENGTH_SHORT).show();
        }
    }
}
