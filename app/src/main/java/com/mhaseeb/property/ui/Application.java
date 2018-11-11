package com.mhaseeb.property.ui;

import android.arch.persistence.room.Room;

import com.mhaseeb.property.ui.common.config.IAPIConstants;
import com.mhaseeb.property.ui.common.persistence.AppDatabase;
import com.mhaseeb.property.ui.common.preferences.PreferenceManager;
import com.mhaseeb.property.ui.common.service.ApplicationContext;
import com.mhaseeb.property.ui.common.service.ServiceLocator;

/**
 * Created by muhammadmoiz on 11/10/18.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Room initialization
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, IAPIConstants.DATABASE_NAME).build();

        ServiceLocator.initServiceLocator(getApplicationContext());
        ServiceLocator.cache.put(AppDatabase.class.getName(), db);

    }
}
