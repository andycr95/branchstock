package com.andycaicedo.branchstock.branchstock.dto.branch;

import java.util.List;

import com.andycaicedo.branchstock.branchstock.entity.Product;

import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@NoArgsConstructor  
@Data  
public class BranchProductsDTO {

    public BranchProductsDTO(Long id, String string, Long franchiseId, List<Product> products) {
        this.franchiseId = franchiseId;
        this.id = id;
        this.name = string;
        this.products = products;
    }
    private Long id;  
    private String name;  
    private Long franchiseId;
    private List<Product> products;
}
