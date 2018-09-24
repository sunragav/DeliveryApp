package com.sundararaghavan.deliveryapp.persistence.entity;

import com.sundararaghavan.deliveryapp.model.Location;
import com.sundararaghavan.deliveryapp.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class RepoEnityModelMapper {
    public static List<RepoEntity> transformModelsToEntities(List<Repo> repos) {
        List<RepoEntity> repoEnities = null;
        if (repos != null && repos.size() > 0) {
            repoEnities = new ArrayList<>();
            for (Repo repo : repos) {
                RepoEntity re = new RepoEntity();
                re.setAddress(repo.location().address());
                re.setLat(repo.location().lat());
                re.setLng(repo.location().lng());
                re.setDescription(repo.description());
                re.setId(repo.id());
                re.setImageUrl(repo.imageUrl());
                repoEnities.add(re);
            }
        }
        return repoEnities;
    }

    public static List<Repo> transformEntitiesToModels(List<RepoEntity> repoEntities) {
        List<Repo> repos = null;
        if (repoEntities != null && repoEntities.size() > 0) {
            repos = new ArrayList<>();
            for (RepoEntity repoEntity : repoEntities) {
                Repo repo = Repo.builder()
                        .setId(repoEntity.getId())
                        .setDescription(repoEntity.getDescription())
                        .setImageUrl(repoEntity.getImageUrl())
                        .setLocation(Location.builder()
                                .setAddress(repoEntity.getAddress())
                                .setLat(repoEntity.getLat())
                                .setLng(repoEntity.getLng())
                                .build())
                        .build();
                repos.add(repo);
            }
        }
        return repos;
    }
}
