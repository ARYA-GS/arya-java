package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ARYA_ESPECIFICACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idEspecificacao;

    @Column(length = 100)
    private String fabricante;

    private Integer autonomiaMinutos;

    @Column(length = 100)
    private String tipoDrone;

    @Column(length = 100)
    private String modelo;
}
