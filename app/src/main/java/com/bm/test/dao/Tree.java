package com.bm.test.dao;

/**
 * Created by zhangp01 on 2017/2/8/008.
 */

public class Tree {
    private int id;
    private String name;
    private String phone;
    private float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Tree() {
    }

    public Tree(String name, String phone, float price) {
        this.name = name;
        this.phone = phone;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", price=" + price +
                '}';
    }
}
