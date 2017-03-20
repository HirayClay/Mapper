package com.hiray.plugin.di.module;


import com.hiray.plugin.model.Man;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CJJ on 2017/3/17.
 */
@Module
public class SimpleModule {

    @Provides
    @Singleton
   public Man provideMan(){
        return new Man();
    }

//    @Provides
//    @Singleton
//    public Girl provideGirl(){
//        return new Girl();
//    }

//    @Provides
//    @Singleton
//    public ManMapper provideMapper(){
//        return new ManMapper();
//    }
}
