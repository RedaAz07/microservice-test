package com.product.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserID(String userId);

}
