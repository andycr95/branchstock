package com.andycaicedo.branchstock.branchstock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.andycaicedo.branchstock.branchstock.dto.product.CreateProductDTO;
import com.andycaicedo.branchstock.branchstock.dto.product.UpdateProductDTO;
import com.andycaicedo.branchstock.branchstock.entity.Product;
import com.andycaicedo.branchstock.branchstock.services.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    // List all products by branch
    @GetMapping("/{branchId}")
    Mono<ResponseEntity<Flux<Product>>> getAllProducts(@PathVariable("branchId") Long branchId) {
        return Mono.just(ResponseEntity.ok()
                .body(productService.getProducts(branchId)));
    }

    // Create a new product
    @PostMapping
    public Mono<ResponseEntity<Product>> createBranch(@RequestBody @Valid CreateProductDTO productDTO) {
        return productService.createProduct(productDTO)
        .map(branch -> ResponseEntity.ok(branch));
    }
 
    // Update a product
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@Valid @PathVariable("id") Long id, @RequestBody CreateProductDTO productDTO) {
        return productService.updateProduct(id, productDTO)
        .map(branch -> ResponseEntity.ok(branch));
    }

    // Update a stock product
    @PutMapping("/{id}/stock")
    public Mono<ResponseEntity<Product>> updateStockProduct(@Valid @PathVariable("id") Long id, @RequestBody UpdateProductDTO updateStockProductDTO) {
        return productService.updateStockProduct(id, updateStockProductDTO)
        .map(branch -> ResponseEntity.ok(branch));
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id).map(result -> ResponseEntity.ok().build());
    }
}
