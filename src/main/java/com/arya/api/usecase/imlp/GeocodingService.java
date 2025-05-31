package com.arya.api.usecase.imlp;

import com.arya.api.adapter.http.dto.response.Geometry;
import com.arya.api.adapter.http.dto.response.OpenCageResponse;
import com.arya.api.adapter.repository.OpenCageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeocodingService {

    @Autowired
    private OpenCageClient openCageClient;

    @Value("${opencage.api.key}")
    private String apiKey;

    public Optional<double[]> obterCoordenadas(String enderecoCompleto) {
        OpenCageResponse response = openCageClient.geocode(enderecoCompleto, apiKey);
        if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
            Geometry geometry = response.getResults().get(0).getGeometry();
            return Optional.of(new double[]{geometry.getLat(), geometry.getLng()});
        }
        return Optional.empty();
    }
}