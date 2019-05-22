/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.test.tutorial.jee.runner;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;

/**
 *
 * @author Uchechukwu Onuoha <yoursuche@gmail.com>
 */
public interface NoSqlDataSource {

    MongoClient getMogoDbClient() ;

    Datastore getMongoDbDatastore();

    MongoDatabase getDatabaseConnection() ;

}
