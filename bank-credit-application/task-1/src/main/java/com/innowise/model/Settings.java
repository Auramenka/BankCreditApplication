package com.innowise.model;

import com.google.gson.annotations.SerializedName;
import com.innowise.model.enums.SortByType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class Settings {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private ShowFor showFor;

    @SerializedName("sortBy")
    private SortByType sortByType;
    private List<String> useDepartments;
    private BigDecimal startCostEUR;
    private BigDecimal startCostUSD;

}
