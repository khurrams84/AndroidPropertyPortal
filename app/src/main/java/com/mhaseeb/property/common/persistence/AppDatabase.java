package com.mhaseeb.property.common.persistence;

import android.arch.persistence.room.RoomDatabase;

/**
 * Created by muhammadmoiz on 11/10/18.
 */


@android.arch.persistence.room.Database(entities = {FavouriteEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao FavouriteDao();


}
