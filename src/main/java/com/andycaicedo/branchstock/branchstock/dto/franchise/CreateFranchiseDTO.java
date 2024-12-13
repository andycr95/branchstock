package com.andycaicedo.branchstock.branchstock.dto.franchise;
 
import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@NoArgsConstructor  
@Data  
public class CreateFranchiseDTO {

    
    public CreateFranchiseDTO(String name) {
        this.name = name;
    }
 
    private String name;  
}
