package com.arya.api.adapter.repository;

import com.arya.api.domain.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, String> {
}
