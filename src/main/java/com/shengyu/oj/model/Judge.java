package com.shengyu.oj.model;

import javax.persistence.*;

@Entity
@Table(name = "Judge")
public class Judge {
    @Id
    private Long id;
    private String example;
    private String res;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }
}
