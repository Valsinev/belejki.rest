package com.example.belejki.demo.entity;//package com.belejki.backend.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "shop_items")
//public class ShoppingItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
////    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
////    @JoinColumn(name = "buyer_user_id")
//    @Column(name = "buyer_user_id")
//    private User shopperId;
//
//    @Override
//    public String toString() {
//        return "ShoppingItem{" +
//                "name='" + name + '\'' +
//                '}';
//    }
//}
//
