package com.fnb.oms_usermanagement.repository;

import com.fnb.oms_usermanagement.entity.User;
import com.fnb.oms_usermanagement.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredential, Long> {

    Optional<UserCredential> findByUser(User user);

}