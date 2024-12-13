package com.andycaicedo.branchstock.branchstock.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;  
  

@Data  
public class CreateProductDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 255, min = 3, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "BranchId is required")
    @Positive(message = "BranchId must be a positive number")
    private int stock;

    @NotNull(message = "BranchId is required")
    @Positive(message = "BranchId must be a positive number")
    private Long branchId;

    public CreateProductDTO(String name, Long branchId, int stock) {
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
    }
}
