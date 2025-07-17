package com.belejki.belejki.restful.authority.domain;


import com.belejki.belejki.restful.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "authorities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private UserRoles authority;
//    private String authority;


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

    @Override
    public String getAuthority() {
        return this.authority.name();
    }

    public void setAuthority(UserRoles authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority1 = (Authority) o;
        return authority == authority1.authority;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }
}
