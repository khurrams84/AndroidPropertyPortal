package com.mhaseeb.property.common.persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.mhaseeb.property.property.model.PropertyModel;

/**
 * Created by muhammadmoiz on 11/10/18.
 */

@Entity(tableName = "favourite")
public class FavouriteEntity {

    public FavouriteEntity() {}

    public FavouriteEntity(PropertyModel propertyModel) {
        this.setPropertyId(propertyModel.getId());
        this.setTitle(propertyModel.getTitle());
        this.setDescription(propertyModel.getDescription());
        this.setAddress(propertyModel.getAddress());
        this.setCategory(propertyModel.getCategory());
        this.setType(propertyModel.getType());
        this.setCountry(propertyModel.getCountry());
        this.setCity(propertyModel.getCity());
        this.setPhoneOfSeller(propertyModel.getPhoneOfSeller());
        this.setAreaUnit(propertyModel.getAreaUnit());
        this.setArea(propertyModel.getArea());
        this.setPrice(propertyModel.getPrice());
        this.setBedrooms(propertyModel.getBedrooms());
        this.setBathrooms(propertyModel.getBathrooms());
        this.setKitchen(propertyModel.getKitchen());
        this.setYearBuild(propertyModel.getYearBuild());
        this.setFavourite(propertyModel.isFavourite());
        this.setUserId(propertyModel.getUserId());
        if (propertyModel.getImages() != null && propertyModel.getImages().size() > 0) {
            this.setImages(propertyModel.getImages().get(0).getImagePath());
        }

    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer propertyId;

    private String title;

    private String description;

    private String address;

    private String category;

    private String type;

    private String country;

    private String city;

    private String phoneOfSeller;

    private String areaUnit;

    private String area;

    private String price;

    private String bedrooms;

    private String bathrooms;

    private String kitchen;

    private String yearBuild;

    private boolean favourite;

    private Integer userId;

    private String images;

    @NonNull
    public Integer getId() {
        return id;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public void setId(@NonNull Integer id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
