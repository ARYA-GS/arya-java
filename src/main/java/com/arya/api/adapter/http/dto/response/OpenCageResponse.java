package com.arya.api.adapter.http.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OpenCageResponse {
    private List<OpenCageResult> results;
}
