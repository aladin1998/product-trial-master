package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.repository.CartItemRepository;
import com.alten.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart createCart(String username, CartItem cartItem) {
        Cart cart = new Cart();
        cart.setUsername(username);
        cart.setItems(List.of(cartItem));
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUsername(String username) {
        return cartRepository.findByUsername(username);
    }

    public Cart addItemToCart(String username, CartItem item) {
        Cart cart = new Cart();

        Optional<CartItem> cartItemOpt = cartItemRepository.findByProduct(item.getProduct());
        if (!cartItemOpt.isPresent()) {
            cart.setUsername(username);
            cart.setItems(List.of(item));
            cartItemRepository.save(item);
        }

        Optional<Cart> cartOpt = cartRepository.findByUsername(username);
        if (cartOpt.isPresent()) {
            cart = cartOpt.get();
            cart.getItems().add(item);
            cartRepository.save(cart);
        } else {
            cart = createCart(username, item);
        }

        return cart;
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
