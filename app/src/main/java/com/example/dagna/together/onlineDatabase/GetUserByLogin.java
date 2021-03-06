package com.example.dagna.together.onlineDatabase;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
 * Created by Dagna on 18/04/2016.
 */
public class GetUserByLogin extends AsyncTask<String, Void, String>
{
public static String JSON_STRING;
public static String json_string;

public interface AsyncResponse {
    void processFinish(String output);
}

public AsyncResponse delegate = null;

    public GetUserByLogin(AsyncResponse delegate){
        this.delegate = delegate;
    }

String json_url;

    @Override
    protected void onPreExecute() {
        json_url="https://omega.aizio.net:5678/get_user_by_login.php";
    }

    @Override
    protected String doInBackground(String... args) {
        String login, password;
        login=args[0];
        //password=args[1];
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String data_string = URLEncoder.encode("login", "UTF-8")+"="+URLEncoder.encode(login, "UTF-8");

            bufferWriter.write(data_string);
            bufferWriter.flush();
            bufferWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            while((JSON_STRING=bufferedReader.readLine())!=null){
                stringBuilder.append(JSON_STRING+ "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        Log.d("data", result);
        json_string=result;
        delegate.processFinish(result);


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
