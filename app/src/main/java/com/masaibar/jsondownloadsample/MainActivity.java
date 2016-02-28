package com.masaibar.jsondownloadsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String URL_JSON = "https://raw.githubusercontent.com/masaibar/SampleJSON/master/sample.json";
//    private static final String URL_JSON = "https://raw.githubusercontent.com/masaibar/SampleJSON/master/sample1.json";
//    private static final String URL_JSON = "https://raw.githubusercontent.com/masaibar/SampleJSON/master/sample2.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("!!!", "button pushed");
                new GetJSONAsyncTask(URL_JSON, MainActivity.this).execute();
            }
        });
    }

    private class GetJSONAsyncTask extends AsyncTask<Void, Void, String> {

        private String mUrl;
        private Activity mActivity; //画面更新不要ならいらないかも

        public GetJSONAsyncTask(String url, Activity activity) {
            mUrl = url;
            mActivity = activity;
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpsURLConnection connection;
            String result = "";
            try {
                URL url = new URL(mUrl);
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();

                int response = connection.getResponseCode();

                result = readStream(connection.getInputStream());

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("!!!", "result = " + result);

            TextView textView = (TextView) mActivity.findViewById(R.id.text_result);
            textView.setText(result);
        }

        private String readStream(InputStream inputStream) {
            if (inputStream == null) {
                return null;
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            try {
                while((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuffer.toString();
        }
    }
}
