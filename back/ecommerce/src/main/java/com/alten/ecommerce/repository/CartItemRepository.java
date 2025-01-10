package com.alten.ecommerce.repository;

import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProduct(Product product);
}
