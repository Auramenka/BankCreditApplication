package com.innowise.strategy;

import com.innowise.model.Settings;

import java.util.function.Predicate;

public interface DateFilterStrategy<T> {

    Predicate<T> isValid(Settings settings);

}
