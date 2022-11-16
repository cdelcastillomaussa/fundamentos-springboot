package com.fundamentos.springboot.fundamentos.bean;

public class MyBeanWithPropertiesImplement implements MyBeanWithProperties{

    private String name;
    private String ape;

    public MyBeanWithPropertiesImplement(String name, String ape) {
        this.name = name;
        this.ape = ape;
    }

    @Override
    public String function() {
        return name + "-" + ape;
    }
}
