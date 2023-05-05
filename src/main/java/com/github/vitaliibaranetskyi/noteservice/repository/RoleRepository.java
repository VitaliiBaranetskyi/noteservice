package com.github.vitaliibaranetskyi.noteservice.repository;

import com.github.vitaliibaranetskyi.noteservice.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String user);
}
