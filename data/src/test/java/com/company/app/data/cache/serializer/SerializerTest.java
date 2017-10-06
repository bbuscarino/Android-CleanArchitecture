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
package com.company.app.data.cache.serializer;

import com.company.app.data.entity.GsonAdaptersAbstractUserEntity;
import com.company.app.data.entity.UserEntity;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

        assertThat(userEntityOne.userId(), is(userEntityTwo.userId()));
        assertThat(userEntityOne.fullname(), is(equalTo(userEntityTwo.fullname())));
        assertThat(userEntityOne.followers(), is(userEntityTwo.followers()));
    }

    @Test
    public void testDesearializeHappyCase() {
        final UserEntity userEntity = serializer.deserialize(JSON_RESPONSE, UserEntity.class);

        assertThat(userEntity.userId(), is(1));
        assertThat(userEntity.fullname(), is("Simon Hill"));
        assertThat(userEntity.followers(), is(7484));
    }
}
