package com.smartcontact.core.springrest.security;

import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {



    @Autowired
    private ContactRepository contactRepository;



    @Override
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }



    @Override
    public Contact getContact(Long cId) {
        return contactRepository.findById(cId).get();
       // return contactRepository.getOne(cId);
    }

    @Override
    public Contact addContact(Contact contact) {
        contactRepository.save(contact);
        return contact;
    }

    @Override
    public Contact updateContact(Contact contact)

    {

        Contact updateContact=contactRepository.findById(contact.getCid()).get();
        updateContact.setName(contact.getName());
        updateContact.setSecondName(contact.getSecondName());
        updateContact.setDescription(contact.getDescription());
        updateContact.setEmail(contact.getEmail());
        updateContact.setPhone(contact.getPhone());
        updateContact.setWork(contact.getWork());
        updateContact.setImage(contact.getImage());

        Contact contact_up=contactRepository.save(updateContact);

        return contact_up;
    }



        @Override
    public void deleteContact(Long cId){
        Contact entity=contactRepository.getOne(cId);
        contactRepository.delete(entity);
    }

    public void softdeleteContact(Long cId){
        contactRepository.softDeleteContact(cId);
    }



}
