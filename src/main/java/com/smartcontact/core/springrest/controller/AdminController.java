package com.smartcontact.core.springrest.controller;

import com.smartcontact.core.springrest.dto.PostDto;
import com.smartcontact.core.springrest.dto.UserDto;
import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.entities.Role;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.repository.UserRepository;
import com.smartcontact.core.springrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService service;

    @GetMapping("/{page}")
    public ResponseEntity<Object> listUsers(@PathVariable("page")int page) {
        //Map<String, Object> mapRespuesta = new HashMap<>();


        Pageable pageable= PageRequest.of(page,5);

        Page<User> users=this.userRepo.listarUsuarios(pageable);

        /*mapRespuesta.put("rpta", "1");
        mapRespuesta.put("currentPage", page);
        mapRespuesta.put("totalPages", contacts.getTotalPages());
        mapRespuesta.put("data", contacts);*/


        return new ResponseEntity<Object>(users, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> showAllPosts() {
        return new ResponseEntity<>(service.showAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/edit/{id}")
    public ResponseEntity<Object> editUsers(@PathVariable("id")Long id) {

        Map<String, Object> response = new HashMap<>();

        User user=service.buscarPorId(id);
        List<Role> listRoles=service.getRoles();

        response.put("usuario",user);
        //response.put("listRoles",listRoles);


        return new ResponseEntity<Object>(response,HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity <Object>update(@PathVariable("id")Long cId, @RequestBody User user) {


        //Guardar
        this.service.updateUser(user);

        return new ResponseEntity<Object>("Usuario actualizado correctamente", HttpStatus.ACCEPTED);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(service.SingleUser(id), HttpStatus.OK);
    }



}


