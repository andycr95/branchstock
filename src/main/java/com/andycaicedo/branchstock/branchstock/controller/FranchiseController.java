package com.andycaicedo.branchstock.branchstock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.andycaicedo.branchstock.branchstock.services.FranchiseService;

import lombok.AllArgsConstructor;

import com.andycaicedo.branchstock.branchstock.dto.franchise.CreateFranchiseDTO;
import com.andycaicedo.branchstock.branchstock.dto.franchise.FranchiseDetailDTO;
import com.andycaicedo.branchstock.branchstock.dto.franchise.FranchiseProducsDTO;
import com.andycaicedo.branchstock.branchstock.entity.Franchise;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("franchises")
public class FranchiseController {

    @Autowired
    private final FranchiseService franchiseService;


    // Get all franchises
    @GetMapping
    public Mono<ResponseEntity<Flux<Franchise>>> getAllFranchises() {
        return Mono.just(ResponseEntity.ok(franchiseService.getFranchises()));
    }

    // Get a branch by id
    @GetMapping("/{id}")
    public Mono<ResponseEntity<FranchiseDetailDTO>> getAllProducts(@PathVariable("id") Long id) {
        return franchiseService.getFranchiseById(id).map(ResponseEntity::ok);
    }


    // Get franchises with branches
    @GetMapping("/branches")
    public Mono<ResponseEntity<Flux<FranchiseDetailDTO>>> getAllFranchisesWithBranches() {
        return Mono.just(ResponseEntity.ok(franchiseService.getFranchisesWithBranches()));
    }

    // Get franchises with products 
    @GetMapping("/{id}/products_stock")
    public Mono<ResponseEntity<Mono<FranchiseProducsDTO>>> getFranchisesWithBranchesProducts(@PathVariable("id") Long id) {
        return Mono.just(ResponseEntity.ok(franchiseService.getFranchisesWithProductsHighStock(id)));
    }

    // Create a franchise
    @PostMapping
    public Mono<ResponseEntity<Franchise>> createFranchise(@RequestBody CreateFranchiseDTO franchise) {
        return franchiseService.createFranchise(franchise)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

      // Update a franchis
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Franchise>> updateBranch(@PathVariable("id") Long id, @RequestBody CreateFranchiseDTO franchise) {
        return franchiseService.updateFranchise(id, franchise)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(IllegalArgumentException.class, e -> {
                    System.out.println(e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }
    

    
}
