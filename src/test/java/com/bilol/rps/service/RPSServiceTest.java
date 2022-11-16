package com.bilol.rps.service;

import com.bilol.rps.RpsApplication;
import com.bilol.rps.dto.CurbThrowResponse;
import com.bilol.rps.dto.ThrowResponse;
import com.bilol.rps.enums.ResultEnum;
import com.bilol.rps.enums.ThrowsEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.bilol.rps.constants.Constants.COMPARISON_MAP;
import static com.bilol.rps.constants.Constants.THROWS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("RPSService Related Tests")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RpsApplication.class)
class RPSServiceTest {

    @MockBean
    private CurbRPSService curbRPSService;

    @Autowired
    private RPSService rpsService;

    @DisplayName("When Throw is passed, it should return server throw and compare result")
    @ParameterizedTest
    @EnumSource(ThrowsEnum.class)
    void testGetAndCompareServersThrow_whenThrowIsPassed_shouldCompareWithServerThrowAndReturn(ThrowsEnum throwParam) {
        when(curbRPSService.retrieveThrow()).thenReturn(new CurbThrowResponse(200, "Paper"));

        ThrowResponse response = rpsService.getAndCompareServersThrow(throwParam);

        assertNotNull(response);
        ThrowsEnum serversThrow = ThrowsEnum.fromString(response.getServersThrow());
        assertTrue(THROWS.contains(serversThrow));
        if (throwParam.equals(serversThrow)) {
            assertEquals(response.getResult(), ResultEnum.TIE, "Wrong Result Expected Tie");
        } else if (COMPARISON_MAP.get(throwParam).equals(serversThrow)) {
            assertEquals(response.getResult(), ResultEnum.LOSE, "Wrong Result Expected Lose");
        } else {
            assertEquals(response.getResult(), ResultEnum.WIN, "Wrong Result Expected Win");
        }
    }

    @DisplayName("When Server Throw is called, it should return crub response or random throw")
    @Test
    void testGetServersThrow_whenCalled_shouldReturnRPSThrows() {
        when(curbRPSService.retrieveThrow()).thenReturn(new CurbThrowResponse(200, "rock"));

        ThrowsEnum serverThrow = rpsService.getServersThrow();

        assertNotNull(serverThrow, "Server throw is null");
        assertTrue(THROWS.contains(serverThrow), "Invalid throw type");
    }

    @DisplayName("When random throw is called, it should return random RPS throws")
    @Test
    void testGetRandomThrow_whenCalled_shouldReturnRandomRPSThrows() {
        ThrowsEnum randomThrow = rpsService.getRandomThrow();

        assertNotNull(randomThrow, "Random throw is null");
        assertTrue(THROWS.contains(randomThrow), "Invalid throw type");
    }
}
