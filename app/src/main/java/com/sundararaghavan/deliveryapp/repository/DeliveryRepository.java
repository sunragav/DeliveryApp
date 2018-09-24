package com.sundararaghavan.deliveryapp.repository;

import android.arch.lifecycle.LiveData;

import com.sundararaghavan.deliveryapp.model.Repo;

import java.util.List;

public interface DeliveryRepository {
    LiveData<List<Repo>> getRepos();

    LiveData<Boolean> getError();

    LiveData<Boolean> getLoading();

    void fetch(int id, int pageSize);

    void clear();
}
