package com.seamfix.test.tutorial.jee.runner.inject;

import com.seamfix.test.tutorial.jee.runner.SingletonNoSqlDataSource;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

// Inject a data-source to @Inject annotated member variables with the appropriate class
public class InjectInjector extends BaseInjector {

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return Inject.class;
    }

    @Override
    public <T> void doInject(T target, Field field) {
        Class<?> fieldClass = field.getType();
        if (fieldClass.getSimpleName().equalsIgnoreCase("NoSqlDataSource")) {
            InjectionUtils.assignObjectToField(target, field, SingletonNoSqlDataSource.getNoSqlDataSource());
        } else {
            final Object objectToInject = findInstanceByClass(field.getType());
            InjectionUtils.assignObjectToField(target, field, objectToInject);
        }
    }
}
