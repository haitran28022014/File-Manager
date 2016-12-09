package com.example.haitran.apache;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hai Tran on 10/1/2016.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static Context getContext() {
        return context;
    }
}
