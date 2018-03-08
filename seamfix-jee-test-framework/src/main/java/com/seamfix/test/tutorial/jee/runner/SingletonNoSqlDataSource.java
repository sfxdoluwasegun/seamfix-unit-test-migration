package com.seamfix.test.tutorial.jee.runner;

import com.sf.bio.common.ds.NoSqlDataSource;
import lombok.Data;

/**
 *
 * @author uonuoha@seamfix.com
 */
@Data
public class SingletonNoSqlDataSource {

    private static SingletonNoSqlDataSource INSTANCE = new SingletonNoSqlDataSource();

    private NoSqlDataSource noSqlDataSource;

    public static NoSqlDataSource getNoSqlDataSource() {
        return INSTANCE.noSqlDataSource;
    }

    private SingletonNoSqlDataSource() {

        try {
            noSqlDataSource = (NoSqlDataSource) MongoDataSourceProxyFactory.newInstance(new NoSqlDataSourceTestImpl());
        } catch (Exception e) {
            throw new EjbWithMockitoRunnerException("Failed getting MongoDB Datasource ", e);
        }

    }

    public synchronized static SingletonNoSqlDataSource getInstnace() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonNoSqlDataSource();
        }
        return INSTANCE;
    }

}
