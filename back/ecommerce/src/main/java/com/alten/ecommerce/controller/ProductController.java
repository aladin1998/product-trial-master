package com.alten.ecommerce.controller;

import com.alten.ecommerce.dto.ProductDTO;
import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.mapper.ProductMapper;
import com.alten.ecommerce.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "product", description = "product API")
@RestController
@RequestMapping("/products")
public class ProductController {

    private static final String ADMIN_EMAIL = "admin@admin.com";

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @Operation(summary = "Create Product", description = "Create a new product. Accessible only to the admin with email admin@admin.com.")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {

        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return ResponseEntity.ok(productRepository.save(ProductMapper.toEntity(product)));
    }

    @GetMapping
    @Operation(summary = "Get All Products", description = "Retrieve all products.")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get a product by ID",
            description = "This endpoint returns a product by its ID."
    )
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update an existing product",
            description = "This endpoint updates an existing product. Accessible only to the admin with email admin@admin.com.."
    )
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setDescription(productDetails.getDescription());
                    // Other updates
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product",
            description = "This endpoint deletes a product by its ID. Accessible only to the admin with email admin@admin.com.."
    )
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && ADMIN_EMAIL.equals(authentication.getName());
    }
}
