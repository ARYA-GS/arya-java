package com.arya.api.adapter.repository;

import com.arya.api.domain.model.MissaoDrone;
import com.arya.api.domain.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissaoDroneRepository extends JpaRepository<MissaoDrone, String> {
    boolean existsByOcorrencia(Ocorrencia ocorrencia);
}
