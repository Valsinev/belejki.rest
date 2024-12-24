package com.example.belejki.demo.entity;//package com.belejki.backend.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Table(name = "wishes")
//public class Wish {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//    @Column(name = "description")
//    private String description;
//    @Column(name = "approximate_price")
//    private double approximatePrice;
//    @Column(name = "link")
//    private String link;
////    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
////    @JoinColumn(name = "wish_user_id")
//    @Column(name = "user_id")
//    User wishUserId;
//
//    @Override
//    public String toString() {
//        return "Wish{" +
//                "description='" + description + '\'' +
//                ", approximatePrice=" + approximatePrice +
//                ", link='" + link + '\'' +
//                ", wishUserId=" + wishUserId.getUsername() +
//                '}';
//    }
//}
//
