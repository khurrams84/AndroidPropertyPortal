package com.mhaseeb.property.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("products")
    @Expose
    private List<ListModel> products = null;

    public List<ListModel> getProducts() {
        return products;
    }

    public void setProducts(List<ListModel> products) {
        this.products = products;
    }

}