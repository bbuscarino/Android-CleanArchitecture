package com.company.app.data.net;

import com.company.app.data.entity.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users.json")
    Call<List<UserEntity>> listUsers();

    @GET("user_{id}.json")
    Call<UserEntity> getUserEntityById(@Path("id") int id);
}
