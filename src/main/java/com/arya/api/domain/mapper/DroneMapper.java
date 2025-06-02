package com.arya.api.domain.mapper;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.Especificacao;
import com.arya.api.domain.model.HubOperacional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroneMapper {

//    @Autowired
//    private EspecificacaoMapper especificacaoMapper;

    public Drone converterParaModelo(DroneCadastroRequest request, HubOperacional hub) {
        return Drone.builder()
                .modelo(request.getModelo())
                .alcanceKm(request.getAlcanceKm())
                .cargaKg(request.getCargaKg())
                .funcoes(request.getFuncoes())
                .hub(hub)
                .build();
    }

    public DroneResposta converterParaResposta(Drone drone) {
        return DroneResposta.builder()
                .idDrone(drone.getIdDrone())
                .modelo(drone.getModelo())
                .alcanceKm(drone.getAlcanceKm())
                .cargaKg(drone.getCargaKg())
                .funcoes(drone.getFuncoes())
                .build();
    }
}
