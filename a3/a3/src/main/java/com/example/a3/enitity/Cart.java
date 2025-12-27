
package com.example.a3.enitity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString(onlyExplicitlyIncluded = true)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Simple status field: OPEN, CHECKED_OUT, CANCELLED */
    @Column(nullable = false, length = 32)
    @Builder.Default
    @ToString.Include
    private String status = "OPEN";

    @Column(nullable = false)
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();

    /** Many-to-many cart ↔ products (no per-item quantity) */
    @ManyToMany
    @JoinTable(name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id",
                    foreignKey = @ForeignKey(name = "fk_cart_product_cart")),
            inverseJoinColumns = @JoinColumn(name = "product_id",
                    foreignKey = @ForeignKey(name = "fk_cart_product_product")))
    @Builder.Default
    private Set<Product> products = new HashSet<>();

    // Basic helpers
    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void clear() {
        products.clear();
    }
    

public Cart(String status, OffsetDateTime createdAt, Set<Product> products) {
        String normalizedStatus = (status != null) ? status.trim().toUpperCase() : "OPEN";
        if (!normalizedStatus.equals("OPEN")
                && !normalizedStatus.equals("CHECKED_OUT")
                && !normalizedStatus.equals("CANCELLED")) {
            throw new IllegalArgumentException(
                "Status must be one of: OPEN, CHECKED_OUT, CANCELLED."
            );
        }

        this.status = normalizedStatus;
        this.createdAt = (createdAt != null) ? createdAt : OffsetDateTime.now();

        // Defensive copy; ensure non-null set
        if (products == null) {
            this.products = new HashSet<>();
        } else {
            // Remove potential nulls to maintain invariants
            Set<Product> cleaned = new HashSet<>();
            for (Product p : products) {
                if (p != null) cleaned.add(p);
            }
            this.products = cleaned;
        }
    }

    // Optional: ensure createdAt is set even if not provided by builder/no-args
    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
        if (status == null || status.isBlank()) {
            status = "OPEN";
        } else {
            status = status.trim().toUpperCase();
            if (!status.equals("OPEN") && !status.equals("CHECKED_OUT") && !status.equals("CANCELLED")) {
                throw new IllegalArgumentException(
                    "Status must be one of: OPEN, CHECKED_OUT, CANCELLED."
                );
            }
        }
    }

}



