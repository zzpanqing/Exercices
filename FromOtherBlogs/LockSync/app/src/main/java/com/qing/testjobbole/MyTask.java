package com.qing.testjobbole;

import java.util.concurrent.BlockingQueue;

/**
 * Created by qing on 23/02/17.
 */
public class MyTask extends Thread{
    private BlockingQueue<Tasker> blockingQueue;
    private int id;
    public MyTask(BlockingQueue<Tasker> blockingQueue, int _id) {
        this.blockingQueue = blockingQueue;
        id = _id;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Tasker person = blockingQueue.take();
                person.change(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
