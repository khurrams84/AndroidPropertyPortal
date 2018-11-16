package com.mhaseeb.property.common.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by muhammadmoiz on 11/10/18.
 */

@Dao
public interface FavouriteDao {

    @Insert
    void insertOnlySingleFavourite(FavouriteEntity favouriteEntity);

    @Insert
    void insertMultipleFavourite (List<FavouriteEntity> favouriteList);

    @Query("SELECT * FROM favourite WHERE id = :id")
    FavouriteEntity fetchOneFavouritebyId (int id);

    @Query("SELECT * from favourite ORDER BY id DESC LIMIT 1")
    FavouriteEntity fetchLastRecord();

    @Query("DELETE FROM favourite WHERE propertyId = :propertyId")
    void deleteByPropertyId(int propertyId);

    @Query("DELETE FROM FAVOURITE")
    void deleteAllFavourite();

    @Update
    void updateFavourite (FavouriteEntity favourite);

    @Delete
    void deleteFavourite (FavouriteEntity favourite);

}
