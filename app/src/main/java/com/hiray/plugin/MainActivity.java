package com.hiray.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hiray.plugin.di.component.DaggerMainComponent;
import com.hiray.plugin.model.GirlFriend;
import com.hiray.plugin.model.Man;
import com.hiray.plugin.model.Son;
import com.hiray.plugin.model.Wife;


import com.hiray.plugin.presenter.SimplePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @Inject
    SimplePresenter presenter;
    @Bind(R.id.textView)
    TextView textView;


//    @Inject
//    ManMapper manMapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerMainComponent.builder()
                .applicationComponent(((BaseApplication) getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);

        Man man = new Man();
        man.setAge(40);
        Wife wife = new Wife();
        wife.setAge(35);
        wife.setName("Rose");
        man.setWife(wife);
        List<Son> sons = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Son son = new Son();
            son.setAge(17 + i);
            son.setName("Son" + i);
            son.setGirlFriend(Arrays.asList(new GirlFriend(16, true), new GirlFriend(15, true)));
            sons.add(son);
        }
        man.setSon(sons);

//        Man copyMan = presenter.getMan().map(man);
        textView.setText(presenter.getMan().map(man).toString());
    }
}
