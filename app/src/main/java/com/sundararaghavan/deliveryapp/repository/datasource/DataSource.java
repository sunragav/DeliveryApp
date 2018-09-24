package com.sundararaghavan.deliveryapp.repository.datasource;

import android.arch.lifecycle.LiveData;

public interface DataSource<T> {
    LiveData<T> getDataStream();

    LiveData<Boolean> getErrorStream();

    void clearData();
}
