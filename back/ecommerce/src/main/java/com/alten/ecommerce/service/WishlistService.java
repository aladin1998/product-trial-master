package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Wishlist;
import com.alten.ecommerce.entity.WishlistItem;
import com.alten.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist createWishlist(String username) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(username);
        return wishlistRepository.save(wishlist);
    }

    public Optional<Wishlist> getWishlistByUsername(String username) {
        return wishlistRepository.findByUsername(username);
    }

    public Wishlist addItemToWishlist(String username, WishlistItem item) {
        Optional<Wishlist> wishlistOpt = wishlistRepository.findByUsername(username);
        Wishlist wishlist;
        if (wishlistOpt.isPresent()) {
            wishlist = wishlistOpt.get();
        } else {
            wishlist = createWishlist(username);
        }
        wishlist.getItems().add(item);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeItemFromWishlist(String username, Long itemId) {
        Optional<Wishlist> wishlistOpt = wishlistRepository.findByUsername(username);
        if (wishlistOpt.isPresent()) {
            Wishlist wishlist = wishlistOpt.get();
            wishlist.getItems().removeIf(item -> item.getId().equals(itemId));
            return wishlistRepository.save(wishlist);
        }
        return null;
    }

    public void clearWishlist(String username) {
        Optional<Wishlist> wishlistOpt = wishlistRepository.findByUsername(username);
        wishlistOpt.ifPresent(wishlist -> {
            wishlist.getItems().clear();
            wishlistRepository.save(wishlist);
        });
    }
}
