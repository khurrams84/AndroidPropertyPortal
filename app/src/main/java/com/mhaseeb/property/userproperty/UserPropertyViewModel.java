package com.mhaseeb.property.userproperty;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.mhaseeb.property.property.model.GetUserPropertiesResponseModel;

/**
 * Created by muhammadmoiz on 11/16/18.
 */

public class UserPropertyViewModel extends ViewModel {

    private LiveData<GetUserPropertiesResponseModel> userProperty;
    private UserPropertyRepository userPropertyRepository;

    public UserPropertyViewModel() {
        this.userPropertyRepository = UserPropertyRepository.getInstance();
    }

    public LiveData<GetUserPropertiesResponseModel> getUserProperty(int userId) {
        return this.userPropertyRepository.getUserProperty(userId);
    }

}
