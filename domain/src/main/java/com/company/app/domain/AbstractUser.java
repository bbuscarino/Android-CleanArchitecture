package com.company.app.domain;

import org.immutables.value.Value;

@DomainStyle
@Value.Immutable
public interface AbstractUser {
    String coverUrl();

    String fullName();

    String email();

    String description();

    int followers();
}
