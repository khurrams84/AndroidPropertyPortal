package com.mhaseeb.property.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritesModel {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("propertyId")
    @Expose
    private Integer propertyId;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}