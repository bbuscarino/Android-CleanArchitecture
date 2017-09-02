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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SingleUseCaseTest {

  private SingleUseCaseTestClass useCase;

  private TestDisposableSingleObserver<Object> testObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    this.useCase = new SingleUseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
    this.testObserver = new TestDisposableSingleObserver<>();
    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  @Test
  public void testBuildUseCaseSingleReturnCorrectResult() {
    useCase.execute(testObserver, Params.EMPTY);

    assertThat(testObserver.valuesCount).isZero();
  }

  @Test
  public void testSubscriptionWhenExecutingUseCase() {
    useCase.execute(testObserver, Params.EMPTY);
    useCase.dispose();

    assertThat(testObserver.isDisposed()).isTrue();
  }

  @Test
  public void testShouldFailWhenExecuteWithNullObserver() {
    expectedException.expect(NullPointerException.class);
    useCase.execute(null, Params.EMPTY);
  }

  private static class SingleUseCaseTestClass extends SingleUseCase<Object, Params> {

    SingleUseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override
    Single<Object> buildUseCaseSingle(Params params) {
      return Single.never();
    }

    @Override
    public void execute(DisposableSingleObserver<Object> observer, Params params) {
      super.execute(observer, params);
    }
  }

  private static class TestDisposableSingleObserver<T> extends DisposableSingleObserver<T> {
    int valuesCount = 0;
    @Override public void onError(Throwable e) {
      // no-op by default.
    }

    @Override
    public void onSuccess(T value) {
      valuesCount++;
    }
  }

  private static class Params {
    private static Params EMPTY = new Params();
    private Params() {}
  }
}
