package com.product.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dto.requestdto.EditProductDto;
import com.product.dto.requestdto.ProductRequestDto;
import com.product.dto.requestdto.userDto;
import com.product.dto.responsedto.ProductResponseDto;
import com.product.entity.Product;
import com.product.exceptions.ApiException;
import com.product.feing.UserClientInterface;
import com.product.mapper.ProductMapper;
import com.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UserClientInterface userClientInterface;

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream().map(p -> productMapper.productToDto(p))
                .collect(Collectors.toList());
    }

    public ProductResponseDto createProduct(ProductRequestDto request, String username) {

        userDto user = userClientInterface.getUserByUsername(username);
        if (user == null) {
            throw ApiException.notFound("User not found ");

        }

        Product p = new Product();
        p.setDescription(request.description());
        p.setName(request.name());
        p.setPrice(request.price());
        p.setUserID(user.id());
        p.setOwnerName(username);
        Product savedP = productRepository.save(p);
        return productMapper.productToDto(savedP);

    }

    public ProductResponseDto editProduct(EditProductDto request, String username, String id) {
        userDto user = userClientInterface.getUserByUsername(username);
        if (user == null) {
            throw ApiException.notFound("User not found ");

        }
        Product product = productRepository.findById(id).orElseThrow(() -> ApiException.notFound("Product Not Found"));

        if (!product.getUserID().equals(user.id()) && !user.role().equals("ROLE_ADMIN")) {
            throw ApiException.forbidden("The Product is not yours ");
        }
        if (request.name() != null) {
            product.setName(request.name());
        }

        if (request.description() != null) {
            product.setDescription(request.description());
        }

        if (request.price() != null) {
            product.setPrice(request.price());
        }
        Product savedP = productRepository.save(product);
        return productMapper.productToDto(savedP);

    }

    public String deleteProduct(String username, String id) {
        userDto user = userClientInterface.getUserByUsername(username);
        if (user == null) {
            throw ApiException.notFound("User not found ");

        }
        Product product = productRepository.findById(id).orElseThrow(() -> ApiException.notFound("Product Not Found"));

        if (!product.getUserID().equals(user.id()) && !user.role().equals("ROLE_ADMIN")) {
            throw ApiException.forbidden("The Product is not yours ");
        }

        productRepository.delete(product);
        return "Seccusefully ";
    }

}
