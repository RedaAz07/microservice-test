package com.lets_plat.mapper;

import org.springframework.stereotype.Component;

import com.lets_plat.dto.responsedto.ProductResponseDto;
import com.lets_plat.entity.Product;

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
