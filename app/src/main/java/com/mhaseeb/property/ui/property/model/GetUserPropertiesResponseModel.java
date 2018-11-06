package com.mhaseeb.property.ui.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserPropertiesResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean stats;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<PropertyModel> data;

    public Boolean getStats() {
        return stats;
    }

    public void setStats(Boolean stats) {
        this.stats = stats;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PropertyModel> getData() {
        return data;
    }

    public void setData(List<PropertyModel> data) {
        this.data = data;
    }
}