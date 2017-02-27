package com.qing.architect.google.todomvp.todomvp.utils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicInteger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An simple counter implementation of {@link IdlingResource} that
 * determine idleness by maintaining an internal counter. When the
 * counter is 0 - it is considered to be idle, when it is non-zero it
 * is not idle. This is very similar to the way a {@link java.util.concurrent.Semaphore}
 * behaves
 *
 * This class can then be used to wrap up operations that while in
 * progress should block tests from accessing the UI
 */
public class SimpleCountingIdlingResource implements IdlingResource{


    private final String mResourceName;
    private AtomicInteger counter;
    // resourceCallback is called when the resource is idle.
    private ResourceCallback resourceCallback;

    /**
     * Creates a SimpleCountingIdlingResource
     * @param resourceName the resource name this resource should report
     *          to Espresso
     */
    public SimpleCountingIdlingResource(String resourceName){
        mResourceName = checkNotNull(resourceName);
    }

    @Override
    public String getName() {
        return mResourceName;
    }

    @Override
    public boolean isIdleNow() {
        return counter.get() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(
            ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    /**
     * Increments the count of the in-flight transactions to the
     * resource being monitored
     */
    public void increment() {
        counter.getAndIncrement();
    }

    /**
     * Decrements the count of in-flight transactions to the resource
     * being monitored.
     * If this operation results in the counter falling bellow 0 - an
     * exception is raised.
     *
     * @throws IllegalStateException if the counter is below 0
     */
    public void decrement(){
        int counterVal = counter.decrementAndGet();
        if (counterVal == 0){
            // we've gone from non-zero to ero. That means we're idle
            if(null != resourceCallback)
                resourceCallback.onTransitionToIdle();
        }

        if(counterVal < 0)
            throw new IllegalStateException("counter has being corrupted");
    }
}
