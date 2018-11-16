package com.mhaseeb.property.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mhaseeb.property.register.models.RegisterRequestModel;

public class LoginResponseModel {

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