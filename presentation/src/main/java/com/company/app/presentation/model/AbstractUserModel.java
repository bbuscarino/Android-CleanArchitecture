package com.company.app.presentation.model;

import com.google.common.base.Optional;

import org.immutables.value.Value;


@ModelStyle
@Value.Immutable
public interface AbstractUserModel {
    int userId();

    Optional<String> coverUrl();

    Optional<String> fullName();

    Optional<String> email();

    Optional<String> description();

    Optional<Integer> followers();
}
