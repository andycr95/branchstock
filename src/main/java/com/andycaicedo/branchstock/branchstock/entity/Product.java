package com.andycaicedo.branchstock.branchstock.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("products")
public class Product {
    
    @Id
    @Column("id")
    private int id;


    @Column("name")
    private String name;

    @Column("stock")
    private int stock;

    @Column("branch_id")
    private int branchId;
}
