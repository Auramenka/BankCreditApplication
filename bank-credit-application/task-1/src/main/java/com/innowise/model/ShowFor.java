package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.model.enums.ShowForType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ShowFor {

    @SerializedName("type")
    private ShowForType showForType;
    private List<String> users;

}
