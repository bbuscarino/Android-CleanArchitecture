/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.app.test.view.activity;

import android.app.Fragment;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.company.app.presentation.view.activity.UserDetailsActivity;
import com.fernandocejas.android10.sample.presentation.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class UserDetailsActivityTest {

  @Rule
  public ActivityTestRule<UserDetailsActivity> mUserDetailsActivityRule
          = new ActivityTestRule<>(UserDetailsActivity.class);

  @Test
  public void testContainsUserDetailsFragment() {
    UserDetailsActivity activity = mUserDetailsActivityRule.getActivity();
    Fragment userDetailsFragment =
            activity.getFragmentManager().findFragmentById(R.id.fragmentContainer);
    assertThat(userDetailsFragment, is(notNullValue()));
  }

  @Test
  public void testContainsProperTitle() {
    UserDetailsActivity activity = mUserDetailsActivityRule.getActivity();

    String actualTitle = activity.getTitle().toString().trim();

    assertThat(actualTitle, is("OldUser Details"));
  }

  @Test
  public void testLoadUserHappyCaseViews() {
    onView(withId(R.id.rl_retry)).check(matches(not(isDisplayed())));
    onView(withId(R.id.rl_progress)).check(matches(not(isDisplayed())));

    onView(withId(R.id.tv_fullname)).check(matches(isDisplayed()));
    onView(withId(R.id.tv_email)).check(matches(isDisplayed()));
    onView(withId(R.id.tv_description)).check(matches(isDisplayed()));
  }

  @Test
  public void testLoadUserHappyCaseData() {
    onView(withId(R.id.tv_fullname)).check(matches(withText("John Sanchez")));
    onView(withId(R.id.tv_email)).check(matches(withText("dmedina@katz.edu")));
    onView(withId(R.id.tv_followers)).check(matches(withText("4523")));
  }
}
