package com.example.plugin.model;

import com.example.Simple;

/**
 * Created by CJJ on 2017/3/16.
 */
@Simple
public class SimpleType {
    String name;
    int number;

    OtheSimpleType otheSimpleType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OtheSimpleType getOtheSimpleType() {
        return otheSimpleType;
    }

    public void setOtheSimpleType(OtheSimpleType otheSimpleType) {
        this.otheSimpleType = otheSimpleType;
    }

}
