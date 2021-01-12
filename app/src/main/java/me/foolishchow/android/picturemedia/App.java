package me.foolishchow.android.picturemedia;

import android.app.Application;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/1/2021 2:11 PM
 */
public class App extends Application {


    public Thread.UncaughtExceptionHandler mExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(mExceptionHandler);
    }
}
