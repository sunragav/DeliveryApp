package com.sundararaghavan.deliveryapp.repository.datasource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.network.RepoService;
import com.sundararaghavan.deliveryapp.persistence.entity.RepoEnityModelMapper;
import com.sundararaghavan.deliveryapp.persistence.entity.RepoEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource<List<RepoEntity>> {
    private final MutableLiveData<Boolean> mError = new MutableLiveData<>();
    private final MutableLiveData<List<RepoEntity>> mDataApi = new MutableLiveData<>();
    private RepoService repoService;

    public RemoteDataSource(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    public LiveData<List<RepoEntity>> getDataStream() {
        return mDataApi;
    }

    public void fetch(int id, int pageSize) {
        Call<List<Repo>> repoCall = repoService.getRepositories(id, pageSize);
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                mError.setValue(false);
                mDataApi.setValue(RepoEnityModelMapper.transformModelsToEntities(response.body()));

            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                mError.setValue(true);
            }
        });
    }

    @Override
    public LiveData<Boolean> getErrorStream() {
        return mError;
    }

    @Override
    public void clearData() {

    }
}
