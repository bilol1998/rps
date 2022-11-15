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

    /**
     * Returns Throw response from curb api.
     * These throw responses include only rock, paper, scissors
     *
     * @return Throw response from curb API
     */
    public CurbThrowResponse retrieveThrow() throws RuntimeException {
        return restTemplate.getForObject(CURB_API, CurbThrowResponse.class);
    }
}
