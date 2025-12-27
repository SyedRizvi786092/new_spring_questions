package com.example.a3.enitity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString(onlyExplicitlyIncluded = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Owning store (one store has many products) */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_product_store"))
    private Store store;

    @ToString.Include
    @Column(nullable = false, unique = true, length = 64)
    @NotBlank
    private String sku;

    @ToString.Include
    @Column(nullable = false, length = 200)
    @NotBlank
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 19, scale = 2)
    @PositiveOrZero
    private BigDecimal price;

    /** One product appears in many stock entries (across stores or locations) */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Stock> stocks = new ArrayList<>();

    // Basic helpers
    public void updatePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
        stock.setProduct(this);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
        stock.setProduct(null);
    }
    

public Product(Store store, String sku, String name, String description, BigDecimal price) {
        // Validate store
        if (store == null) {
            throw new IllegalArgumentException("Store must not be null.");
        }

        // Normalize text fields
        String normalizedSku = (sku != null) ? sku.trim() : null;
        String normalizedName = (name != null) ? name.trim() : null;
        String normalizedDescription = (description != null) ? description.trim() : null;

        // Validate SKU
        if (normalizedSku == null || normalizedSku.isEmpty()) {
            throw new IllegalArgumentException("SKU must not be blank.");
        }
        if (normalizedSku.length() > 64) {
            throw new IllegalArgumentException("SKU must be at most 64 characters.");
        }

        // Validate name
        if (normalizedName == null || normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Name must not be blank.");
        }
        if (normalizedName.length() > 200) {
            throw new IllegalArgumentException("Name must be at most 200 characters.");
        }

        // Validate description (optional)
        if (normalizedDescription != null && normalizedDescription.length() > 500) {
            throw new IllegalArgumentException("Description must be at most 500 characters.");
        }
}
}



