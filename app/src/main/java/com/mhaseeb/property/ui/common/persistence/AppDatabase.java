package com.mhaseeb.property.ui.common.persistence;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.mhaseeb.property.ui.common.config.IAPIConstants;

/**
 * Created by muhammadmoiz on 11/10/18.
 */


@android.arch.persistence.room.Database(entities = {FavouriteEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao FavouriteDao();


}
