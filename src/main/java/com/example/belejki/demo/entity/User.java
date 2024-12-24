package com.example.belejki.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;
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

//    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Column(name = "roles")
//    private List<UserRole> roles;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
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

    public User(String email, int enabled, String firstName, List<User> friends, LocalDate lastLogin, String lastName, String password, List<Reminder> reminders, boolean setForDeletion) {
        this.email = email;
        this.enabled = enabled;
        this.firstName = firstName;
        this.friends = friends;
        this.lastLogin = lastLogin;
        this.lastName = lastName;
        this.password = password;
        this.reminders = reminders;
        this.setForDeletion = setForDeletion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && setForDeletion == user.setForDeletion && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(password, user.password) && Objects.equals(lastLogin, user.lastLogin) && Objects.equals(friends, user.friends) && Objects.equals(reminders, user.reminders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName, password, enabled, lastLogin, setForDeletion, friends, reminders);
    }
}

