package com.andycaicedo.branchstock.branchstock.dto.franchise;

import java.util.List;

import com.andycaicedo.branchstock.branchstock.entity.Branch;

import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@NoArgsConstructor  
@Data  
public class FranchiseDetailDTO {
    public FranchiseDetailDTO(Long id, String name, List<Branch> branches) {
        this.id = id;
        this.name = name;
        this.branches = branches;
    }

    private Long id;  
    private String name;  
    private List<Branch> branches;
}
