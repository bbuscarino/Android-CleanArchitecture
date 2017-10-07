package com.company.app.presentation.internal.di.modules;

import android.content.Context;

import com.company.app.data.entity.GsonAdaptersAbstractUserEntity;
import com.company.app.data.entity.UserEntity;
import com.company.app.data.net.GithubService;
import com.company.app.domain.User;
import com.company.app.domain.mapper.Mapper;
import com.company.app.presentation.AndroidApplication;
import com.company.app.presentation.UIThread;
import com.company.app.data.cache.UserCache;
import com.company.app.data.cache.UserCacheImpl;
import com.company.app.data.executor.JobExecutor;
import com.company.app.data.repository.UserDataRepository;
import com.company.app.domain.executor.PostExecutionThread;
import com.company.app.domain.executor.ThreadExecutor;
import com.company.app.domain.repository.UserRepository;
import com.company.app.presentation.model.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserCache provideUserCache(UserCacheImpl userCache) {
        return userCache;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersAbstractUserEntity())
                .create();
    }

    @Provides
    @Singleton
    GithubService provideGithubService(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(GithubService.class);
    }

    @Provides
    @Singleton
    Mapper<UserEntity, User> provideUserEntityMapper() {
        return new Mapper<UserEntity, User>() {
            @Override
            public User mapTo(UserEntity userEntity) {
                return new User.Builder()
                        .setUserId(userEntity.userId())
                        .setFullName(userEntity.fullName())
                        .setFollowers(userEntity.followers())
                        .setDescription(userEntity.description())
                        .setCoverUrl(userEntity.coverUrl())
                        .setEmail(userEntity.email())
                        .create();
            }

            @Override
            public List<User> mapTo(List<UserEntity> t) {
                return Observable.fromIterable(t)
                        .map(this::mapTo)
                        .toList().blockingGet();
            }

            @Override
            public UserEntity mapFrom(User user) {
                return new UserEntity.Builder()
                        .setUserId(user.userId())
                        .setFullName(user.fullName())
                        .setFollowers(user.followers())
                        .setDescription(user.description())
                        .setCoverUrl(user.coverUrl())
                        .setEmail(user.email())
                        .create();
            }

            @Override
            public List<UserEntity> mapFrom(List<User> u) {
                return Observable.fromIterable(u)
                        .map(this::mapFrom)
                        .toList().blockingGet();
            }
        };
    }




}
