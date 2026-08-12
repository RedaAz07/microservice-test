package com.lets_plat.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lets_plat.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByUserID(String userId);

}
