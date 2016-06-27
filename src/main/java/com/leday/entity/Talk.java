package com.leday.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/27.
 */
public class Talk {

    private String name;
    private String msg;
    private Type type;
    private Date date;

    public enum Type {
        INCOMING, OUTCOMING;
    }

    public Talk() {
    }

    public Talk(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}