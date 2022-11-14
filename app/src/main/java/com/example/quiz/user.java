package com.example.quiz;

public class user {
    private String name;
    private String email;
    private String password;
    private String refercode;

    private long coins;

    public user() {
    }

    public user(String name, String email, String password, String refercode) {
        this.name = name;
        this.email= email;
        this.password = password;
        this.refercode = refercode;

    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCoins() {
        return coins;}

    public void setCoins(long coins) {
        this.coins = coins;}


}
