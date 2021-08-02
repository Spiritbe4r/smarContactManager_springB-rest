package com.smartcontact.core.springrest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Where(clause="status = true")
@Table(name="CONTACT")
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)

    private Long cId;
    private String name;
    private String secondName;
    private String work;
    private String email;
    private String phone;
    private String image;
    @Column(length=1000)
    private String description;
    @Column(name="status",columnDefinition = "boolean DEFAULT '1' ")
    private Boolean status = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id")
    private User user;

    @JsonBackReference
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Contact() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Long getCid() {
        return cId;
    }
    public void setCid(Long cid) {
        this.cId = cid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Contact(Long cId, String name, String secondName, String work, String email, String phone, String description, User user) {
        this.cId = cId;
        this.name = name;
        this.secondName = secondName;
        this.work = work;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.user = user;
    }





}
