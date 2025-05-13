package com.EcoMarket.Project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, length=13, unique=true)
    private Integer run;

    @Column(nullable=false)
    private String prNombre;

    @Column(nullable=false)
    private String seNombre;

    @Column(nullable=false)
    private String apPaterno;

    @Column(nullable=false)
    private String apMaterno;



}
