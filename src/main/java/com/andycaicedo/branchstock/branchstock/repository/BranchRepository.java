package com.andycaicedo.branchstock.branchstock.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.andycaicedo.branchstock.branchstock.entity.Branch;

import reactor.core.publisher.Flux;

@Repository
public interface BranchRepository extends ReactiveCrudRepository<Branch, Long> {
    
    Flux<Branch> findByFranchiseId(Long franchiseId);
}
