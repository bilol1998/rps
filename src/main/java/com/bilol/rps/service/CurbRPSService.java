package com.bilol.rps.service;

import com.bilol.rps.dto.CurbThrowResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.bilol.rps.constants.Constants.CURB_API;

@Service
public class CurbRPSService {

    private final RestTemplate restTemplate;

    public CurbRPSService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurbThrowResponse retrieveThrow() throws RuntimeException {
        return restTemplate.getForObject(CURB_API, CurbThrowResponse.class);
    }
}
