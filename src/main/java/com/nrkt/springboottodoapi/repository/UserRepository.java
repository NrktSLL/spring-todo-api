package com.nrkt.springboottodoapi.repository;

import com.nrkt.springboottodoapi.domain.User;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;

public interface UserRepository extends CouchbaseRepository<User, String> {

     Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
