package com.sundararaghavan.deliveryapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.network.RepoService;
import com.sundararaghavan.deliveryapp.persistence.entity.RepoEnityModelMapper;
import com.sundararaghavan.deliveryapp.persistence.entity.RepoEntity;
import com.sundararaghavan.deliveryapp.repository.datasource.LocalDataSource;
import com.sundararaghavan.deliveryapp.repository.datasource.RemoteDataSource;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeliveryRepositoryImpl implements DeliveryRepository {
    private final RemoteDataSource mRemoteDataSource;
    private final LocalDataSource mLocalDataSource;
    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);
    private MediatorLiveData<List<Repo>> mDataMerger = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> mErrorMerger = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> mLoadingMerger = new MediatorLiveData<>();

    private DeliveryRepositoryImpl(RemoteDataSource mRemoteDataSource, LocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
        mDataMerger.addSource(this.mLocalDataSource.getDataStream(), entities ->
                mExecutor.execute(() -> {
                    List<Repo> models = RepoEnityModelMapper.transformEntitiesToModels(mLocalDataSource.getAll());
                    mDataMerger.postValue(models);
                    mLoadingMerger.postValue(false);

                })

        );
        mDataMerger.addSource(this.mRemoteDataSource.getDataStream(), entities ->
                mExecutor.execute(() -> mLocalDataSource.writeData((List<RepoEntity>) entities))
        );
        mErrorMerger.addSource(mRemoteDataSource.getErrorStream(), error -> mExecutor.execute(() -> {

                    List<RepoEntity> entities = mLocalDataSource.getAll();
                    mDataMerger.postValue(RepoEnityModelMapper.transformEntitiesToModels(entities));
                    mLoadingMerger.postValue(false);
                })
        );
        mErrorMerger.addSource(mLocalDataSource.getErrorStream(), error -> {
            mErrorMerger.setValue(error);
            mLoadingMerger.setValue(false);
        });

    }

    public static DeliveryRepository create(Application mAppContext, RepoService repoService) {
        final RemoteDataSource remoteDataSource = new RemoteDataSource(repoService);
        final LocalDataSource localDataSource = new LocalDataSource(mAppContext);
        return new DeliveryRepositoryImpl(remoteDataSource, localDataSource);
    }


    @Override
    public LiveData<List<Repo>> getRepos() {
        return mDataMerger;
    }

    @Override
    public LiveData<Boolean> getError() {
        return mErrorMerger;
    }

    @Override
    public LiveData<Boolean> getLoading() {
        return mLoadingMerger;
    }


    @Override
    public void fetch(int id, int pageSize) {

        if (!mLocalDataSource.fetch(id, pageSize)) {
            mLoadingMerger.setValue(true);
            mRemoteDataSource.fetch(id, pageSize);
        }

    }

    @Override
    public void clear() {
        mLocalDataSource.clearData();
    }
}
