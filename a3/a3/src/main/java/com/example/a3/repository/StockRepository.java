
package com.example.a3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.a3.enitity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}
