package com.example.dagna.together.onlineDatabase;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.dagna.together.RegisterActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dagna on 11/04/2016.
 */
public class AddEvent extends AsyncTask<String, Void, String>
{
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public AddEvent(AsyncResponse delegate){
        this.delegate = delegate;
    }

    String add_user_url;
    @Override
    protected void onPreExecute() {
        add_user_url="https://omega.aizio.net:5678/add_event.php";
    }

    @Override
    protected String doInBackground(String... args) {
        String name, category, description, country, city, street, user;
        name=args[0];
        category=args[1];
        description=args[2];
        country=args[3];
        city=args[4];
        street=args[5];
        user=args[6];

        try {
            URL url = new URL(add_user_url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data_string = URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+"&"+
                    URLEncoder.encode("category", "UTF-8")+"="+URLEncoder.encode(category, "UTF-8")+"&"+
                    URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode(description, "UTF-8")+"&"+
                    URLEncoder.encode("country", "UTF-8")+"="+URLEncoder.encode(country, "UTF-8")+"&"+
                    URLEncoder.encode("city", "UTF-8")+"="+URLEncoder.encode(city, "UTF-8")+"&"+
                    URLEncoder.encode("street", "UTF-8")+"="+URLEncoder.encode(street, "UTF-8")+"&"+
                    URLEncoder.encode("user", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8");

            bufferWriter.write(data_string);
            bufferWriter.flush();
            bufferWriter.close();
            outputStream.close();

            InputStream is = new BufferedInputStream(httpURLConnection.getInputStream());
            String response = readStream(is);
            is.close();

            httpURLConnection.disconnect();
            //Log.d("data", login + password + city);
            Log.d("response", response);
            if(response.contains("error")) {
                Log.d("info", "error");
                return "error";
            }
            else{
                Log.d("info", "ok");
                return "ok";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("reslut", result);
        delegate.processFinish(result);

    }
}
