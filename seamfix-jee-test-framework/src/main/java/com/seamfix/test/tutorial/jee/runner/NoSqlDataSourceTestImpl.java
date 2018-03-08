/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seamfix.test.tutorial.jee.runner;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.sf.bio.common.ds.NoSqlDataSource;
import com.sf.bio.common.exception.BioCommonException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import javax.annotation.PreDestroy;
import lombok.Data;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Godswill
 */
@Data
public class NoSqlDataSourceTestImpl implements NoSqlDataSource {

    private String replicaConnectionUrl;
    private String password;
    private String mongoHost;
    private int mongoPort;
    private String username;
    private String adminDbName;
    private MongoClient mongoClient;
    private Properties monngoDbProperties;
    private Datastore mongoDataStore;

    public NoSqlDataSourceTestImpl() {

        Properties properties = new Properties();
        try {
            properties.load(NoSqlDataSourceTestImpl.class.getResourceAsStream("/META-INF/mongoDb.properties"));
            monngoDbProperties = properties;
            replicaConnectionUrl = monngoDbProperties.getProperty("test.mongodb.replica.connection.url", "url");
            password = monngoDbProperties.getProperty("test.mongodb.password");
            mongoHost = monngoDbProperties.getProperty("test.mongodb.host");
            mongoPort = Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.port"));
            username = monngoDbProperties.getProperty("test.mongodb.user");
            adminDbName = monngoDbProperties.getProperty("test.admin.database.name");
        } catch (IOException | NumberFormatException e) {
            throw new EjbWithMockitoRunnerException("Failed getting " + NoSqlDataSourceTestImpl.class.getResource("/META-INF/mongoDb.properties"), e);
        }
        try {
            mongoClient = getAppMogoDbClient();
            Morphia morphia = new Morphia();
            morphia.mapPackage(monngoDbProperties.getProperty("test.package.registry"));
            mongoDataStore = morphia.createDatastore(mongoClient, monngoDbProperties.getProperty("test.app.database.name"));

        } catch (BioCommonException | UnsupportedEncodingException e) {
            throw new EjbWithMockitoRunnerException("Failed connecting to the mongoDB", e);
        }

    }

    private MongoClientOptions getMongoOptions() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
        optionsBuilder.connectionsPerHost(Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.connectionsPerHost")));
        optionsBuilder.connectTimeout(Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.connectTimeout")));
        optionsBuilder.cursorFinalizerEnabled(Boolean.valueOf(monngoDbProperties.getProperty("test.mongodb.cursorFinalizerEnabled")));
        optionsBuilder.maxWaitTime(Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.maxWaitTime")));
        optionsBuilder.socketTimeout(Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.socketTimeout")));
        return optionsBuilder.build();
    }

    @PreDestroy
    public void stop() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        if (mongoDataStore != null) {
            mongoDataStore.getMongo().close();
        }
    }

    @Override
    public MongoDatabase getDatabaseConnection() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().register(monngoDbProperties.getProperty("test.package.registry")).automatic(true).build()));
        return mongoClient.getDatabase(monngoDbProperties.getProperty("test.app.database.name")).withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public MongoClient getMogoDbClient(){
        return mongoClient;
    }

    private MongoClient getAppMogoDbClient() throws BioCommonException, UnsupportedEncodingException {
        MongoClient innerMongoClient;
        if (Boolean.valueOf(monngoDbProperties.getProperty("test.mongodb-use-replica-set", "false"))) {
            //connect to mongodb replica set
            StringBuilder uriBuilder = new StringBuilder("mongodb://");

            String credentials = username + ":" + URLEncoder.encode(password, "UTF-8") + "@";
            uriBuilder.append(credentials).append(replicaConnectionUrl);

            innerMongoClient = new MongoClient(new MongoClientURI(uriBuilder.toString()));
        } else {

            MongoCredential credential = MongoCredential.createCredential(username, adminDbName, password.toCharArray());
            innerMongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), credential, getMongoOptions());
        }
        return innerMongoClient;
    }

    @Override
    public Datastore getMongoDbDatastore() {
        return mongoDataStore;
    }
}
