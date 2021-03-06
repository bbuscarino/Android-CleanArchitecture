package com.company.app.domain;

import com.google.common.base.Optional;

import org.immutables.value.Value;


@DomainStyle
@Value.Immutable
public interface AbstractUser {
    int userId();

    Optional<String> coverUrl();

    Optional<String> fullName();

    Optional<String> email();

    Optional<String> description();

    Optional<Integer> followers();
}
