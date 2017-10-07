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
import com.google.common.base.Preconditions;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture) that returns a single piece of data.
 * This interface represents a execution unit for different use cases.
 *
 * By convention each SingleUseCase implementation will return the result using a {@link DisposableSingleObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class SingleUseCase<T, Params> {

  private final ThreadExecutor threadExecutor;
  private final PostExecutionThread postExecutionThread;
  private final CompositeDisposable disposables;

  SingleUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
    this.threadExecutor = threadExecutor;
    this.postExecutionThread = postExecutionThread;
    this.disposables = new CompositeDisposable();
  }

  /**
   * Builds an {@link Observable} which will be used when executing the current {@link SingleUseCase}.
   */
  abstract Single<T> buildUseCaseSingle(Params params);

  /**
   * Executes the current use case.
   *
   * @param observer {@link DisposableSingleObserver} which will be listening to the single build
   * by {@link #buildUseCaseSingle(Params)} ()} method.
   * @param params Parameters (Optional) used to build/execute this use case.
   */
  public void execute(DisposableSingleObserver<T> observer, Params params) {
    Preconditions.checkNotNull(observer);
    final Single<T> single = this.buildUseCaseSingle(params)
        .subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutionThread.getScheduler());
    addDisposable(single.subscribeWith(observer));
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.dispose();
    }
  }

  /**
   * Dispose from current {@link CompositeDisposable}.
   */
  private void addDisposable(Disposable disposable) {
    Preconditions.checkNotNull(disposable);
    Preconditions.checkNotNull(disposables);
    disposables.add(disposable);
  }
}
