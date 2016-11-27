package com.example.qing.popularmovies;

import android.os.AsyncTask;
import android.util.Log;
import android.util.LogPrinter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


class FetchList extends AsyncTask<String, Void, Object[]> {

    final static String LOG_TAG  = FetchList.class.getSimpleName();

    OnTaskCompleted mTaskCompletedListener;
    JsonParser mJsonParser;
    int mAskHowMany;

    FetchList(OnTaskCompleted listener,
              JsonParser iParser,
              int iAskHowMany){
        mTaskCompletedListener = listener;
        mJsonParser = iParser;
        mAskHowMany = iAskHowMany;
    }

    @Override
    protected Object[] doInBackground(String... Uri) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;


        try {
            //String u2 = "http://api.themoviedb.org/3/movie/popular/?language=en-US&api_key=ef24890fe794fe46f73df80ccc5a71bc";
            URL url = new URL(Uri[0]);
            Log.e(LOG_TAG, url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a string;
            InputStream inputStream = urlConnection.getInputStream();
            int a = inputStream.available();
            String s = inputStream.toString();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonStr = buffer.toString();

            Log.v(LOG_TAG, jsonStr);

            Object[] ret = mJsonParser.extractData(jsonStr, mAskHowMany);
            return ret;
        } catch (MalformedURLException iE) {
            Log.e(LOG_TAG, "mal formed url " + Uri + " " + iE.getMessage());
        } catch (ProtocolException iE) {
            Log.e(LOG_TAG, "ProtocolException " + urlConnection + " " + iE.getMessage());
        } catch (IOException iE) {
            Log.e(LOG_TAG, "url.openConnection() throw IOException " + iE.getMessage());
        } catch (JSONException iE) {
            Log.e(LOG_TAG, "extractData from json throw an exception" + iE.getMessage());
        }
        finally {

        }


        return null;
    }

    @Override
    protected void onPostExecute(Object[] ret) {
        mTaskCompletedListener.onTaskCompleted(ret);
    }
}
