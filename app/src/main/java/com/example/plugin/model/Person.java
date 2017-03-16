package com.example.plugin.model;

import java.util.List;
import com.example.Mapper;
/**
 * Created by CJJ on 2017/3/15.
 *
 */
@Mapper
public class Person {
    String name;
    String gender;
    String exe;
    String code;
    List<String> strings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExe() {
        return exe;
    }

    public void setExe(String exe) {
        this.exe = exe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
