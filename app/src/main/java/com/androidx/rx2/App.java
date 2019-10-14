package com.androidx.rx2;

import android.app.Application;

/**
 * Created by meikai on 2019/10/12.
 */
public class App extends Application {

    private static App app;

    public static App getApplication(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

}
