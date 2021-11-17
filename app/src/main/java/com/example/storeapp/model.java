package com.example.storeapp;

public class model {
    String shop;
    String phone;
    String account;
    String ifsc;

    model()
    {

    }

    public model(String shop, String phone, String account, String ifsc) {
        this.shop = shop;
        this.phone = phone;
        this.account = account;
        this.ifsc = ifsc;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
}
