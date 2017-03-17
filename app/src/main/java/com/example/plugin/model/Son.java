package com.example.plugin.model;

import java.util.List;

/**
 * Created by CJJ on 2017/3/17.
 */

public class Son {
    String name;
    int age;
    List<GirlFriend> girFriend;

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

    public List<GirlFriend> getGirFriend() {
        return girFriend;
    }

    public void setGirFriend(List<GirlFriend> girFriend) {
        this.girFriend = girFriend;
    }

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", girFriend=" + girFriend +"\n             "+
                '}';
    }
}
