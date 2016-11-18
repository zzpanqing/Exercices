package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Pair;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;


/**
 * Tests for {@link EndpointsAsyncTask} in  {@link MainActivity}
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest{
    /**
     * {@link ActivityTestRule} is a JUnit{@link Rule @Rule} to launch
     * your activity under test
     * <p>
     * Rules are interceptors which are executed for each test method
     * are important building blocks of Junit tests
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnTellAJoke_GetJavaJoke(){
        onView(withId(R.id.bt_tell_joke))
                .perform(click());
        onView(withId(R.id.jokeText))
                .check(matches(allOf(isDisplayed())));


    }

    @Test
    public void getJokeNonNull(){
        OnTaskCompleted listener = new OnTaskCompleted(){
            @Override
            public void onTaskCompleted(String result) {
                assertEquals("JavaJokes tell you a joke.", result);
            }
        };
        new EndpointsAsyncTask(listener).execute(new Pair<Context, String>(InstrumentationRegistry.getContext(), "Manfred"));

    }
}