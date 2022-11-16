package com.bilol.rps.controller;

import com.bilol.rps.constants.Constants;
import com.bilol.rps.dto.Response;
import com.bilol.rps.dto.ThrowResponse;
import com.bilol.rps.enums.ResultEnum;
import com.bilol.rps.enums.ThrowsEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.bilol.rps.constants.Constants.COMPARISON_MAP;
import static com.bilol.rps.constants.Constants.THROWS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("RPS Controller Related Tests")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class RPSControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @DisplayName("When Throw is passed, it should return server throw and compare result")
    @ParameterizedTest
    @ValueSource(strings = {"rock", "paper", "scissors"})
    void testGetServersThrow_whenThrowIsPassed_shouldCompareWithServerThrowAndReturn(String userThrow) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(Constants.RPS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("throw", userThrow);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String body = mvcResult.getResponse().getContentAsString();
        Response<ThrowResponse> response = new ObjectMapper().readValue(body, new TypeReference<Response<ThrowResponse>>() {});

        ThrowResponse throwResponse = response.getPayload();
        assertNotNull(throwResponse);
        ThrowsEnum serversThrow = ThrowsEnum.fromString(throwResponse.getServersThrow());
        ThrowsEnum userThrowEnum = ThrowsEnum.fromString(userThrow);
        assertTrue(THROWS.contains(serversThrow));
        if (userThrowEnum.equals(serversThrow)) {
            assertEquals(throwResponse.getResult(), ResultEnum.TIE, "Wrong Result Expected Tie");
        } else if (COMPARISON_MAP.get(userThrowEnum).equals(serversThrow)) {
            assertEquals(throwResponse.getResult(), ResultEnum.LOSE, "Wrong Result Expected Lose");
        } else {
            assertEquals(throwResponse.getResult(), ResultEnum.WIN, "Wrong Result Expected Win");
        }
    }
}
