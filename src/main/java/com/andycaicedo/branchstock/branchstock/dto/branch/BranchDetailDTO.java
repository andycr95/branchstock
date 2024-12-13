package com.andycaicedo.branchstock.branchstock.dto.branch;

import java.util.List;

import com.andycaicedo.branchstock.branchstock.entity.Franchise;
import com.andycaicedo.branchstock.branchstock.entity.Product;

import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@NoArgsConstructor  
@Data  
public class BranchDetailDTO {

    public BranchDetailDTO(Long id, String string, Franchise franchise, List<Product> products) {
        this.id = id;
        this.name = string;
        this.franchise = franchise;
        this.products = products;
    }
    private Long id;  
    private String name;  
    private Franchise franchise;
    private List<Product> products;
}
