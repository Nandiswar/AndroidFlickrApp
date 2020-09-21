package com.example.flickr;

import android.app.Application;

import com.example.flickr.di.ApplicationComponent;
import com.example.flickr.di.DaggerApplicationComponent;

public class BaseApplication extends Application {

    ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent appComponent() {
        if (appComponent == null) {
            appComponent = DaggerApplicationComponent.create();
        }

        return appComponent;
    }
}
