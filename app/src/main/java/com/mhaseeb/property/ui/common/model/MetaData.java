package com.mhaseeb.property.ui.common.model;

;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaData {

    @SerializedName("category")
    @Expose
    private List<String> category = null;
    @SerializedName("sub_category1")
    @Expose
    private List<String> subCategory1 = null;
    @SerializedName("sub_category2")
    @Expose
    private List<String> subCategory2 = null;
    @SerializedName("product_name")
    @Expose
    private List<String> productName = null;
    @SerializedName("premises")
    @Expose
    private List<String> premises = null;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getSubCategory1() {
        return subCategory1;
    }

    public void setSubCategory1(List<String> subCategory1) {
        this.subCategory1 = subCategory1;
    }

    public List<String> getSubCategory2() {
        return subCategory2;
    }

    public void setSubCategory2(List<String> subCategory2) {
        this.subCategory2 = subCategory2;
    }

    public List<String> getProductName() {
        return productName;
    }

    public void setProductName(List<String> productName) {
        this.productName = productName;
    }

    public List<String> getPremises() {
        return premises;
    }

    public void setPremises(List<String> premises) {
        this.premises = premises;
    }

}