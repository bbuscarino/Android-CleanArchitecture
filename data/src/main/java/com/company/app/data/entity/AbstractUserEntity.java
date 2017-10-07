package com.company.app.data.entity;


import com.google.common.base.Optional;
import com.google.gson.annotations.SerializedName;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@EntityStyle
@Gson.TypeAdapters
@Value.Immutable
public interface AbstractUserEntity {
    @SerializedName("id")
    int userId();

    @SerializedName("cover_url")
    Optional<String> coverUrl();

    @SerializedName("full_name")
    Optional<String> fullName();

    @SerializedName("description")
    Optional<String> description();

    @SerializedName("followers")
    Optional<Integer> followers();

    @SerializedName("email")
    Optional<String> email();
}
