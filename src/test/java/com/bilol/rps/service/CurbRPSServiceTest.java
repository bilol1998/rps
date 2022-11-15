package com.bilol.rps.service;

import com.bilol.rps.RpsApplication;
import com.bilol.rps.constants.Constants;
import com.bilol.rps.dto.CurbThrowResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@DisplayName("Curb Service Related Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RpsApplication.class)
class CurbRPSServiceTest {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurbRPSService curbRPSService;

    private MockRestServiceServer mockServer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @DisplayName("When CURB retrieve throw api is called, it should return any of RPS throws")
    @ParameterizedTest
    @ValueSource(strings = {"rock", "paper", "scissors"})
    void testRetrieveThrow_whenApiIsCalled_shouldReturnRPSThrows(String expectedBody) throws URISyntaxException, JsonProcessingException {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(Constants.CURB_API)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(new CurbThrowResponse(200, expectedBody)))
                );
        CurbThrowResponse curbThrow = curbRPSService.retrieveThrow();

        assertNotNull(curbThrow);
        assertEquals(curbThrow.getStatusCode(), 200);
        assertEquals(curbThrow.getBody(), expectedBody);
        mockServer.verify();
    }
}
