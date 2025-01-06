package com.example.belejki.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    @Column(name = "authority")
    private String authority;

    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
