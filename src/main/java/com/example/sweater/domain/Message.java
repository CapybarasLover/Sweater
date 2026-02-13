package com.example.sweater.domain;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;
    private String tag;

    public Message(){}

    public Message(String text, String tag){
        this.text = text;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
