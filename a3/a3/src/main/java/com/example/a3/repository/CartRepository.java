
package com.example.a3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.a3.enitity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
