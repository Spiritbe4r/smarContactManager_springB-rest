package com.smartcontact.core.springrest.controller;

import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.repository.ContactRepository;
import com.smartcontact.core.springrest.repository.UserRepository;
import com.smartcontact.core.springrest.security.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository repo;
    @Autowired
    private ContactRepository cont_repo;
    @Autowired
    private ContactService contactService;


    @PostMapping("/signup")
    public ResponseEntity createcontact(@RequestBody Contact contact,Principal principal) {
        String nombre = principal.getName();

       
        User user = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));

        //Asignar a Id Usuario
        contact.setUser(user);
        contact.setImage("https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png");

        //Agregar el contacto
        user.getContactos().add(contact);

        //Guardar
        this.repo.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }



    @GetMapping("/all_contacts")
    public ResponseEntity<Object> listarcontact(Principal principal) {

        String nombre = principal.getName();

        User user = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));

        List<Contact> contacts=this.cont_repo.findContactByUsername(user.getId());




        return new ResponseEntity<Object>(contacts,HttpStatus.OK);
    }
    @PutMapping("/update")
    public Contact updateContact(@RequestBody Contact contact,Principal principal){
        return this.contactService.updateContact(contact);
    }



    @DeleteMapping(path="/delete/{cId}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long cId) {
        try{
            this.contactService.deleteContact(cId);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path="/{cId}")
    public Contact getContact (@PathVariable Long cId){
        return this.contactService.getContact(cId);

    }





    @PutMapping("/update/{cId}")
    public ResponseEntity <Object>update(@PathVariable("cId")Long cId, @RequestBody Contact contact,Principal principal) {

        String nombre = principal.getName();

        User user = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));

        //Asignar a Id Usuario
        contact.setUser(user);


        //Guardar
        this.contactService.updateContact(contact);

        return new ResponseEntity<Object>("Funcion actualizada correctamente", HttpStatus.ACCEPTED);
    }

    /*public ResponseEntity<Object> actualizar( @PathVariable("id") int idFuncionActualizar, @RequestBody Funciones funcionActualizar)
	{

    funcionActualizar.setIdFunciones(Integer.valueOf(idFuncionActualizar));
		servicio.actualizar(funcionActualizar);
		return new ResponseEntity<Object>("Funcion actualizada correctamente", HttpStatus.ACCEPTED);*/

    @GetMapping("/show_contacts/{page}")
    public ResponseEntity<Object> listarcontact(@PathVariable("page")int page, Principal principal) {
        Map<String, Object> mapRespuesta = new HashMap<>();




        String nombre = principal.getName();

        User user = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));

        Pageable pageable=PageRequest.of(page,5);

        Page<Contact> contacts=this.cont_repo.findContactsByUser(user.getId(),pageable);

        /*mapRespuesta.put("rpta", "1");
        mapRespuesta.put("currentPage", page);
        mapRespuesta.put("totalPages", contacts.getTotalPages());
        mapRespuesta.put("data", contacts);*/


        return new ResponseEntity<Object>(contacts,HttpStatus.OK);
    }
    @GetMapping(path="/search/{query}")
    public ResponseEntity<?>search(@PathVariable("query")String query,Principal principal){
        System.out.println(query);
        String nombre = principal.getName();
        User user = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));
        List<Contact>contacts=this.cont_repo.findByNameContainingAndUser(query,user);

        return ResponseEntity.ok(contacts);
    }
    @PostMapping("/change-password")
    public ResponseEntity<Object> cambiar(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword, Principal principal)
    {
        Map<String, Object> mapRespuesta = new HashMap<>();

        System.out.println("old password"+oldPassword);
        System.out.println("new password"+newPassword);
        String nombre = principal.getName();
        User currentuser = repo.findByUserName(nombre).orElseThrow(() ->new UsernameNotFoundException("No user found " +  nombre));

        if(this.bCryptPasswordEncoder.matches(oldPassword, currentuser.getPassword()))
        {
            // change the password
            currentuser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.repo.save(currentuser);
            mapRespuesta.put("rpta", "1");
            mapRespuesta.put("msg", "The password is changed ");

        }else{
            mapRespuesta.put("rpta", "-1");
            mapRespuesta.put("msg", "The password is incorrect try again ");

        }

        return new ResponseEntity<Object>(mapRespuesta,HttpStatus.OK);

    }
    @PutMapping(path="/delete/{cId}")
    public ResponseEntity<HttpStatus> softdeleteContact(@PathVariable Long cId) {
        try{
            this.contactService.softdeleteContact(cId);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
