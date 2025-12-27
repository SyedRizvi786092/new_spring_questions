package com.example.a3.enitity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "stores")
       
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
@ToString(onlyExplicitlyIncluded = true)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
   
    @Column(nullable = false, length = 200)
   @NotBlank
    @Length(max=200)
    private String name;

    @Column(length = 300)
    private String address;

    /** One store has many product entries */
    
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();


public Store(String name, String address) {
        // Normalize input
        String normalizedName = name != null ? name.trim() : null;
        String normalizedAddress = address != null ? address.trim() : null;

        // Validate 'name'
        if (normalizedName == null || normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Store name must not be blank.");
        }
        if (normalizedName.length() > 200) {
            throw new IllegalArgumentException("Store name must be at most 200 characters.");
        }

        // Validate 'address' (optional)
        if (normalizedAddress != null && normalizedAddress.length() > 300) {
            throw new IllegalArgumentException("Address must be at most 300 characters.");
        }

        this.name = normalizedName;
        this.address = normalizedAddress;
       // this.products = new ArrayList<>();
    }


    // Basic helpers to maintain both sides
    public void addProduct(Product product) {
        products.add(product);
        product.setStore(this);
    }

    
}

