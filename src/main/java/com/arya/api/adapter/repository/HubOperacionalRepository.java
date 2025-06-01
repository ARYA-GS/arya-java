package com.arya.api.adapter.repository;

import com.arya.api.domain.model.HubOperacional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubOperacionalRepository extends JpaRepository<HubOperacional, String> {

    boolean existsByEndereco_LatitudeAndEndereco_Longitude(Double latitude, Double longitude);

}
