package com.company.app.data.repository.datasource;

import com.company.app.data.cache.UserCache;
import com.company.app.data.entity.UserEntity;
import com.company.app.data.net.GithubService;
import com.google.common.collect.Iterables;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

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

    private final NetworkBehavior behavior = NetworkBehavior.create();

    private GithubService mockGithubService;

    @Mock
    private UserCache mockUserCache;

    @Before
    public void setUp() {
        // Create fake entity
        fakeUserEntity = new UserEntity.Builder()
                .setUserId(FAKE_USER_ID)
                .setCoverUrl(FAKE_USER_COVER_URL)
                .setDescription(FAKE_USER_DESCRIPTION)
                .setEmail(FAKE_USER_EMAIL)
                .setFollowers(FAKE_USER_FOLLOWERS)
                .setFullName(FAKE_USER_FULL_NAME)
                .create();
        // Mock Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/").build();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior).build();
        final BehaviorDelegate<GithubService> delegate = mockRetrofit.create(GithubService.class);
        mockGithubService = new GithubServiceMock(delegate, fakeUserEntity);

        cloudUserDataStore = new CloudUserDataStore(mockGithubService, mockUserCache);

    }

    @Test
    public void testGetUserEntityListFromApi() {
        cloudUserDataStore.userEntityList().blockingGet();
    }

    @Test
    public void testGetUserEntityDetailsFromApi() {
        cloudUserDataStore.userEntityDetails(FAKE_USER_ID).blockingGet();
    }


    private class GithubServiceMock implements GithubService {
        private final BehaviorDelegate<GithubService> delegate;
        private final UserEntity user;

        GithubServiceMock(BehaviorDelegate<GithubService> delegate, UserEntity testUser) {
            this.delegate = delegate;
            this.user = testUser;
        }

        @Override
        public Call<List<UserEntity>> listUsers() {
            return delegate.returningResponse(Collections.singletonList(user)).listUsers();
        }

        @Override
        public Call<UserEntity> getUserEntityById(int id) {
            return delegate.returningResponse(user).getUserEntityById(id);
        }
    }
}
