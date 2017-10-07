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
package com.company.app.presentation.presenter;

import android.support.annotation.NonNull;

import com.company.app.domain.User;
import com.company.app.domain.mapper.Mapper;
import com.company.app.presentation.model.UserModel;
import com.company.app.presentation.view.UserListView;
import com.company.app.domain.exception.DefaultErrorBundle;
import com.company.app.domain.exception.ErrorBundle;
import com.company.app.domain.interactor.GetUserList;
import com.company.app.presentation.exception.ErrorMessageFactory;
import com.company.app.presentation.internal.di.PerActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class UserListPresenter implements Presenter {

    private UserListView viewListView;

    private final GetUserList getUserListUseCase;
    private final Mapper<UserModel, User> userModelMapper;

    @Inject
    public UserListPresenter(GetUserList getUserListUserCase,
                             Mapper<UserModel, User> userModelMapper) {
        this.getUserListUseCase = getUserListUserCase;
        this.userModelMapper = userModelMapper;
    }

    public void setView(@NonNull UserListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getUserListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    public void onUserClicked(UserModel userModel) {
        this.viewListView.viewUser(userModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.context(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showUsersCollectionInView(List<User> usersCollection) {
        final List<UserModel> userModelsCollection =
                this.userModelMapper.mapFrom(usersCollection);
        this.viewListView.renderUserList(userModelsCollection);
    }

    private void getUserList() {
        this.getUserListUseCase.execute(new UserListObserver(), null);
    }

    private final class UserListObserver extends DisposableSingleObserver<List<User>> {
        @Override
        public void onSuccess(List<User> users) {
            UserListPresenter.this.showUsersCollectionInView(users);
            UserListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            UserListPresenter.this.hideViewLoading();
            UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            UserListPresenter.this.showViewRetry();
        }
    }
}
