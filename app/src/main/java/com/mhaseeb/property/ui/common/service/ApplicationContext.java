package com.mhaseeb.property.ui.common.service;

import android.content.Context;

public class ApplicationContext {

    Context context;

    public ApplicationContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}