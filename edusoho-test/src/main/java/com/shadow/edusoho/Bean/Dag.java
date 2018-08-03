package com.shadow.edusoho.Bean;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/2.
 */

public class Dag {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Dag{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
