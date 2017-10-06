package com.company.app.data.entity;


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
    String coverUrl();

    @SerializedName("full_name")
    String fullname();

    @SerializedName("description")
    String description();

    @SerializedName("followers")
    int followers();

    @SerializedName("email")
    String email();
}
