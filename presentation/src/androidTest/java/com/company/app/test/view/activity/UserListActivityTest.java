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
package com.company.app.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.fernandocejas.android10.sample.presentation.R;
import com.company.app.presentation.view.activity.UserListActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserListActivityTest {

    @Rule
    public ActivityTestRule<UserListActivity> mActivityRule =
            new ActivityTestRule<>(UserListActivity.class);

    @Test
    public void testContainsUserListFragment() {
        UserListActivity userListActivity = mActivityRule.getActivity();
        Fragment userListFragment =
                userListActivity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
        assertThat(userListFragment, is(notNullValue()));
    }

    @Test
    public void testContainsProperTitle() {
        UserListActivity userListActivity = mActivityRule.getActivity();
        String actualTitle = userListActivity.getTitle().toString().trim();
        assertThat(actualTitle, is("Users List"));
    }
}
