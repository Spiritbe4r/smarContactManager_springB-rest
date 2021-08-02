package com.smartcontact.core.springrest.repository;

import com.smartcontact.core.springrest.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    @Query("select u from User u where u.email = :email")
    public User getUsuarioPorEmail(@Param("email")String email);


    @Query("SELECT u FROM User u")
    public Page<User> listarUsuarios(Pageable pag);
}
