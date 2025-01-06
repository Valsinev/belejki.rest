package com.example.belejki.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String howToMake;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

}
