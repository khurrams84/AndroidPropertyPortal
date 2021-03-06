package com.mhaseeb.property;

import android.arch.persistence.room.Room;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mhaseeb.property.common.config.IAPIConstants;
import com.mhaseeb.property.common.persistence.AppDatabase;
import com.mhaseeb.property.common.service.ServiceLocator;

/**
 * Created by muhammadmoiz on 11/10/18.
 */

public class Application extends android.app.Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        //Room initialization
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, IAPIConstants.DATABASE_NAME).build();

        ServiceLocator.initServiceLocator(getApplicationContext());
        ServiceLocator.cache.put(AppDatabase.class.getName(), db);

        MobileAds.initialize(this, IAPIConstants.AD_MOB_ID);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }
}
