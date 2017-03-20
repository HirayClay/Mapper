package com.hiray.plugin.di.component;

import com.hiray.plugin.ActivityScope;
import com.hiray.plugin.MainActivity;

import dagger.Component;

/**
 * Created by CJJ on 2017/3/17.
 */
@ActivityScope
@Component(dependencies = {ApplicationComponent.class})
public interface MainComponent {
    void inject(MainActivity activity);

}
