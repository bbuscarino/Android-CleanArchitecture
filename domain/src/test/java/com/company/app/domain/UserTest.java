package com.company.app.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private static final int FAKE_USER_ID = 420;
    private static final String FAKE_USER_COVER_URL = "http://www.yolo.org/420";
    private static final String FAKE_USER_DESCRIPTION = "my name jeff";
    private static final String FAKE_USER_EMAIL = "jeff@yolo.org";
    private static final int FAKE_USER_FOLLOWERS = 69;
    private static final String FAKE_USER_FULL_NAME = "Jeff Ree";

    private User user;

    @Before
    public void setUp() {
        user = new User.Builder()
                .setUserId(FAKE_USER_ID)
                .setCoverUrl(FAKE_USER_COVER_URL)
                .setDescription(FAKE_USER_DESCRIPTION)
                .setEmail(FAKE_USER_EMAIL)
                .setFollowers(FAKE_USER_FOLLOWERS)
                .setFullName(FAKE_USER_FULL_NAME)
                .create();
    }

    @Test
    public void testUserBuilderSuccess() {
        assertThat(user.userId()).isEqualTo(FAKE_USER_ID);

        assertThat(user.coverUrl().isPresent()).isTrue();
        assertThat(user.description().isPresent()).isTrue();
        assertThat(user.email().isPresent()).isTrue();
        assertThat(user.followers().isPresent()).isTrue();
        assertThat(user.fullName().isPresent()).isTrue();


        assertThat(user.coverUrl().get()).isEqualTo(FAKE_USER_COVER_URL);
        assertThat(user.description().get()).isEqualTo(FAKE_USER_DESCRIPTION);
        assertThat(user.email().get()).isEqualTo(FAKE_USER_EMAIL);
        assertThat(user.followers().get()).isEqualTo(FAKE_USER_FOLLOWERS);
        assertThat(user.fullName().get()).isEqualTo(FAKE_USER_FULL_NAME);
    }
}
