package com.sundararaghavan.deliveryapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sundararaghavan.deliveryapp.model.Repo;
import com.sundararaghavan.deliveryapp.network.RepoService;
import com.sundararaghavan.deliveryapp.repository.DeliveryRepository;
import com.sundararaghavan.deliveryapp.repository.DeliveryRepositoryImpl;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class DeliveriesViewModel extends ViewModel {

    private DeliveryRepository deliveryRepository;

    private Call<List<Repo>> repoCall;

    @Inject
    DeliveriesViewModel(Application application, RepoService repoService) {
        deliveryRepository = DeliveryRepositoryImpl.create(application, repoService);
    }

    public LiveData<List<Repo>> getRepos() {
        return deliveryRepository.getRepos();
    }

    public LiveData<Boolean> getError() {
        return deliveryRepository.getError();
    }

    public LiveData<Boolean> getLoading() {
        return deliveryRepository.getLoading();
    }

    public void fetchRepos(Integer id, Integer items) {
        deliveryRepository.fetch(id, items);

    }

    public void clear() {
        deliveryRepository.clear();
    }

    @Override
    protected void onCleared() {
        if (repoCall != null) {
            repoCall.cancel();
            repoCall = null;
        }
    }
}
