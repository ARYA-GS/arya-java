package com.arya.api.usecase.service;

import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.MissaoDrone;
import com.arya.api.domain.model.Ocorrencia;

public interface MissaoDroneService {
    MissaoDrone registrarMissao(Drone drone, Ocorrencia ocorrencia);
}
