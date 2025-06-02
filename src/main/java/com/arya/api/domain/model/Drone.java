package com.arya.api.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ARYA_DRONE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idDrone;

    @ManyToOne
    @JoinColumn(name = "id_hub")
    private HubOperacional hub;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(nullable = false)
    private Integer alcanceKm;

    @Column(nullable = false)
    private Double cargaKg;

    @ElementCollection
    @CollectionTable(name = "ARYA_DRONE_FUNCOES", joinColumns = @JoinColumn(name = "id_drone"))
    @Column(name = "funcao")
    private List<String> funcoes;

}
