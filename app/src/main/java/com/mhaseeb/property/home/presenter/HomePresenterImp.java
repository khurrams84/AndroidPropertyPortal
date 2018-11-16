package com.mhaseeb.property.home.presenter;

import android.content.Context;

import com.mhaseeb.property.common.model.ListModel;
import com.mhaseeb.property.common.service.ApplicationContext;
import com.mhaseeb.property.common.service.ServiceLocator;
import com.mhaseeb.property.home.IHomeContract;

import java.util.List;

public class HomePresenterImp implements IHomeContract.Presenter {

    private Context context;
    private IHomeContract.View view;
    private List<ListModel> filteredProducts;

    public HomePresenterImp(IHomeContract.View view) {
        this.context = ServiceLocator.getService(ApplicationContext.class).getContext();
        this.view = view;
    }

}
