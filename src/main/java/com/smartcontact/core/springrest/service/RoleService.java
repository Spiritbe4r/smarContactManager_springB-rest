package com.smartcontact.core.springrest.service;

import com.smartcontact.core.springrest.entities.Role;
import com.smartcontact.core.springrest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;

    public void save(Role role){

        repo.save(role);

    }
}
