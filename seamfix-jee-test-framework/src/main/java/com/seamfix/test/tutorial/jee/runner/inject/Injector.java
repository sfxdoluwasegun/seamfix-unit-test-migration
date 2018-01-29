package com.seamfix.test.tutorial.jee.runner.inject;

import java.lang.reflect.Field;

public interface Injector {
    <T> void inject(T target, Field field);
    void reset();
}
