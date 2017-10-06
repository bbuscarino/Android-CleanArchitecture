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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudUserDataStoreTest {

    private static final int FAKE_USER_ID = 765;
    private static final String FAKE_USER_COVER_URL = "http://www.yolo.org/420";
    private static final String FAKE_USER_DESCRIPTION = "my name jeff";
    private static final String FAKE_USER_EMAIL = "jeff@yolo.org";
    private static final int FAKE_USER_FOLLOWERS = 69;
    private static final String FAKE_USER_FULL_NAME = "Jeff Ree";

    private CloudUserDataStore cloudUserDataStore;
    private UserEntity fakeUserEntity;


    @Mock
    private GithubService mockGithubService;
    @Mock
    private UserCache mockUserCache;

    @Before
    public void setUp() {
        cloudUserDataStore = new CloudUserDataStore(mockGithubService, mockUserCache);
        fakeUserEntity = new UserEntity.Builder()
                .setUserId(FAKE_USER_ID)
                .setCoverUrl(FAKE_USER_COVER_URL)
                .setDescription(FAKE_USER_DESCRIPTION)
                .setEmail(FAKE_USER_EMAIL)
                .setFollowers(FAKE_USER_FOLLOWERS)
                .setFullname(FAKE_USER_FULL_NAME)
                .create();
    }

    @Test
    public void testGetUserEntityListFromApi() {
        cloudUserDataStore.userEntityList();
        verify(mockGithubService).listUsers();
    }

    @Test
    public void testGetUserEntityDetailsFromApi() throws IOException {
        given(mockGithubService.getUserEntityById(FAKE_USER_ID).execute().body()).willReturn(fakeUserEntity);

        cloudUserDataStore.userEntityDetails(FAKE_USER_ID);

        verify(mockGithubService).getUserEntityById(FAKE_USER_ID);
    }
}
