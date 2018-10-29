package com.mhaseeb.property.ui.home.presenter;

import android.content.Context;

import com.mhaseeb.property.ui.common.model.ListModel;
import com.mhaseeb.property.ui.home.IHomeContract;

import java.util.List;

public class HomePresenterImp implements IHomeContract.Presenter {

    private Context context;
    private IHomeContract.View view;
    private List<ListModel> filteredProducts;

    public HomePresenterImp(Context context, IHomeContract.View view) {
        this.context = context;
        this.view = view;
    }

}
