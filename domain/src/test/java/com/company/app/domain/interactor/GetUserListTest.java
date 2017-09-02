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
package com.company.app.domain.interactor;

import com.company.app.domain.executor.PostExecutionThread;
import com.company.app.domain.executor.ThreadExecutor;
import com.company.app.domain.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetUserListTest {

  private GetUserList getUserList;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private UserRepository mockUserRepository;

  @Before
  public void setUp() {
    getUserList = new GetUserList(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseObservableHappyCase() {
    getUserList.buildUseCaseSingle(null);

    verify(mockUserRepository).users();
    verifyNoMoreInteractions(mockUserRepository);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
