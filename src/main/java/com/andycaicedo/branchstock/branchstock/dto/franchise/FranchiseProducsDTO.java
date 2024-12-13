package com.andycaicedo.branchstock.branchstock.dto.franchise;

import java.util.List;

import com.andycaicedo.branchstock.branchstock.dto.product.ProductDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
  

@Data  
@NoArgsConstructor
public class FranchiseProducsDTO {
    public FranchiseProducsDTO(Long id, String name, List<ProductDTO> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    private Long id;  
    private String name;  
    private List<ProductDTO> products;
}
