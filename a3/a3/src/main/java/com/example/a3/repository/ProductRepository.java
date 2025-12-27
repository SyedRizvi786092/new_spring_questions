
package com.example.a3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.a3.enitity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
