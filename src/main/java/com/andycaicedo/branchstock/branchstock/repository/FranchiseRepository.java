package com.andycaicedo.branchstock.branchstock.repository;

import org.springframework.stereotype.Repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.andycaicedo.branchstock.branchstock.entity.Franchise;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {}

