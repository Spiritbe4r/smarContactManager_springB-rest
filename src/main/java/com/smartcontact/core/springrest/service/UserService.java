package com.smartcontact.core.springrest.service;

import com.smartcontact.core.springrest.dto.PostDto;
import com.smartcontact.core.springrest.dto.UserDto;
import com.smartcontact.core.springrest.entities.Contact;
import com.smartcontact.core.springrest.entities.Post;
import com.smartcontact.core.springrest.entities.Role;
import com.smartcontact.core.springrest.entities.User;
import com.smartcontact.core.springrest.exception.PostNotFoundException;
import com.smartcontact.core.springrest.repository.RoleRepository;
import com.smartcontact.core.springrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;

    public List<Role> getRoles(){
        return roleRepo.findAll();
    }

    public User buscarPorId(Long id) {

        return this.userRepo.findById(id).get();
    }

    public void borrarPorId(Long id) {

        this.userRepo.deleteById(id);
    }

    public void save(User user){

        userRepo.save(user);

    }


    public User updateUser(User user)

    {

        User updateUser=userRepo.findById(user.getId()).get();
        updateUser.setUserName(user.getUserName());
        updateUser.setPassword(user.getUserName());
        updateUser.setAbout(user.getAbout());
        updateUser.setEmail(user.getEmail());
        updateUser.setEnabled(user.isEnabled());
        updateUser.setRoles(user.getRoles());
        updateUser.setImageUrl(user.getImageUrl());


        User user_up=userRepo.save(updateUser);

        return user_up;
    }

    @Transactional
    public List<UserDto> showAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(this::mapFromUserToDto).collect(toList());
    }

    @Transactional
    public UserDto SingleUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("For id " + id));
        return mapFromUserToDto(user);
    }

    private UserDto mapFromUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUserName());
        userDto.setAbout(user.getAbout());
        userDto.setEmail(user.getEmail());
        userDto.setEnabled(user.isEnabled());
        userDto.setImageUrl(user.getImageUrl());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
