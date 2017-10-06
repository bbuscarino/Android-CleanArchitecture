/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.app.data.repository.datasource;

import com.company.app.data.cache.UserCache;
import com.company.app.data.entity.UserEntity;
import com.company.app.data.net.GithubService;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
class CloudUserDataStore implements UserDataStore {

    private final GithubService githubService;
    private final UserCache userCache;

    /**
     * Construct a {@link UserDataStore} based on connections to the api (Cloud).
     *
     * @param githubService The {@link GithubService} implementation to use.
     * @param userCache     A {@link UserCache} to cache data retrieved from the api.
     */
    CloudUserDataStore(GithubService githubService, UserCache userCache) {
        this.githubService = githubService;
        this.userCache = userCache;
    }

    @Override
    public Single<List<UserEntity>> userEntityList() {
        return Single.create((emitter) -> this.githubService.listUsers().enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                emitter.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                emitter.onError(t);
            }
        }));
    }

    @Override
    public Single<UserEntity> userEntityDetails(final int userId) {
        return Single.<UserEntity>create((emitter) -> this.githubService.getUserEntityById(userId).enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                emitter.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
                emitter.onError(t);
            }
        })).doOnSuccess(CloudUserDataStore.this.userCache::put);
    }
}
