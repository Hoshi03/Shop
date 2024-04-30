package com.example.shop;

public class Status {
    private int age;
    private String name;

    public void setAge(int age) {
        if (age > 0 && age <= 100) this.age = age;
    }
    public void oneYearPlus(){
        age++;
    }
}
