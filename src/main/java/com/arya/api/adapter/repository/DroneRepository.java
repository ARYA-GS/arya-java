package com.arya.api.adapter.repository;

import com.arya.api.domain.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, String> {
}
