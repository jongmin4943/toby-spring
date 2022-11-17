package com.byultudy.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
