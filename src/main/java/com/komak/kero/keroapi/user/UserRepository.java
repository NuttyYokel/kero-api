package com.komak.kero.keroapi.user;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends MongoRepository<User, String> {

  @Query("{ 'username' : ?0}")
  User findByUsername(String username);

  @Query("{ 'email' : ?0}")
  User findByEmail(String email);

  @Query("{ 'role' : { $ne: 'ROLE_ADMIN' }}")
  List<User> list();
}
