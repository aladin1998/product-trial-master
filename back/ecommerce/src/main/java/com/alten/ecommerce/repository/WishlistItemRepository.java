package com.alten.ecommerce.repository;

import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    Optional<WishlistItem> findByProduct(Product product);
}
