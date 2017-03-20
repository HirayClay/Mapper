package com.hiray.plugin;

import android.app.Application;

import com.hiray.plugin.di.component.ApplicationComponent;
import com.hiray.plugin.di.component.DaggerApplicationComponent;


/**
 * Created by CJJ on 2017/3/17.
 */

public class BaseApplication extends Application {

    private ApplicationComponent build;

    @Override
    public void onCreate() {
        super.onCreate();
        build = DaggerApplicationComponent.builder()
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return  build;
    }
}
