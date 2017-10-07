package com.company.app.presentation.internal.di.modules;

import com.company.app.data.net.GithubService;
import com.company.app.domain.User;
import com.company.app.domain.mapper.Mapper;
import com.company.app.presentation.model.UserModel;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

    public UserModule() {
    }

    @Provides
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/")
                .build().create(GithubService.class);

    }

    @Provides
    Mapper<UserModel, User> provideUserModelMapper() {
        return new Mapper<UserModel, User>() {
            @Override
            public User mapTo(UserModel userEntity) {
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
            public List<User> mapTo(List<UserModel> t) {
                return Observable.fromIterable(t)
                        .map(this::mapTo)
                        .toList().blockingGet();
            }

            @Override
            public UserModel mapFrom(User user) {
                return new UserModel.Builder()
                        .setUserId(user.userId())
                        .setFullName(user.fullName())
                        .setFollowers(user.followers())
                        .setDescription(user.description())
                        .setCoverUrl(user.coverUrl())
                        .setEmail(user.email())
                        .create();
            }

            @Override
            public List<UserModel> mapFrom(List<User> u) {
                return Observable.fromIterable(u)
                        .map(this::mapFrom)
                        .toList().blockingGet();
            }
        };
    }
}
