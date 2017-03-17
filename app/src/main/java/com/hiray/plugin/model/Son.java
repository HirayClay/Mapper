package com.hiray.plugin.model;

import java.util.List;

/**
 * Created by CJJ on 2017/3/17.
 */

public class Son {
    String name;
    int age;
    List<GirlFriend> girlFriend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<GirlFriend> getGirlFriend() {
        return girlFriend;
    }

    public void setGirlFriend(List<GirlFriend> girlFriend) {
        this.girlFriend = girlFriend;
    }

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", girlFriend=" + girlFriend +"\n             "+
                '}';
    }
}
