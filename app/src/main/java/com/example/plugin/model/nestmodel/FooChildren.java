package com.example.plugin.model.nestmodel;

import com.example.Mapper;
import com.example.plugin.model.Foo;

import java.util.List;

/**
 * Created by CJJ on 2017/3/16.
 */
@Mapper
public class FooChildren {
    String parent;

    String self;

    Foo foo;
    List<Foo> fooList;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public List<Foo> getFooList() {
        return fooList;
    }

    public void setFooList(List<Foo> fooList) {
        this.fooList = fooList;
    }
}
