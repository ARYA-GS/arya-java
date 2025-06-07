package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.request.DroneCadastroRequest;
import com.arya.api.adapter.http.dto.response.DroneResposta;
import com.arya.api.adapter.repository.DroneRepository;
import com.arya.api.adapter.repository.EspecificacaoRepository;
import com.arya.api.adapter.repository.HubOperacionalRepository;
import com.arya.api.domain.mapper.DroneMapper;
import com.arya.api.domain.mapper.EspecificacaoMapper;
import com.arya.api.domain.model.Drone;
import com.arya.api.domain.model.HubOperacional;
import com.arya.api.infra.messaging.DroneProducer;
import com.arya.api.usecase.service.DroneService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private EspecificacaoRepository especificacaoRepository;

    @Autowired
    private HubOperacionalRepository hubRepository;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private EspecificacaoMapper especificacaoMapper;

    @Autowired
    private DroneProducer droneProducer;

    @Override
    public DroneResposta salvar(DroneCadastroRequest request) {
        HubOperacional hub = hubRepository.findById(request.getIdHub())
                .orElseThrow(() -> new EntityNotFoundException("Hub operacional não encontrado"));

        Drone drone = droneMapper.converterParaModelo(request, hub);
        drone = droneRepository.save(drone);

        droneProducer.send(drone);

        return droneMapper.converterParaResposta(drone);
    }

    @Override
    public List<DroneResposta> listarTodos() {
        return droneRepository.findAll()
                .stream()
                .map(droneMapper::converterParaResposta)
                .collect(Collectors.toList());
    }

    @Override
    public DroneResposta buscarPorId(String id) {
        Drone drone = droneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drone não encontrado"));
        return droneMapper.converterParaResposta(drone);
    }

    @Override
    public DroneResposta atualizar(String id, DroneCadastroRequest request) {
        Drone existente = droneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drone não encontrado"));

        HubOperacional hub = hubRepository.findById(request.getIdHub())
                .orElseThrow(() -> new EntityNotFoundException("Hub operacional não encontrado"));



        existente.setModelo(request.getModelo());
        existente.setAlcanceKm(request.getAlcanceKm());
        existente.setCargaKg(request.getCargaKg());
        existente.setFuncoes(request.getFuncoes());
        existente.setHub(hub);

        existente = droneRepository.save(existente);

        return droneMapper.converterParaResposta(existente);
    }

    @Override
    public void deletar(String id) {
        Drone drone = droneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Drone não encontrado"));
        droneRepository.delete(drone);
    }
}

