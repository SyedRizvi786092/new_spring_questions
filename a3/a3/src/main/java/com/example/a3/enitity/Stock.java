
package com.example.a3.enitity; // fix typo: 'entity' not 'enitity'

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "stocks",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_stock_store_product", columnNames = {"store_id", "product_id"})
    }
)
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(onlyExplicitlyIncluded = true)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Which store holds this stock record */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_stock_store"))
    @NotNull
    private Store store;

    /** Which product this stock refers to */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_stock_product"))
    @NotNull
    private Product product;

    /** Available quantity */
    @Column(nullable = false)
    @Min(0)
    @ToString.Include
    private Integer quantity;

    // Business helpers
    public void increase(int by) {
        if (by < 0) {
            throw new IllegalArgumentException("Increase amount must be non-negative.");
        }
        this.quantity = (this.quantity == null ? 0 : this.quantity) + by;
    }

    public void decrease(int by) {
        if (by < 0) {
            throw new IllegalArgumentException("Decrease amount must be non-negative.");
        }
        int current = (this.quantity == null ? 0 : this.quantity);
        this.quantity = Math.max(0, current - by);
    }

    // Convenience constructor for Stock (this belongs in Stock, not Store)
    public Stock(Store store, Product product, Integer quantity) {
        if (store == null) {
            throw new IllegalArgumentException("Store must not be null.");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null.");
        }
        int q = (quantity == null ? 0 : quantity);
        if (q < 0) {
            throw new IllegalArgumentException("Quantity must be zero or positive.");
        }

        this.store = store;
        this.product = product;
        this.quantity = q;
    }
}
