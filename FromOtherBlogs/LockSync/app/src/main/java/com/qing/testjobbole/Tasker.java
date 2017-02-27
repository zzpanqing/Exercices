package com.qing.testjobbole;

import android.util.Log;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by qing on 23/02/17.
 */
public class Tasker implements Serializable, Comparable<Tasker> {
    private static Integer value = 0;

    private static Lock lock = new ReentrantLock();
    private static Tasker tasker;

    public  static Tasker getInstance() {
        synchronized(Tasker.class) {
            if (tasker == null)
                tasker = new Tasker();
        return tasker;
        }
    }

    // synchronized 关键字定义了锁，同时只能有一个 thread
    // 执行该段代码，但是这个锁是每个 object 一个锁，不同的
    // object 对这段代码，还是可以同时访问
    public  void change(int thread_id) {

         lock.lock();
        try
        {
            // Tasker.class 是该类唯一的class object
            // 可以Object锁的Object
            value++;
            Log.i("test_thread_" + thread_id, "" + value);
            if (value == 50)
                throw new RuntimeException("");
        }
        finally {
            lock.unlock();
        }

    }



    @Override
    public int compareTo(Tasker o) {
        return 0;
    }
}
