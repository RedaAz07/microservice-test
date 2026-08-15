package com.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.Service.ProductService;
import com.product.dto.requestdto.EditProductDto;
import com.product.dto.requestdto.ProductRequestDto;
import com.product.dto.responsedto.ProductResponseDto;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> product = productService.getProducts();
        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto request,
            @RequestHeader("X-User-Name") String username) {
        ProductResponseDto product = productService.createProduct(request, username);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> editProduct(@RequestBody @Valid EditProductDto request,
            @PathVariable String id,
            @RequestHeader("X-User-Name") String username) {
        ProductResponseDto product = productService.editProduct(request, username, id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id,
            @RequestHeader("X-User-Name") String username) {
        String res = productService.deleteProduct(username, id);
        return ResponseEntity.ok(res);
    }

}
