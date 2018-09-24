package com.sundararaghavan.deliveryapp.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sundararaghavan.deliveryapp.persistence.entity.RepoEntity;

import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<RepoEntity> repos);

    @Query("SELECT * FROM repodb")
    LiveData<List<RepoEntity>> getAllReposLive();

    @Query("SELECT * FROM repodb")
    List<RepoEntity> getAllRepos();

    @Query("SELECT * FROM repodb where id BETWEEN :id AND :id+:pageSize ")
    LiveData<List<RepoEntity>> getRepo(int id, int pageSize);

    @Query("SELECT * FROM repodb WHERE id=:id")
    LiveData<RepoEntity> getRepo(String id);

    @Query("DELETE FROM repodb")
    void deleteAllRepos();
}

