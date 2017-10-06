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
package com.company.app.data.repository;

import com.company.app.data.entity.UserEntity;
import com.company.app.data.repository.datasource.UserDataStore;
import com.company.app.data.repository.datasource.UserDataStoreFactory;
import com.company.app.domain.User;
import com.company.app.domain.mapper.Mapper;
import com.company.app.domain.repository.UserRepository;

import io.reactivex.Single;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

    private final UserDataStoreFactory userDataStoreFactory;
    private final Mapper<UserEntity, User> userMapper;

    /**
     * Constructs a {@link UserRepository}.
     *
     * @param dataStoreFactory     A factory to construct different data source implementations.
     * @param userMapper {@link Mapper}.
     */
    @Inject
    UserDataRepository(UserDataStoreFactory dataStoreFactory, Mapper<UserEntity, User> userMapper) {
        this.userDataStoreFactory = dataStoreFactory;
        this.userMapper = userMapper;
    }

    @Override
    public Single<List<User>> users() {
        //we always get all users from the cloud
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.userEntityList().map(this.userMapper::mapTo);
    }

    @Override
    public Single<User> user(int userId) {
        final UserDataStore userDataStore = this.userDataStoreFactory.create(userId);
        return userDataStore.userEntityDetails(userId).map(this.userMapper::mapTo);
    }
}
