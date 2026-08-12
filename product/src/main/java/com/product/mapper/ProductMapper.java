package com.product.mapper;

import org.springframework.stereotype.Component;

import com.product.dto.responsedto.ProductResponseDto;
import com.product.entity.Product;

@Component
public class ProductMapper {

    public ProductResponseDto productToDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(),
                product.getPrice(), product.getOwnerName());

    }
}
