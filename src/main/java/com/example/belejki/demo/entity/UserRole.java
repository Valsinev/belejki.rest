package com.example.belejki.demo.entity;//package com.belejki.backend.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Objects;
//
//@Setter
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor(staticName = "build")
//@Table(name = "authorities")
//public class UserRole {
//    @Getter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Getter
////    @ManyToOne(cascade = CascadeType.ALL)
////    @JoinColumn(name = "username")
//    @Column(name = "user")
//    private User userInfo;
//
//    @Column(name = "authority")
//    private String authority;
//
//    @Override
//    public String toString() {
//        return "UserAuthority{" +
//                "userInfo=" + userInfo.getUsername() +
//                ", authority='" + authority + '\'' +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        UserRole authority1 = (UserRole) o;
//        return Objects.equals(authority, authority1.authority);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(authority);
//    }
//}
//
