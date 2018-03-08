/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.test.tutorial.jee.runner;

/**
 *
 * @author Godswill
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MongoDataSourceInvocationHandler implements InvocationHandler {

    private Object obj;

    public MongoDataSourceInvocationHandler(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result;
        try {
            if (m.getName().indexOf("get") > -1) {
                System.out.println("...get Method Executing...");
            } else {
                System.out.println("...set Method Executing...");
            }
            result = m.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return result;
    }
}
