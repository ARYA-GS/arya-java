package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ARYA_OUTPUT_MODELO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutputModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOutput;

    @Column(name = "nome_modelo_ml", length = 150)
    private String nomeModeloMl;

    @Column(name = "data_geracao", nullable = false)
    private OffsetDateTime dataGeracao;

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Column(name = "status_processamento", length = 50)
    private String statusProcessamento;

    @Lob
    private String resultado;
}
