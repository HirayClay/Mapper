package com.hiray.plugin.presenter;

import com.hiray.plugin.ActivityScope;
import com.hiray.plugin.model.ManMapper;

import javax.inject.Inject;

/**
 * Created by CJJ on 2017/3/17.
 */
@ActivityScope
public class SimplePresenter {


    ManMapper man;

    @Inject
    public SimplePresenter(ManMapper man) {
        this.man = man;
    }

    public ManMapper getMan() {
        return man;
    }
}
