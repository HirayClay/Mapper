package com.hiray.plugin.model;



import com.hiray.Mapper;

import java.util.List;

/**
 * Created by CJJ on 2017/3/17.
 */
@Mapper
public class Man {
    int age;
    List<Son> son;
    Wife wife;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Son> getSon() {
        return son;
    }

    public void setSon(List<Son> son) {
        this.son = son;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "Man{" +
                " age=" + age +
                ", son=" + son +"\n         "+
                ", wife=" + wife +
                '}';
    }
}
