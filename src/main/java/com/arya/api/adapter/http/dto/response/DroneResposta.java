package com.arya.api.adapter.http.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneResposta {

    private String idDrone;
    private String nome;
    private String status;
}

