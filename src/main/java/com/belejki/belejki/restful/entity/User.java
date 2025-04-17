package com.belejki.belejki.restful.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.rest.core.annotation.RestResource;

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

    @Getter
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
    private boolean enabled = true;
    @Column(name = "last_login")
    private LocalDate lastLogin = LocalDate.now();
    @Column(name = "is_set_for_deletion")
    private boolean setForDeletion;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Friendship> friendships = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Authority> authorities = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reminder> reminders = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Wish> wishList = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ShoppingItem> shoppingItems = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Recipe> recipes = new ArrayList<>();

    public User(String email, String firstName, String lastName, String password) {
        this.username = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }


}

