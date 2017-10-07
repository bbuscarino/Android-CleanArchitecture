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
package com.company.app.test.mapper;

import com.company.app.presentation.mapper.UserModelDataMapper;
import com.company.app.presentation.model.OldUserModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class UserModelDataMapperTest extends TestCase {

  private static final int FAKE_USER_ID = 123;
  private static final String FAKE_FULL_NAME = "Tony Stark";

  private UserModelDataMapper userModelDataMapper;

  @Override protected void setUp() throws Exception {
    super.setUp();
    userModelDataMapper = new UserModelDataMapper();
  }

  public void testTransformUser() {
    OldUser user = createFakeUser();
    OldUserModel userModel = userModelDataMapper.transform(user);

    assertThat(userModel, is(instanceOf(OldUserModel.class)));
    assertThat(userModel.getUserId(), is(FAKE_USER_ID));
    assertThat(userModel.getFullName(), is(FAKE_FULL_NAME));
  }

  public void testTransformUserCollection() {
    OldUser mockUserOne = mock(OldUser.class);
    OldUser mockUserTwo = mock(OldUser.class);

    List<OldUser> userList = new ArrayList<OldUser>(5);
    userList.add(mockUserOne);
    userList.add(mockUserTwo);

    Collection<OldUserModel> userModelList = userModelDataMapper.transform(userList);

    assertThat(userModelList.toArray()[0], is(instanceOf(OldUserModel.class)));
    assertThat(userModelList.toArray()[1], is(instanceOf(OldUserModel.class)));
    assertThat(userModelList.size(), is(2));
  }

  private OldUser createFakeUser() {
    OldUser user = new OldUser(FAKE_USER_ID);
    user.setFullName(FAKE_FULL_NAME);

    return user;
  }
}
