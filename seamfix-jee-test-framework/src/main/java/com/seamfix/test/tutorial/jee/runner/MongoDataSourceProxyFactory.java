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

import java.lang.reflect.Proxy;

public class MongoDataSourceProxyFactory {

    public static Object newInstance(Object ob) {
        return Proxy.newProxyInstance(ob.getClass().getClassLoader(),
                new Class<?>[]{NoSqlDataSource.class}, new MongoDataSourceInvocationHandler(ob));
    }

}
