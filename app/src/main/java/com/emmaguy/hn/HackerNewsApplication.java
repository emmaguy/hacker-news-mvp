package com.emmaguy.hn;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by emma on 20/06/15.
 */
public class HackerNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
