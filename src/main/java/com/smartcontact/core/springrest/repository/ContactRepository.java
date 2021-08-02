package com.smartcontact.core.springrest.repository;

import com.smartcontact.core.springrest.entities.Contact;

import com.smartcontact.core.springrest.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {


    @Query("from Contact as c where c.user.id =:userId and c.status=1 ")
    public List<Contact> findContactByUsername(@Param("userId")Long userId);

    @Query("from Contact as c where c.user.id =:userId")
    public Page<Contact> findContactsByUser(@Param("userId")Long userId, Pageable pageable);

    public List<Contact>findByNameContainingAndUser(String query, User user);

    @Transactional
    @Modifying
    @Query("update Contact c SET c.status=0 WHERE c.id=:cid")
    void softDeleteContact(@Param("cid") Long cid);

}
