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

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.DisposableSubscriber;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FlowableUseCaseTest {

  private FlowableUseCaseTestClass useCase;

  private TestDisposableObserver<Object> testObserver;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    this.useCase = new FlowableUseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
    this.testObserver = new TestDisposableObserver<>();
    given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
  }

  @Test
  public void testBuildUseCaseObservableReturnCorrectResult() {
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

  private static class FlowableUseCaseTestClass extends FlowableUseCase<Object, Params> {

    FlowableUseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
      super(threadExecutor, postExecutionThread);
    }

    @Override
    Flowable<Object> buildUseCaseFlowable(Params params) {
      return Flowable.empty();
    }

    @Override
    public void execute(DisposableSubscriber<Object> observer, Params params) {
      super.execute(observer, params);
    }
  }

  private static class TestDisposableObserver<T> extends DisposableSubscriber<T> {
    private int valuesCount = 0;

    @Override public void onNext(T value) {
      valuesCount++;
    }

    @Override public void onError(Throwable e) {
      // no-op by default.
    }

    @Override public void onComplete() {
      // no-op by default.
    }
  }

  private static class Params {
    private static Params EMPTY = new Params();
    private Params() {}
  }
}
