package com.sundararaghavan.deliveryapp.repository.datasource;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.sundararaghavan.deliveryapp.persistence.dao.RepoDB;
import com.sundararaghavan.deliveryapp.persistence.entity.RepoEntity;

import java.util.List;

import timber.log.Timber;

public class LocalDataSource implements DataSource<List<RepoEntity>> {
    private final RepoDB mDb;
    private final MutableLiveData<Boolean> mError = new MutableLiveData<>();

    public LocalDataSource(Application application) {
        mDb = RepoDB.getDatabase(application);
    }

    @Override
    public LiveData<List<RepoEntity>> getDataStream() {
        return mDb.repoDao().getAllReposLive();
    }

    public boolean fetch(int id, int pageSize) {
        LiveData<List<RepoEntity>> repos = mDb.repoDao().getRepo(id, pageSize);
        return repos != null && repos.getValue() != null && repos.getValue().size() > 0;
    }

    @Override
    public LiveData<Boolean> getErrorStream() {
        return mError;
    }

    public void writeData(List<RepoEntity> repos) {
        try {
            mDb.repoDao().insertRepos(repos);
            mError.postValue(false);
        } catch (Exception e) {
            Timber.e("Error writing data to Room DB");
            mError.postValue(true);
        }
    }

    @Override
    public void clearData() {
        new Thread() {
            @Override
            public void run() {
                mDb.repoDao().deleteAllRepos();
            }
        }.start();
    }

    public List<RepoEntity> getAll() {
        return mDb.repoDao().getAllRepos();
    }
}
