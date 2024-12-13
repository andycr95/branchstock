package com.andycaicedo.branchstock.branchstock.dto.product;

import lombok.AllArgsConstructor;  
import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@NoArgsConstructor  
@AllArgsConstructor  
@Data  
public class ProductDTO {
    public ProductDTO(Long id, String name, String branchName, Long branchId, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
        this.branchName = branchName;
    }
    private Long id;  
    private String name;  
    private int stock;
    private Long branchId;
    private String branchName;
}
