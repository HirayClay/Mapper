package com.hiray.plugin.di.component;



import com.hiray.plugin.MapperModule;
import com.hiray.plugin.di.module.SimpleModule;
import com.hiray.plugin.model.Man;
import com.hiray.plugin.model.ManMapper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by CJJ on 2017/3/17.
 */
@Singleton
@Component(modules = {SimpleModule.class, MapperModule.class})
public interface ApplicationComponent {

//    void inject(MainActivity activity);
    Man man();
    ManMapper mapper();
}
