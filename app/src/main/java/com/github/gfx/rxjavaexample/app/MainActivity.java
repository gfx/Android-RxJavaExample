package com.github.gfx.rxjavaexample.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                hello();

                return null;
            }
        }.execute();
    }


    public static void hello(String... names) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext("foo");
                    Thread.sleep(1000);
                    subscriber.onNext("bar");
                    Thread.sleep(2000);
                    subscriber.onNext("baz");
                    Thread.sleep(3000);
                    subscriber.onError(new Exception("error!"));
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
            }
        }).onErrorReturn(new Func1<Throwable, String>() {
            @Override
            public String call(Throwable throwable) {
                Log.wtf("XXX", throwable);
                return null;
            }
        }).subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                Log.d("XXX", "Hello " + s + "!");
            }

        });
    }
}
