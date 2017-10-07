package com.company.app.presentation.view;

import com.company.app.presentation.model.UserModel;

import java.util.List;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link UserModel}.
 */
public interface UserListView extends LoadDataView {
    /**
     * Render a user list in the UI.
     *
     * @param userModelList The list of {@link UserModel} that will be shown.
     */
    void renderUserList(List<UserModel> userModelList);

    /**
     * View a {@link UserModel} profile/details.
     *
     * @param userModel The user that will be shown.
     */
    void viewUser(UserModel userModel);
}
