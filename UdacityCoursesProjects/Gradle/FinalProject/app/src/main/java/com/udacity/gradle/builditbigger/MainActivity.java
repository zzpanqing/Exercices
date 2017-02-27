package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qingpan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.javajokes.JavaJokes;
import com.udacity.qingpan.libandroidjokes.ActivityJokes;

import java.io.IOException;

import static android.os.AsyncTask.execute;


public class MainActivity extends AppCompatActivity implements OnTaskCompleted{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.main_activity_container) != null){
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_activity_container, new MainActivityFragment())
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, "Manfred"));
        //JavaJokes jokes = new JavaJokes();
        //Toast.makeText(this, jokes.getAJoke(), Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, ActivityJokes.class);
        //intent.putExtra("javaJoke", jokes.getAJoke());
        //startActivity(intent);


    }


    @Override
    public void onTaskCompleted(String result) {
        ActivityJokes newFragment = new ActivityJokes();
        Bundle args = new Bundle();
        args.putString("GCEJoke", result);
        newFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_activity_container, newFragment)
                .commit();
    }
}