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
package com.company.app.domain.interactor;

import com.company.app.domain.User;
import com.company.app.domain.executor.PostExecutionThread;
import com.company.app.domain.executor.ThreadExecutor;
import com.company.app.domain.repository.UserRepository;
import com.google.common.base.Preconditions;

import io.reactivex.Single;

import javax.inject.Inject;

public class GetUserDetails extends SingleUseCase<User, GetUserDetails.Params> {

    private final UserRepository userRepository;

    @Inject
    GetUserDetails(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    Single<User> buildUseCaseSingle(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.user(params.userId);
    }

    public static final class Params {

        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
