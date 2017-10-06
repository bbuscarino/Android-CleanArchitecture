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
package com.company.app.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private static final String FAKE_USER_COVER_URL = "http://www.yolo.org/420";
    private static final String FAKE_USER_DESCRIPTION = "my name jeff";
    private static final String FAKE_USER_EMAIL = "jeff@yolo.org";
    private static final int FAKE_USER_FOLLOWERS = 69;
    private static final String FAKE_USER_FULL_NAME = "Jeff Ree";

    private User user;

    @Before
    public void setUp() {
        user = new User.Builder()
                .setCoverUrl(FAKE_USER_COVER_URL)
                .setDescription(FAKE_USER_DESCRIPTION)
                .setEmail(FAKE_USER_EMAIL)
                .setFollowers(FAKE_USER_FOLLOWERS)
                .setFullName(FAKE_USER_FULL_NAME)
                .create();
    }

    @Test
    public void testUserBuilderSuccess() {
        assertThat(user.coverUrl()).isEqualTo(FAKE_USER_COVER_URL);
        assertThat(user.description()).isEqualTo(FAKE_USER_DESCRIPTION);
        assertThat(user.email()).isEqualTo(FAKE_USER_EMAIL);
        assertThat(user.followers()).isEqualTo(FAKE_USER_FOLLOWERS);
        assertThat(user.fullName()).isEqualTo(FAKE_USER_FULL_NAME);
    }
}
