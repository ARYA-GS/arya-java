package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ARYA_AREA_OPERACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaOperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idAreaOperacao;

    @Column(nullable = false)
    private Double latitudeCentral;

    @Column(nullable = false)
    private Double longitudeCentral;
}
