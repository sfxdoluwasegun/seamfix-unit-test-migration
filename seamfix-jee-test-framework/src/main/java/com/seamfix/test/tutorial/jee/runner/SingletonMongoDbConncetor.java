package com.seamfix.test.tutorial.jee.runner;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.net.URLEncoder;
import java.util.Properties;
import javax.annotation.PreDestroy;
import lombok.Data;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *
 * @author uonuoha@seamfix.com
 */
@Data
public class SingletonMongoDbConncetor {

    private static final SingletonMongoDbConncetor INSTANCE = new SingletonMongoDbConncetor();

    private final String replicaConnectionUrl;
    private final String password;
    private final String mongoHost;
    private final int mongoPort;
    private final String username;
    private final String dbName;
    private final MongoClient mongoClient;
    private final Properties monngoDbProperties;

    private SingletonMongoDbConncetor() {

        Properties properties = new Properties();
        try {
            properties.load(SingletonMongoDbConncetor.class.getResourceAsStream("/META-INF/mongoDb.properties"));
            monngoDbProperties = properties;
            replicaConnectionUrl = monngoDbProperties.getProperty("test.mongodb.replica.connection.url", "url");
            password = monngoDbProperties.getProperty("test.mongodb.password");
            mongoHost = monngoDbProperties.getProperty("test.mongodb.host");
            mongoPort = Integer.parseInt(monngoDbProperties.getProperty("test.mongodb.port"));
            username = monngoDbProperties.getProperty("test.mongodb.user");
            dbName = monngoDbProperties.getProperty("test.mongodb.db.name");
        } catch (Exception e) {
            throw new EjbWithMockitoRunnerException("Failed getting " + SingletonMongoDbConncetor.class.getResource("/META-INF/mongoDb.properties"), e);
        }

        try {

            mongoClient = getMongoClient();
        } catch (Exception e) {
            throw new EjbWithMockitoRunnerException("Failed connecting to the mongoDB", e);
        }

    }

    public MongoClient getMongoClient() throws Exception {
        MongoClient innerMongoClient;
        if (Boolean.valueOf(monngoDbProperties.getProperty("test.mongodb.use.replica", "false"))) {
            //connect to mongodb replica set
            StringBuilder uriBuilder = new StringBuilder("mongodb://");

            String credentials = username + ":" + URLEncoder.encode(password, "UTF-8") + "@";
            uriBuilder.append(credentials).append(replicaConnectionUrl);

            innerMongoClient = new MongoClient(new MongoClientURI(uriBuilder.toString()));
        } else {

            MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
            innerMongoClient = new MongoClient(new ServerAddress(mongoHost, mongoPort), credential, getMongoOptions());
        }
        return innerMongoClient;
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
    }

    public static SingletonMongoDbConncetor getInstnace() {
        return INSTANCE;
    }

    public MongoDatabase getDatabaseConnection() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().register(monngoDbProperties.getProperty("test.package.registry")).automatic(true).build()));
        return mongoClient.getDatabase(monngoDbProperties.getProperty("test.app.database.name")).withCodecRegistry(pojoCodecRegistry);
    }

}
