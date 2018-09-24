package com.sundararaghavan.deliveryapp.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sundararaghavan.deliveryapp.R;
import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.network.RepoService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedRepoViewModel extends AndroidViewModel {

    private final MutableLiveData<Repo> selectedRepo = new MutableLiveData<>();
    private final RepoService repoService;
    private Application application;

    private Call<List<Repo>> repoCall;

    @Inject
    SelectedRepoViewModel(RepoService repoService, Application application) {
        super(application);
        this.repoService = repoService;
        this.application = application;
    }

    public LiveData<Repo> getSelectedRepo() {
        return selectedRepo;
    }

    public void setSelectedRepo(Repo repo) {
        selectedRepo.setValue(repo);
    }

    public void saveToBundle(Bundle outState) {
        if (selectedRepo.getValue() != null) {
            outState.putInt("repo_id",
                    selectedRepo.getValue().id());
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (selectedRepo.getValue() == null) {
            // We only care about restoring if we don't have a selected Repo set already
            if (savedInstanceState != null && savedInstanceState.containsKey("repo_id")) {
                loadRepo(savedInstanceState.getInt("repo_id"));
            }
        }
    }

    private void loadRepo(Integer id) {
        repoCall = repoService.getRepositories(id, 1);
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, @NonNull Response<List<Repo>> response) {
                selectedRepo.setValue(response.body().get(0));
                repoCall = null;
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), application.getResources().getString(R.string.api_error_repos), t);
                repoCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
