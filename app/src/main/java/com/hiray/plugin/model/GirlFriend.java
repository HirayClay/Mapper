package com.hiray.plugin.model;

/**
 * Created by CJJ on 2017/3/17.
 */

public class GirlFriend {

    int age;
    boolean isHot;

    public GirlFriend() {
    }

    public GirlFriend(int age, boolean isHot) {
        this.age = age;
        this.isHot = isHot;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "age=" + age +
                ", isHot=" + isHot +
                '}';
    }
}
