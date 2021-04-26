package com.example.longrunningtasksimulationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    TextView ProgressOut;
    private Toast mToast;
    ProgressBar progressBar;
    public int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = (Button) findViewById(R.id.button);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);

        ProgressOut = (TextView) findViewById(R.id.ProgressOut);
        ProgressOut.setVisibility(View.INVISIBLE);

        button.setOnClickListener((v) -> {
            new Task().execute();
        });

        button1.setOnClickListener((v) -> {
            ToastIt("Button 1 pressed");
        });
        button2.setOnClickListener((v) -> {
            ToastIt("Button 2 pressed");
        });

    }

    class Task extends AsyncTask<Void, Integer, String> {
        EditText numInput = findViewById(R.id.editText);
        Long TimeM = Long.valueOf(Integer.parseInt(numInput.getText().toString()));

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressOut.setVisibility((View.VISIBLE));
        }

        @Override
        protected String doInBackground(Void... voids) {

            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                        try {
                            Thread.sleep(TimeM);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            for (int i = 0; i <= 100; i = i + 1) {
               try {
                   Thread.sleep(TimeM);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               publishProgress(i);

           }
           return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... Values) {
            super.onProgressUpdate(Values);

            ProgressOut.setText(Values[0].toString() + "% finished");
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ToastIt("The result of the background task is: " + result);
        }

    }
    void ToastIt(String message){
        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this,message,Toast.LENGTH_LONG);
        mToast.show();
    }
}