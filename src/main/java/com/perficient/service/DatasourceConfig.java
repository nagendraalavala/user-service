package com.perficient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages={"com.perficient.service.dao"})
public class DatasourceConfig extends AbstractCouchbaseConfiguration {

    @Value("${app.datasource.couchbase.hostname}")
    private List<String> couchbaseHostname;

    @Value("${app.datasource.couchbase.bucketname}")
    private String couchbaseBucketName;

    @Value("${app.datasource.couchbase.bucketpassword}")
    private String couchbaseBucketPassword;

    @Override
    protected List<String> getBootstrapHosts() {
        return couchbaseHostname;
    }

    @Override
    protected String getBucketName() {
        return couchbaseBucketName;
    }

    @Override
    protected String getBucketPassword() {
        return couchbaseBucketPassword;
    }
}
