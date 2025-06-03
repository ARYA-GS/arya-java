package com.arya.api.adapter.http.client;

import com.arya.api.adapter.http.dto.response.OpenCageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openCageClient", url = "https://api.opencagedata.com")
public interface OpenCageClient {

    @GetMapping("/geocode/v1/json")
    OpenCageResponse geocode(
            @RequestParam("q") String query,
            @RequestParam("key") String apiKey
    );
}
