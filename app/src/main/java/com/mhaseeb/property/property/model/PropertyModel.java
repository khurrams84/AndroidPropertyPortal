package com.mhaseeb.property.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PropertyModel implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("phone_of_seller")
    @Expose
    private String phoneOfSeller;
    @SerializedName("area_unit")
    @Expose
    private String areaUnit;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("bedrooms")
    @Expose
    private String bedrooms;
    @SerializedName("bathrooms")
    @Expose
    private String bathrooms;
    @SerializedName("kitchen")
    @Expose
    private String kitchen;
    @SerializedName("year_build")
    @Expose
    private String yearBuild;
    @SerializedName("favourite")
    @Expose
    private boolean favourite;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("images")
    @Expose
    private List<ImageModel> images = null;

    //Constructors


    public PropertyModel() {
    }

    public PropertyModel(Integer id, String title, String description, String address, String type, String category, String country, String city, String phoneOfSeller, String areaUnit, String area, String price, String bedrooms, String bathrooms, String kitchen, String yearBuild, Integer userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.type = type;
        this.category = category;
        this.country = country;
        this.city = city;
        this.phoneOfSeller = phoneOfSeller;
        this.areaUnit = areaUnit;
        this.area = area;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.kitchen = kitchen;
        this.yearBuild = yearBuild;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneOfSeller() {
        return phoneOfSeller;
    }

    public void setPhoneOfSeller(String phoneOfSeller) {
        this.phoneOfSeller = phoneOfSeller;
    }

    public String getAreaUnit() {
        return areaUnit;
    }

    public void setAreaUnit(String areaUnit) {
        this.areaUnit = areaUnit;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getYearBuild() {
        return yearBuild;
    }

    public void setYearBuild(String yearBuild) {
        this.yearBuild = yearBuild;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }
}