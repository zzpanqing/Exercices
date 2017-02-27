package com.example.qingpan.myapplication.backend;

import com.javajokes.JavaJokes;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {


    private String myData;

    private JavaJokes mJoke;

    public MyBean(){
        mJoke = new JavaJokes();
    }
    public String getJavaJokes() {
        return mJoke.getAJoke();
    }
    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}