package com.alten.ecommerce.controller;

import com.alten.ecommerce.entity.Wishlist;
import com.alten.ecommerce.entity.WishlistItem;
import com.alten.ecommerce.service.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "wishlist", description = "wishlist API")
@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @Operation(summary = "Get Wishlist", description = "Retrieve the wishlist of the current user.")
    @GetMapping
    public Wishlist getWishlist(Principal principal) {
        return wishlistService.getWishlistByUsername(principal.getName()).orElse(null);
    }

    @Operation(summary = "Add Item to Wishlist", description = "Add a new item to the wishlist of the current user.")
    @PostMapping("/add")
    public Wishlist addItemToWishlist(Principal principal, @RequestBody WishlistItem item) {
        return wishlistService.addItemToWishlist(principal.getName(), item);
    }

    @Operation(summary = "Remove Item from Wishlist", description = "Remove an item from the wishlist of the current user.")
    @DeleteMapping("/remove/{itemId}")
    public Wishlist removeItemFromWishlist(Principal principal, @PathVariable Long itemId) {
        return wishlistService.removeItemFromWishlist(principal.getName(), itemId);
    }

    @Operation(summary = "Clear Wishlist", description = "Clear the wishlist of the current user.")
    @PostMapping("/clear")
    public void clearWishlist(Principal principal) {
        wishlistService.clearWishlist(principal.getName());
    }
}