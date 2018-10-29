package com.mhaseeb.property.ui.register.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponseModel {
    @SerializedName("status")
    @Expose
    private Boolean stats;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private RegisterRequestModel data;

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

    public RegisterRequestModel getData() {
        return data;
    }

    public void setData(RegisterRequestModel data) {
        this.data = data;
    }

}