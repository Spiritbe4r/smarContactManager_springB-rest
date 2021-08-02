package com.smartcontact.core.springrest.security;

import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.repository.ContactRepository;
import com.smartcontact.core.springrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ContactService {



    List<Contact> getContacts();

    public Contact getContact(Long cId);

    public Contact addContact(Contact contact);

    public Contact updateContact(Contact contact);

    public void deleteContact(Long cId);

    public void softdeleteContact(Long cId);




}
