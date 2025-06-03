package com.arya.api.usecase.imlp;

import com.arya.api.adapter.repository.MissaoDroneRepository;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.MissaoDrone;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.usecase.service.MissaoDroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class MissaoDroneServiceImpl implements MissaoDroneService {

    @Autowired
    private MissaoDroneRepository missaoDroneRepository;

    @Override
    public MissaoDrone registrarMissao(Drone drone, Ocorrencia ocorrencia) {
        MissaoDrone missao = MissaoDrone.builder()
                .drone(drone)
                .ocorrencia(ocorrencia)
                .dataInicio(OffsetDateTime.now())
                .status("DESPACHADO")
                .build();
        return missaoDroneRepository.save(missao);
    }
}
