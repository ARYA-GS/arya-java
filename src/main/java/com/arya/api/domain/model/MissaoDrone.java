package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ARYA_MISSAO_DRONE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissaoDrone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idMissao;

    @ManyToOne
    @JoinColumn(name = "id_drone", nullable = false)
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "id_ocorrencia", nullable = false)
    private Ocorrencia ocorrencia;

    @Column(nullable = false)
    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;

    @Column(length = 50)
    private String status;
}
