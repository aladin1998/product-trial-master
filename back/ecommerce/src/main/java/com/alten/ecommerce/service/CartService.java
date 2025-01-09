package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart createCart(String username) {
        Cart cart = new Cart();
        cart.setUsername(username);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public Cart addItemToCart(String username, CartItem item) {
        Optional<Cart> cartOpt = cartRepository.findByUsername(username);
        Cart cart;
        if (cartOpt.isPresent()) {
            cart = cartOpt.get();
        } else {
            cart = createCart(username);
        }
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(String username, Long itemId) {
        Optional<Cart> cartOpt = cartRepository.findByUsername(username);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            cart.getItems().removeIf(item -> item.getId().equals(itemId));
            return cartRepository.save(cart);
        }
        return null;
    }

    public void clearCart(String username) {
        Optional<Cart> cartOpt = cartRepository.findByUsername(username);
        cartOpt.ifPresent(cart -> {
            cart.getItems().clear();
            cartRepository.save(cart);
        });
    }
}
