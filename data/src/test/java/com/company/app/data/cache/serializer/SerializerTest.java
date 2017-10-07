package com.company.app.data.cache.serializer;

import com.company.app.data.entity.GsonAdaptersAbstractUserEntity;
import com.company.app.data.entity.UserEntity;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class SerializerTest {

    private static final String JSON_RESPONSE = "{\n"
            + "    \"id\": 1,\n"
            + "    \"cover_url\": \"http://www.android10.org/myapi/cover_1.jpg\",\n"
            + "    \"full_name\": \"Simon Hill\",\n"
            + "    \"description\": \"Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.\\n\\nInteger tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.\\n\\nPraesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.\",\n"
            + "    \"followers\": 7484,\n"
            + "    \"email\": \"jcooper@babbleset.edu\"\n"
            + "}";

    private Serializer serializer;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersAbstractUserEntity());
        serializer = new Serializer(gsonBuilder.create());
    }

    @Test
    public void testSerializeHappyCase() {
        final UserEntity userEntityOne = serializer.deserialize(JSON_RESPONSE, UserEntity.class);
        final String jsonString = serializer.serialize(userEntityOne, UserEntity.class);
        final UserEntity userEntityTwo = serializer.deserialize(jsonString, UserEntity.class);

        assertThat(userEntityOne.fullName().isPresent()).isTrue();
        assertThat(userEntityOne.followers().isPresent()).isTrue();
        assertThat(userEntityOne.coverUrl().isPresent()).isTrue();
        assertThat(userEntityOne.description().isPresent()).isTrue();
        assertThat(userEntityOne.email().isPresent()).isTrue();

        assertThat(userEntityTwo.fullName().isPresent()).isTrue();
        assertThat(userEntityTwo.followers().isPresent()).isTrue();
        assertThat(userEntityTwo.coverUrl().isPresent()).isTrue();
        assertThat(userEntityTwo.description().isPresent()).isTrue();
        assertThat(userEntityTwo.email().isPresent()).isTrue();

        assertThat(userEntityTwo.userId()).isEqualTo(userEntityOne.userId());
        assertThat(userEntityTwo.fullName().get()).isEqualToNormalizingWhitespace(userEntityOne.fullName().get());
        assertThat(userEntityTwo.coverUrl().get()).isEqualToNormalizingWhitespace(userEntityOne.coverUrl().get());
        assertThat(userEntityTwo.description().get()).isEqualToNormalizingWhitespace(userEntityOne.description().get());
        assertThat(userEntityTwo.email().get()).isEqualToNormalizingWhitespace(userEntityOne.email().get());
    }

    @Test
    public void testDesearializeHappyCase() {
        final UserEntity userEntity = serializer.deserialize(JSON_RESPONSE, UserEntity.class);

        assertThat(userEntity.fullName().isPresent()).isTrue();
        assertThat(userEntity.followers().isPresent()).isTrue();
        assertThat(userEntity.coverUrl().isPresent()).isTrue();
        assertThat(userEntity.description().isPresent()).isTrue();
        assertThat(userEntity.email().isPresent()).isTrue();


        assertThat(userEntity.userId()).isEqualTo(1);
        assertThat(userEntity.fullName().get()).isEqualToNormalizingWhitespace("Simon Hill");
        assertThat(userEntity.followers().get()).isEqualTo(7484);
        assertThat(userEntity.coverUrl().get()).isEqualToNormalizingWhitespace("http://www.android10.org/myapi/cover_1.jpg");
        assertThat(userEntity.description().get()).isNotEmpty();
        assertThat(userEntity.email().get()).isEqualToNormalizingWhitespace("jcooper@babbleset.edu");
    }
}
