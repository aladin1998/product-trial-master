package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.entity.Wishlist;
import com.alten.ecommerce.entity.WishlistItem;
import com.alten.ecommerce.repository.WishlistItemRepository;
import com.alten.ecommerce.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    public Wishlist createWishlist(String username, WishlistItem wishlistItem) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUsername(username);
        wishlist.setItems(List.of(wishlistItem));
        return wishlistRepository.save(wishlist);
    }

    public Optional<Wishlist> getWishlistByUsername(String username) {
        return wishlistRepository.findByUsername(username);
    }

    public Wishlist addItemToWishlist(String username, WishlistItem item) {

        Wishlist wishlist = new Wishlist();

        Optional<WishlistItem> wishItem = wishlistItemRepository.findByProduct(item.getProduct());
        if (!wishItem.isPresent()) {
            wishlist.setUsername(username);
            wishlist.setItems(List.of(item));
            wishlistItemRepository.save(item);
        }

        Optional<Wishlist> wishlistOpt = wishlistRepository.findByUsername(username);

        if (wishlistOpt.isPresent()) {
            wishlist = wishlistOpt.get();
            wishlist.getItems().add(item);
            wishlistRepository.save(wishlist);
        } else {
            wishlist = createWishlist(username, item);
        }

        return wishlist;
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
