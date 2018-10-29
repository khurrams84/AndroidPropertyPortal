package com.mhaseeb.property.ui.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetaDataResponseModel {

@SerializedName("success")
@Expose
private Boolean success;
@SerializedName("data")
@Expose
private List<MetaData> data = null;

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public List<MetaData> getData() {
return data;
}

public void setData(List<MetaData> data) {
this.data = data;
}

}