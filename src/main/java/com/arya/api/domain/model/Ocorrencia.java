package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Entity
@Table(name = "ARYA_OCORRENCIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idOcorrencia;

    @Column(name = "tipo_ocorrencia", nullable = false, length = 100)
    private String tipoOcorrencia;

    @Column(name = "nivel_severidade", nullable = false)
    private Integer nivelSeveridade;

    @Column(name = "data_ocorrencia", nullable = false)
    private OffsetDateTime dataOcorrencia;

    @Lob
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "id_area_operacao")
    private AreaOperacao areaOperacao;
}
