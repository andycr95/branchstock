package com.andycaicedo.branchstock.branchstock.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andycaicedo.branchstock.branchstock.entity.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    Flux<Product> findByBranchId(Long branchId);

    Mono<Product> findTopByBranchIdOrderByStockDesc(Long branchId);
}

