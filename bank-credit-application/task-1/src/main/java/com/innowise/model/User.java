package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.model.enums.SexType;
import lombok.*;

@Getter
@Setter
@ToString
public class User {

    private Long id;
    private String name;
    private String secondName;

    @SerializedName("sex")
    private SexType sexType;

}
