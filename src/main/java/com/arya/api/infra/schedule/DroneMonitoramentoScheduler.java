package com.arya.api.infra.schedule;

import com.arya.api.adapter.repository.DroneRepository;
import com.arya.api.adapter.repository.MissaoDroneRepository;
import com.arya.api.adapter.repository.OcorrenciaRepository;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.Ocorrencia;
import com.arya.api.infra.notification.NotificationService;
import com.arya.api.usecase.service.MissaoDroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneMonitoramentoScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DroneMonitoramentoScheduler.class);

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MissaoDroneRepository missaoDroneRepository;

    @Autowired
    private MissaoDroneService missaoDroneService;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void verificarOcorrencias() {
        List<Ocorrencia> ocorrencias = ocorrenciaRepository.findAll();
        for (Ocorrencia ocorrencia : ocorrencias) {
            if (ocorrencia.getNivelSeveridade() != null && ocorrencia.getNivelSeveridade() >= 8
                    && !missaoDroneRepository.existsByOcorrencia(ocorrencia)) {
                List<Drone> drones = droneRepository.findAll();
                if (!drones.isEmpty()) {
                    Drone drone = drones.get(0);
                    missaoDroneService.registrarMissao(drone, ocorrencia);
                    notificationService.enviarAlerta("Drone " + drone.getIdDrone() +
                            " despachado para ocorrência " + ocorrencia.getIdOcorrencia());
                    LOGGER.info("Drone {} despachado para ocorrência {}", drone.getIdDrone(), ocorrencia.getIdOcorrencia());
                } else {
                    LOGGER.warn("Nenhum drone disponível para atender ocorrência {}", ocorrencia.getIdOcorrencia());
                }
            }
        }
    }
}
