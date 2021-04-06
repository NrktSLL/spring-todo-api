package com.nrkt.springboottodoapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@EnableCouchbaseRepositories
@Configuration
public class CouchbaseConfig {
}
