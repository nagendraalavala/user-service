package com.perficient.service.dao;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.CrudRepository;

import com.perficient.service.types.User;
import org.springframework.stereotype.Repository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "User", viewName= "all")
@Repository
public interface UserRepository extends CouchbaseRepository<User,Long> {
    User findByUsername(String username);
}
