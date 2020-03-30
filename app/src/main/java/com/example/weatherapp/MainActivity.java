package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_API ="https://samples.openweathermap.org/data/2.5/weather?q=";
    private Button button;
   private  String api=null;
    ProgressBar progressBar;
    private static final String   str2 ="&appid=78963329fe701d6fa39e79649a37f310";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         button = (Button)findViewById(R.id.bt);
        progressBar= (ProgressBar)findViewById(R.id.prog);
        progressBar.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) findViewById(R.id.edit);
                String name = editText.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                     api = WEATHER_API + name + str2;

                }
                TextView textView = (TextView) findViewById(R.id.text);
                textView.setText("");
                progressBar.setVisibility(View.VISIBLE);
                Weather we = new Weather();
                we.execute(api);
            }
        });

    }
    private class Weather extends AsyncTask<String,Void,Details>
    {

        @Override
        protected Details doInBackground(String... strings) {
            URL url=null;
            String jsonResponse="ggg";
            try
            {
                url = new URL(strings[0]);
                Log.i("abcd",url.toString());
            }
            catch (Exception e)
            {
                Log.e("dasd","Dfsf",e);
            }
            try
            {
                Log.i("a2",jsonResponse);
                jsonResponse = makeRequest(url);
                Log.i("a2",jsonResponse);

            }
            catch(Exception f)
            {
                Log.e("dasd","Dfsf",f);
            }
           Details details =  extractJson(jsonResponse);
            return details;
        }
        public String makeRequest(URL url) throws IOException
        {
            HttpURLConnection httpURLConnection=null;
            InputStream inputStream=null;
            String jsonresponse= "";
            try
            {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode()==200)
                {
                    inputStream = httpURLConnection.getInputStream();
                    Log.i("a3",inputStream.toString());
                    jsonresponse = getJson(inputStream);
                }else{
                    Log.i("a4",httpURLConnection.getResponseMessage());
                }
            }
            catch (Exception e)
            {
                Log.e("dasd","Dfsf",e);
            }
            finally {
                if (httpURLConnection!= null) {
                    httpURLConnection.disconnect();
                }
                if (inputStream != null) {

                    inputStream.close();
                }
            }
            Log.i("a","Dfsf");
            return jsonresponse;
        }
        private String getJson(InputStream inputStream) throws IOException
        {
            StringBuilder stringBuilder = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null)
            {
                stringBuilder.append(line);
                line= bufferedReader.readLine();
            }
            return stringBuilder.toString();
        }
        public Details extractJson(String json)
        {
            Details details=new Details();
            try
            {
                JSONObject root = new JSONObject(json);
                JSONArray weather = root.getJSONArray("weather");
                JSONObject ob = weather.getJSONObject(0);
                String description = ob.getString("description");
                Log.i(description,description);
                JSONObject ob2 = root.getJSONObject("main");
                double temp = ob2.getDouble("temp");
                double humidity = ob2.getDouble("humidity");
                JSONObject ob3 = root.getJSONObject("wind");
                double windspeed = ob3.getDouble("speed");
                double wind_degree = ob3.getDouble("deg");
                details.setDescription(description);
                details.setTemp(temp);
                details.setWind_speed(windspeed);
                details.setWind_degree(wind_degree);
                details.setHumidity(humidity);
            }
            catch(Exception e)
            {
                Log.e("dasd","extract",e);
            }
            return details;
        }
        public void onPostExecute(Details details)
        {
            progressBar.setVisibility(View.INVISIBLE);
            updateUI(details);
        }
    }
    public void updateUI(Details details)
    {
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("temperature:"+details.getTemp()+"\n");
        textView.append("Description:"+details.getDescription()+"\n");
        textView.append("humidity:"+details.getHumidity()+"\n");
        textView.append("wind speed:"+details.getWind_speed()+"\n");
        textView.append("wind degree:"+details.getWind_degree()+"\n");
    }
}
