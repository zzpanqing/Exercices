package com.qing.testjobbole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class MainActivity extends AppCompatActivity {

    private BlockingQueue<Tasker> blockingQueue = new PriorityBlockingQueue<Tasker>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.activity_main);
        start();
    }


    /**
     * 程序入口
     */
    public void start() {
        // 启动三个线程
        for (int i = 0; i < 3; i++) {
            new MyTask(blockingQueue, i).start();
        }

        // 添加100个任务让三个线程执行
        for (int i = 0; i < 100; i++) {
            Tasker tasker =  new Tasker();//Tasker.getInstance();
            blockingQueue.add(tasker);
        }
    }
}
