package com.andycaicedo.branchstock.branchstock.entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.*;


@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table("branches")
public class Branch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id")
    private Long id;


    @Column("name")
    private String name;


    @Column("franchise_id")
    private Long franchiseId;

}
