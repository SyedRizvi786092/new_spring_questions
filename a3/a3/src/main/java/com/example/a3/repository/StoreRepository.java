
package com.example.a3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.a3.enitity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	
}

