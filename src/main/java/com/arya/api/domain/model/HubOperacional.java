package com.arya.api.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ARYA_HUB_OPERACIONAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HubOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idHub;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 50)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;
}
