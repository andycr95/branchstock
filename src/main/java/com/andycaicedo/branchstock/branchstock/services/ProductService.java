package com.andycaicedo.branchstock.branchstock.services;

import com.andycaicedo.branchstock.branchstock.dto.product.CreateProductDTO;
import com.andycaicedo.branchstock.branchstock.dto.product.UpdateProductDTO;
import com.andycaicedo.branchstock.branchstock.entity.Product;
import com.andycaicedo.branchstock.branchstock.repository.BranchRepository;
import com.andycaicedo.branchstock.branchstock.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;  

@Service  
@Transactional  
public class ProductService {

    @Autowired  
    private ProductRepository productRepository; 

    @Autowired  
    private BranchRepository branchRepository; 

    @Autowired  
    private ModelMapper modelMapper;  
    
    @Transactional
    public Mono<Product> createProduct(CreateProductDTO productDTO) {  
        return branchRepository.findById(productDTO.getBranchId())
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Branch does not exist")))
            .flatMap(branch -> {
                Product product = modelMapper.map(productDTO, Product.class);
                product.setId(0);
                return productRepository.save(product);
            });
    }

    public Flux<Product> getProducts(Long branchId) {
        return branchRepository.findById(branchId)
        .switchIfEmpty(Mono.error(new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Branch does not exist")))
        .flatMapMany(branch -> {
            return productRepository.findByBranchId(branchId)
                .switchIfEmpty(Flux.empty());
        });
    }

    @Transactional
    public Mono<Product> updateProduct(Long id, CreateProductDTO productDTO) {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product not found")))
            .flatMap(product -> {
                product.setName(productDTO.getName());
                return productRepository.save(product);
            })
            .onErrorResume(ex -> Mono.error(new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error updating product", ex)));
    }

    @Transactional
    public Mono<Product> updateStockProduct(Long id, UpdateProductDTO productDTO) {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product not found")))
            .flatMap(product -> {
                product.setStock(productDTO.getStock());
                return productRepository.save(product);
            })
            .onErrorResume(ex -> Mono.error(new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error updating product", ex)));
    }

    @Transactional
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Product not found")))
            .flatMap(product -> productRepository.deleteById(id))
            .onErrorResume(ex -> Mono.error(new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting product", ex)));
    }

}
