package com.belejki.belejki.restful.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "last_login")
    private LocalDate lastLogin;
    @Column(name = "is_set_for_deletion")
    private boolean setForDeletion;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Friendship> friendships;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Authority> authorities;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reminder> reminders;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(name = "wish_list")
    @JsonManagedReference
    private List<Wish> wishList;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(name = "shopping_list")
    @JsonManagedReference
    private List<ShoppingItem> shoppingItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Recipe> recipes;

    public User(String email, String firstName, String lastName, String password) {
        this.username = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = true;
        this.lastLogin = LocalDate.now();
        this.setForDeletion = false;
        this.friendships = new ArrayList<>();
        this.reminders = new ArrayList<>();
        this.recipes = new ArrayList<>();
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public boolean isSetForDeletion() {
        return setForDeletion;
    }

    public void setSetForDeletion(boolean setForDeletion) {
        this.setForDeletion = setForDeletion;
    }


}

