package com.example.applicationadminapp;

public class newShirt {
    String bcode,size,style,quantity,url,price,company,categoryy;

    public newShirt(String bcode, String size, String style, String quantity, String url, String price, String company, String categoryy) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
        this.quantity = quantity;
        this.url = url;
        this.price = price;
        this.company = company;
        this.categoryy = categoryy;
    }

    public newShirt(String bcode, String size, String style, String quantity, String url, String price) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
        this.quantity = quantity;
        this.url = url;
        this.price = price;
    }

    public newShirt(String bcode, String size, String style, String quantity, String url) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
        this.quantity = quantity;
        this.url = url;
    }

    public newShirt(String bcode, String size, String style, String quantity) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
        this.quantity = quantity;
    }

    public newShirt(String bcode, String size, String style) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
    }

    public newShirt(String bcode, String size) {
        this.bcode = bcode;
        this.size = size;
    }

    public newShirt() {
    }

    public newShirt(String bcode, String size, String style, String quantity, String url, String price, String company) {
        this.bcode = bcode;
        this.size = size;
        this.style = style;
        this.quantity = quantity;
        this.url = url;
        this.price = price;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public String getCategoryy() {
        return categoryy;
    }

    public void setCategoryy(String categoryy) {
        this.categoryy = categoryy;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
