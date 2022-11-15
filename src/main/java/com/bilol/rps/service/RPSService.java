package com.bilol.rps.service;

import com.bilol.rps.dto.CurbThrowResponse;
import com.bilol.rps.dto.ThrowResponse;
import com.bilol.rps.enums.ResultEnum;
import com.bilol.rps.enums.ThrowsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.bilol.rps.constants.Constants.COMPARISON_MAP;
import static com.bilol.rps.constants.Constants.THROWS;

@Service
@Slf4j
public class RPSService {

    private final CurbRPSService curbRPSService;

    public RPSService(CurbRPSService curbRPSService) {
        this.curbRPSService = curbRPSService;
    }

    /**
     * Accepts one of the R-P-S throws and gets the server throw.
     * Lastly compares these two results;
     *
     * @param throwsEnum can be one of the R-P-S throws
     * @return ThrowResponse object that contains servers throw and comparison
     */
    public ThrowResponse getAndCompareServersThrow(ThrowsEnum throwsEnum) {
        if (!THROWS.contains(throwsEnum)) {
            throw new RuntimeException();
        }
        ThrowsEnum serversThrow = getServersThrow();

        ThrowResponse response = new ThrowResponse();
        response.setServersThrow(serversThrow.getTitle());
        if (throwsEnum.equals(serversThrow)) {
            response.setResult(ResultEnum.TIE);
        } else if (COMPARISON_MAP.get(throwsEnum).equals(serversThrow)) {
            response.setResult(ResultEnum.WIN);
        } else {
            response.setResult(ResultEnum.LOSE);
        }
        return response;
    }

    /**
     * Returns Throw response from curb api if it executes successfully or returns random throw.
     * These throw responses include only rock, paper, scissors
     *
     * @return Throw response from curb API or Random Throw
     */
    public ThrowsEnum getServersThrow() {
        try {
            CurbThrowResponse curbThrowResponse = curbRPSService.retrieveThrow();
            if (curbThrowResponse != null && curbThrowResponse.getBody() != null) {
                return ThrowsEnum.fromString(curbThrowResponse.getBody());
            }
        } catch (RuntimeException exception) {
            log.warn(exception.getMessage());
        }
        return getRandomThrow();
    }

    /**
     * Returns random throw response that's present in THROWS constant
     * These throw responses include only rock, paper, scissors
     *
     * @return Random Throw
     */
    public ThrowsEnum getRandomThrow() {
        Random random = new Random();
        return THROWS.get(random.nextInt(THROWS.size()));
    }

}
