package com.mhaseeb.property.ui.property.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("property")
    @Expose
    private PropertyModel property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PropertyModel getProperty() {
        return property;
    }

    public void setProperty(PropertyModel property) {
        this.property = property;
    }
}
