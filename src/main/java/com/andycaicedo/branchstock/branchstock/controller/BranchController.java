package com.andycaicedo.branchstock.branchstock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.andycaicedo.branchstock.branchstock.dto.branch.BranchDetailDTO;
import com.andycaicedo.branchstock.branchstock.dto.branch.BranchProductsDTO;
import com.andycaicedo.branchstock.branchstock.dto.branch.CreateBranchDTO;
import com.andycaicedo.branchstock.branchstock.entity.Branch;
import com.andycaicedo.branchstock.branchstock.services.BranchService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("branches")
public class BranchController {

    @Autowired
    private final BranchService branchService;

    // List all branches
    @GetMapping
    public Mono<ResponseEntity<Flux<Branch>>> getAllBranchs() {
        return Mono.just(ResponseEntity.ok(branchService.getBranchs()));
    }

    // Get a branch by id
    @GetMapping("/{id}")
    public Mono<ResponseEntity<BranchDetailDTO>> getAllProducts(@PathVariable("id") Long id) {
        return branchService.getBranchById(id).map(ResponseEntity::ok);
    }

    // Get a branches with its products
    @GetMapping("/products")
    public Mono<ResponseEntity<Flux<BranchProductsDTO>>> getAllBranchsWithProducts() {
        return Mono.just(ResponseEntity.ok().body(branchService.getBranchsWithProducts()));
    }

    // Create a new branch
    @PostMapping
    public Mono<ResponseEntity<Branch>> createBranch(@RequestBody @Valid CreateBranchDTO branch) {
        return branchService.createBranch(branch).map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    // Update a branch
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Branch>> updateBranch(@PathVariable("id") Long id, @RequestBody CreateBranchDTO branch) {
        return branchService.updateBranch(id, branch)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(IllegalArgumentException.class, e -> {
                    System.out.println(e.getMessage());
                    return Mono.just(ResponseEntity.badRequest().build());
                });
    }
    
}
