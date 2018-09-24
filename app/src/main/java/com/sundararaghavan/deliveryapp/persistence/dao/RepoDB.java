package com.sundararaghavan.deliveryapp.persistence.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sundararaghavan.deliveryapp.persistence.entity.RepoEntity;


@Database(entities = {RepoEntity.class}, version = 1, exportSchema = false)
public abstract class RepoDB extends RoomDatabase {

    static private final String DATABASE_NAME = "repodb";
    private static RepoDB INSTANCE;

    public static RepoDB getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    RepoDB.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public abstract RepoDao repoDao();

}