package com.andycaicedo.branchstock.branchstock.dto.branch;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;  

@Data  
public class CreateBranchDTO {

    
    public CreateBranchDTO(String name, Long franchiseId) {
        this.name = name;
        this.franchiseId = franchiseId;
    }


    @NotBlank(message = "name is required")
    @Size(max = 255, min = 3, message = "name must be between 3 and 255 characters")
    private String name;  


    @NotNull(message = "franchiseId is required")
    @Positive(message = "franchiseId must be a positive number")
    private Long franchiseId;

}
