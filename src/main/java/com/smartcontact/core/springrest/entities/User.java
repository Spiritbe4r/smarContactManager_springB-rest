package com.smartcontact.core.springrest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="USER")
//@Where(clause="enabled = true")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @NotBlank(message="Columna Nombre es Requerida !!")
    @Size(min=2,max=20,message="minimo 2 y maximo 20 caracteres es Requerido !!")
    private String userName;
    @NotBlank(message="Columna Password es Requerida !!")
    //@Size(min=8,message="Password minimo 8 caracteres es Requerido !!")
    private String password;

    @NotNull
    @Column(name = "email", nullable = false,unique = true)
    @Email(message="El email ingresado ya ha sido Registrado")
    @NotBlank(message="Columna Email es Requerida !! ")
    private String email;


   // @Column(name="enabled",columnDefinition="boolean DEFAULT 'true'")
    private boolean enabled ;
    private String imageUrl;

    @Column(name = "about", length=500)
    private String about;



    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user",fetch=FetchType.LAZY)
    // @JsonBackReference(value="contact_use") //loops infinitos al momento de construir el json
    private List<Contact> contacts=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();


    @JsonManagedReference
    public List<Contact> getContactos() {
        return contacts;
    }

    public void setContactos(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // NUEVO METODO
    public void addRole (Role role)
    {

        this.roles.add(role);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public User(){

    }




}
