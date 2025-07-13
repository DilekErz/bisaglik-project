package com.dilekersoz.eczane.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilekersoz.eczane.model.CartItem;
import com.dilekersoz.eczane.model.Drug;
import com.dilekersoz.eczane.model.User;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
	
	// Belirli bir ilaca ait tüm sepet öğelerini getir
	List<CartItem> findByDrug(Drug drug);
	
	Optional<CartItem> findByUserAndDrug(User user, Drug drug);


}
