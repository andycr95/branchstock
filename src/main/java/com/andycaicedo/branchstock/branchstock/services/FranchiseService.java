package com.andycaicedo.branchstock.branchstock.services;

import com.andycaicedo.branchstock.branchstock.dto.franchise.CreateFranchiseDTO;
import com.andycaicedo.branchstock.branchstock.dto.franchise.FranchiseDetailDTO;
import com.andycaicedo.branchstock.branchstock.dto.franchise.FranchiseProducsDTO;
import com.andycaicedo.branchstock.branchstock.dto.product.ProductDTO;
import com.andycaicedo.branchstock.branchstock.entity.Branch;
import com.andycaicedo.branchstock.branchstock.entity.Franchise;
import com.andycaicedo.branchstock.branchstock.repository.BranchRepository;
import com.andycaicedo.branchstock.branchstock.repository.FranchiseRepository;
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
public class FranchiseService {
    @Autowired  
    private FranchiseRepository franchiseRepository; 

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired  
    private ModelMapper modelMapper;

    @Transactional
    public Mono<Franchise> createFranchise(CreateFranchiseDTO franchiseDTO){  
        Franchise franchise = modelMapper.map(franchiseDTO, Franchise.class);
        return franchiseRepository.save(franchise);
    }  

    @Transactional
    public Mono<Franchise> updateFranchise(Long id, CreateFranchiseDTO franchiseDTO){
        return franchiseRepository.findById(id)
        .flatMap(franchise -> {
            franchise.setName(franchiseDTO.getName());
            return franchiseRepository.save(franchise);
        });
    }

    public Flux<Franchise> getFranchises(){
       return franchiseRepository.findAll();
    }

    public Mono<FranchiseDetailDTO> getFranchiseById(Long id){
       return franchiseRepository.findById(id)
        .flatMap(franchise -> {
            Flux<Branch> branchesFlux = branchRepository.findByFranchiseId(franchise.getId());

            return branchesFlux.collectList()
                .map(branches -> {
                    FranchiseDetailDTO franchiseDetailDTO = new FranchiseDetailDTO();
                    franchiseDetailDTO.setId(franchise.getId());
                    franchiseDetailDTO.setName(franchise.getName());
                    franchiseDetailDTO.setBranches(branches);
                    return franchiseDetailDTO;
                });
        })
        .switchIfEmpty(Mono.error(new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Franchise with ID " + id + " not found"
        )));

    }

    public Flux<FranchiseDetailDTO> getFranchisesWithBranches(){
        return franchiseRepository.findAll()
        .flatMap(franchise -> branchRepository.findByFranchiseId(franchise.getId())
            .collectList()
            .map(branches -> {
                FranchiseDetailDTO franchiseDetailDTO = new FranchiseDetailDTO();
                franchiseDetailDTO.setId(franchise.getId());
                franchiseDetailDTO.setName(franchise.getName());
                if (!branches.isEmpty()) {
                    franchiseDetailDTO.setBranches(branches);
                }
                
                return franchiseDetailDTO;
            })
        );
    }

    public Mono<FranchiseProducsDTO> getFranchisesWithProductsHighStock(Long franchiseId) {
        return franchiseRepository.findById(franchiseId)
            .flatMap(franchise -> branchRepository.findByFranchiseId(franchiseId)
                .flatMap(branch -> productRepository.findTopByBranchIdOrderByStockDesc(branch.getId())
                    .map(product -> new ProductDTO(branch.getId(), product.getName(), branch.getName(), branch.getId(), product.getStock())))
                .collectList()
                .map(branchProducts -> {
                    FranchiseProducsDTO franchiseProducsDTO = new FranchiseProducsDTO();
                    franchiseProducsDTO.setId(franchise.getId());
                    franchiseProducsDTO.setName(franchise.getName());
                    franchiseProducsDTO.setProducts(branchProducts);
                    return franchiseProducsDTO;
                }))
            .switchIfEmpty(Mono.error(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Franchise with ID " + franchiseId + " not found"
            )));
    }

}
