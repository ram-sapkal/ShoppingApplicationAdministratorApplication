package com.example.applicationadminapp;

public class A {
    String userid,userpass;

    public A() {
    }

    public A(String userid, String userpass) {
        this.userid = userid;
        this.userpass = userpass;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }
}
