package cn.blueshit.testweb.utils;

import java.io.Serializable;

/**
 * Created by zhaoheng on 16/9/1.
 */
public class Persion  implements Serializable {

    private String name;
    private int age;

    public Persion() {
    }

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}
