package com.jzm.anp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jzm.anp.R;
import com.jzm.anp.thread.ThreadRunner;
import com.jzm.anp.thread.WorkIntentService;
import com.jzm.anp.thread.WorkResultReceiver;

import java.lang.ref.WeakReference;

/**
 * 多线程使用测试
 */
public class MutiThreadActivity extends AppCompatActivity {
    private Handler handler;
    private TextView textView;

    private Handler mThreadHandler;
    private MyHandlerThread myHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        Button startBtn = findViewById(R.id.start_btn);
        textView = findViewById(R.id.handelr_text);


        startBtn.setOnClickListener(view -> {
            startThread();
        });

        //匿名内部类默认持有外部类的对象，容易导致内存泄漏
//        handler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == 1) {
//                    textView.setText((String)msg.obj);
//                } else if(msg.what == 2) {
//                    textView.setText((String)msg.obj);
//                }
//            }
//        };

        handler = new MyHandler(this);

        //HandlerThread, 开启子线程并运行
        myHandlerThread = new MyHandlerThread("myHandlerThread");
        myHandlerThread.start();
        mThreadHandler = new MyThreadHandler(myHandlerThread.getLooper());

        //向子线程发送消息，子线程接收消息并处理
//        for (int i = 0; i < 10; i++) {
//            Message msg = mThreadHandler.obtainMessage();
//            msg.obj = "message <" + i + "> from main thread";
//            mThreadHandler.sendMessage(msg);
//        }

        //模拟开启多个子线程，和开启多个AsyncTask做比较，AsyncTask会按顺序执行，会阻塞
//        for (int i = 0; i < 10; i++) {
//           MyHandlerThread handlerThread = new MyHandlerThread("mht" + i);
//           handlerThread.start();
//
//           MyThreadHandler handler = new MyThreadHandler(handlerThread.getLooper());
//           Message msg = handler.obtainMessage();
//           msg.obj = "msssage in loop, index: " + i;
//           handler.sendMessage(msg);
//        }

        Button asyncBtn = findViewById(R.id.async_btn);
        //AsyncTask 默认会在单独的线程中执行，于是多任务情况下也可能是顺序执行，导致阻塞
        asyncBtn.setOnClickListener(view -> {
            for (int i = 0; i < 10 ; i++) {
                new MyAsyncTask(this).execute("asynctask" + i);
            }
        });

        Button intentServiceBtn = findViewById(R.id.intent_service_btn);
        //AsyncTask 默认会在单独的线程中执行，于是多任务情况下也可能是顺序执行，导致阻塞
        WorkResultReceiver wrr = new WorkResultReceiver(new Handler());
        wrr.setView(textView);
        intentServiceBtn.setOnClickListener(view -> {
            for (int i = 0; i < 10; i++) {
                Intent intent = new Intent(this, WorkIntentService.class);
                intent.putExtra("extra", "string from main thread, #" + i);
                intent.putExtra("rr", wrr);
                startService(intent);
            }
        });

        Button threadPoolBtn = findViewById(R.id.thread_pool_btn);
        threadPoolBtn.setOnClickListener(view -> {
//            ThreadRunner.INSTANCE.runInFixedThreadPool();
//            ThreadRunner.INSTANCE.runInCachedThreadPool();
//            ThreadRunner.INSTANCE.runInScheduledThreadPool();
            ThreadRunner.INSTANCE.runInSingleThreadExecutor();
        });
    }

    @Override
    protected void onDestroy() {
        myHandlerThread.quit();
        super.onDestroy();
        Log.d("handler", "onDestroy");
    }

    private void startThread() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = handler.obtainMessage();
            message.what = 1;
            message.obj = "hello";
            handler.sendMessage(message);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message message = handler.obtainMessage();
            message.what = 2;
            message.obj = "earth";
            handler.sendMessage(message);
        }).start();
    }

    /**
     * 静态内部类，不持有外部类的对象，handler消息未处理完成时，activity可正常销毁
     * 静态内部类要使用activity的属性时，activity在内部以弱引用的方式保存，便于gc及时回收
     */
    private static class MyHandler extends Handler {
        private WeakReference<MutiThreadActivity> ref;

        public MyHandler(MutiThreadActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("handler", "handle message: " + msg.what);
            Log.d("handler", "ref get: " + ref.get());

            Log.d("handler", "handle message on thread: " + Thread.currentThread().getName());

            ref.get().textView.setText((String)msg.obj);
        }
    }

    private static class MyHandlerThread extends HandlerThread {

        public MyHandlerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Log.d("handler", "handler thread run 0");
            super.run();
            //Looper.loop() in super.run, so after HandlerThread's quit, below log will print
            Log.d("handler", "handler thread run 1");
        }
    }

    private static class MyThreadHandler extends Handler {
        public MyThreadHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("handler", "handle message on thread: " + Thread.currentThread().getName() +
                    "\n message: " + msg.obj);
        }
    }

    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<MutiThreadActivity> ref;
        public MyAsyncTask(MutiThreadActivity activity) {
            ref = new WeakReference(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d("handler", "do in background, current thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("handler", "onPostExecute, current thread: " + Thread.currentThread().getName());
            ref.get().textView.setText(s);
        }
    }
}

