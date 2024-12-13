package com.andycaicedo.branchstock.branchstock.services;

import com.andycaicedo.branchstock.branchstock.dto.branch.BranchDetailDTO;
import com.andycaicedo.branchstock.branchstock.dto.branch.BranchProductsDTO;
import com.andycaicedo.branchstock.branchstock.dto.branch.CreateBranchDTO;
import com.andycaicedo.branchstock.branchstock.entity.Branch;
import com.andycaicedo.branchstock.branchstock.entity.Franchise;
import com.andycaicedo.branchstock.branchstock.entity.Product;
import com.andycaicedo.branchstock.branchstock.repository.BranchRepository;
import com.andycaicedo.branchstock.branchstock.repository.FranchiseRepository;
import com.andycaicedo.branchstock.branchstock.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;  

@Service  
@Transactional  
public class BranchService {

    @Autowired  
    private BranchRepository branchRepository; 

    @Autowired  
    private ProductRepository productRepository; 

    @Autowired  
    private FranchiseRepository franchiseRepository; 

    @Autowired  
    private ModelMapper modelMapper;
    
    @Transactional
    public Mono<Branch> createBranch(CreateBranchDTO branchDTO){  
        Branch branch = modelMapper.map(branchDTO, Branch.class);
        branch.setId(null);
        franchiseRepository.findById(branch.getFranchiseId())
        .switchIfEmpty(Mono.error(new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Franchise does not exist")));

        return franchiseRepository.findById(branch.getFranchiseId())
        .flatMap(franchise -> {
            return branchRepository.save(branch);
        });
    }

    @Transactional
    public Mono<Branch> updateBranch(Long id, CreateBranchDTO branchDTO){
        return branchRepository.findById(id)
        .switchIfEmpty(Mono.error(new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Branch does not exist")))
        .flatMap(branch -> {
            branch.setName(branchDTO.getName());
            return branchRepository.save(branch);
        });
    }

    public Flux<Branch> getBranchs(){
        return branchRepository.findAll();
    }

    public Mono<BranchDetailDTO> getBranchById(Long id){
        return branchRepository.findById(id)
                .flatMap(branch -> {
                    Mono<Franchise> franchiseMono = franchiseRepository.findById(branch.getFranchiseId())
                            .map(franchise -> new Franchise(franchise.getId(), franchise.getName()));

                    Flux<Product> productFlux = productRepository.findByBranchId(branch.getId())
                                    .map(product -> new Product(product.getId(), product.getName(), product.getStock(), product.getBranchId())
                            );

                    return franchiseMono.zipWith(productFlux.collectList())
                            .map(tuple -> {
                                Franchise franchise = tuple.getT1();
                                List<Product> products = tuple.getT2();

                                return new BranchDetailDTO(branch.getId(), branch.getName(), franchise, products);
                            });
                });
    }

    public Flux<BranchProductsDTO> getBranchsWithProducts(){
        return branchRepository.findAll()
        .flatMap(branch -> productRepository.findByBranchId(branch.getId())
        .collectList()
        .map(products -> {
            BranchProductsDTO dto = new BranchProductsDTO();
            dto.setId(branch.getId());
            dto.setName(branch.getName());
            dto.setFranchiseId(branch.getFranchiseId());
            dto.setProducts(products);
            return dto;
        }));
    }

}
