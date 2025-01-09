package com.alten.ecommerce.mapper;

import com.alten.ecommerce.dto.ProductDTO;
import com.alten.ecommerce.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.id());
        product.setCode(dto.code());
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setImage(dto.image());
        product.setCategory(dto.category());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        product.setRating(dto.rating());
        return product;
    }

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getInventoryStatus().toString(),
                product.getRating()
        );
    }
}
