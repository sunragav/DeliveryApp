package com.sundararaghavan.deliveryapp.network;

import com.sundararaghavan.deliveryapp.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepoService {
    @GET("/deliveries")
    Call<List<Repo>> getRepositories(@Query("offset") Integer id, @Query("limit") Integer limit);

}
