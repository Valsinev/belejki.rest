package com.belejki.belejki.restful.wish.domain;

import com.belejki.belejki.restful.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "wishlist")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "approximate_price")
    private double approximatePrice;
    @Column(name = "link", length = 2000)
    private String link;
    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Double.compare(approximatePrice, wish.approximatePrice) == 0 && Objects.equals(id, wish.id) && Objects.equals(description, wish.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, approximatePrice);
    }
}

