package com.sundararaghavan.deliveryapp.screens.deliverylist.view;

import android.support.v7.util.DiffUtil;

import com.sundararaghavan.deliveryapp.model.Repo;

import java.util.List;

public class RepoDiffCallback extends DiffUtil.Callback {
    private final List<Repo> oldRepo;
    private final List<Repo> newRepo;

    RepoDiffCallback(List<Repo> oldRepo, List<Repo> newRepo) {
        this.oldRepo = oldRepo;
        this.newRepo = newRepo;
    }

    @Override
    public int getOldListSize() {
        return oldRepo.size();
    }

    @Override
    public int getNewListSize() {
        return newRepo.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRepo.get(oldItemPosition) == newRepo.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRepo.get(oldItemPosition).equals(newRepo.get(newItemPosition));
    }
}