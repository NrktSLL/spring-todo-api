package com.nrkt.springboottodoapi.repository;

import com.nrkt.springboottodoapi.domain.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CouchbaseRepository<Todo, String> {
    List<Todo> findAllByUserId(String userId);

    Optional<Todo> findByIdAndUserId(String id, String userId);
}
