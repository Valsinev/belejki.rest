package com.example.belejki.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
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
    private int enabled;
    @Column(name = "last_login")
    private LocalDate lastLogin;
    @Column(name = "is_set_for_deletion")
    private boolean setForDeletion;
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reminder> reminders;


//    @OneToMany(mappedBy = "wishUserId", cascade = CascadeType.ALL)
//    @Column(name = "wish_list")
//    private List<Wish> wishList;


//    @OneToMany(mappedBy = "shopperId", cascade = CascadeType.ALL)
//    @Column(name = "shopping_list")
//    private List<ShoppingItem> shoppingItems;


    public User() {
    }

    public User(String email, int enabled, String firstName, String lastName, LocalDate lastLogin, String password, boolean setForDeletion, List<Authority> authorities) {
        this.username = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.friends = new ArrayList<>();
        this.lastLogin = lastLogin;
        this.password = password;
        this.reminders = new ArrayList<>();
        this.setForDeletion = setForDeletion;
        this.authorities = authorities;
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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
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

    public void addReminder(Reminder reminder) {
        if (reminder!=null) {
            this.reminders.add(reminder);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && setForDeletion == user.setForDeletion && Objects.equals(username, user.username) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(lastLogin, user.lastLogin) && Objects.equals(friends, user.friends) && Objects.equals(authorities, user.authorities) && Objects.equals(reminders, user.reminders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName, password, enabled, lastLogin, setForDeletion, friends, authorities, reminders);
    }
}

