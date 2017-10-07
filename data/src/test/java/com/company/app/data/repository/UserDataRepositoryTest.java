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

import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserDataRepositoryTest {

    private static final int FAKE_USER_ID = 123;
    private static final String FAKE_USER_COVER_URL = "http://www.yolo.org/420";
    private static final String FAKE_USER_DESCRIPTION = "my name jeff";
    private static final String FAKE_USER_EMAIL = "jeff@yolo.org";
    private static final int FAKE_USER_FOLLOWERS = 69;
    private static final String FAKE_USER_FULL_NAME = "Jeff Ree";

    private UserDataRepository userDataRepository;
    private UserEntity fakeUserEntity;

    @Mock
    private UserDataStoreFactory mockUserDataStoreFactory;
    @Mock
    private Mapper<UserEntity, User> mockUserEntityDataMapper;
    @Mock
    private UserDataStore mockUserDataStore;

    @Before
    public void setUp() {
        userDataRepository = new UserDataRepository(mockUserDataStoreFactory, mockUserEntityDataMapper);
        given(mockUserDataStoreFactory.create(anyInt())).willReturn(mockUserDataStore);
        given(mockUserDataStoreFactory.createCloudDataStore()).willReturn(mockUserDataStore);
        fakeUserEntity = new UserEntity.Builder()
                .setUserId(FAKE_USER_ID)
                .setCoverUrl(FAKE_USER_COVER_URL)
                .setDescription(FAKE_USER_DESCRIPTION)
                .setEmail(FAKE_USER_EMAIL)
                .setFollowers(FAKE_USER_FOLLOWERS)
                .setFullName(FAKE_USER_FULL_NAME)
                .create();
    }

    @Test
    public void testGetUsersHappyCase() {
        List<UserEntity> usersList = new ArrayList<>();
        usersList.add(fakeUserEntity);
        given(mockUserDataStore.userEntityList()).willReturn(Single.just(usersList));

        userDataRepository.users();

        verify(mockUserDataStoreFactory).createCloudDataStore();
        verify(mockUserDataStore).userEntityList();
    }

    @Test
    public void testGetUserHappyCase() {

        given(mockUserDataStore.userEntityDetails(FAKE_USER_ID)).willReturn(Single.just(fakeUserEntity));
        userDataRepository.user(FAKE_USER_ID);

        verify(mockUserDataStoreFactory).create(FAKE_USER_ID);
        verify(mockUserDataStore).userEntityDetails(FAKE_USER_ID);
    }
}
