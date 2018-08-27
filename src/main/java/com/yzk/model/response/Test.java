package com.yzk.model.response;


public class Test {

    private int id;
    private String str;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", str='" + str + '\'' +
                '}';
    }
}
