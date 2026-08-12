package com.lets_plat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lets_plat.Service.ProductService;
import com.lets_plat.dto.requestdto.EditProductDto;
import com.lets_plat.dto.requestdto.ProductRequestDto;
import com.lets_plat.dto.responsedto.ProductResponseDto;
import com.lets_plat.utils.RateLimited;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RateLimited
    @GetMapping()
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> product = productService.getProducts();
        return ResponseEntity.ok(product);
    }

    @PostAuthorize("returnObject.body.ownerName == authentication.name")
    @PostMapping()
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto request,
            Principal principal) {
        String username = principal.getName();
        ProductResponseDto product = productService.createProduct(request, username);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> editProduct(@RequestBody @Valid EditProductDto request,
            @PathVariable String id,
            Principal principal) {
        String username = principal.getName();
        ProductResponseDto product = productService.editProduct(request, username, id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id,
            Principal principal) {
        String username = principal.getName();
        String res = productService.deleteProduct(username, id);
        return ResponseEntity.ok(res);
    }

}
