package com.andycaicedo.branchstock.branchstock.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;  
  

@Data  
public class UpdateProductDTO {
    @NotNull(message = "BranchId is required")
    @Positive(message = "BranchId must be a positive number")
    private int stock;

    public UpdateProductDTO(int stock) {
        this.stock = stock;
    }
}
