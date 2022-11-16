package com.bilol.rps.constants;

import com.bilol.rps.enums.ThrowsEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Constants {
    private Constants() {
    }

    public static final List<ThrowsEnum> THROWS = Arrays.asList(ThrowsEnum.ROCK, ThrowsEnum.PAPER, ThrowsEnum.SCISSORS);
    public static final Map<ThrowsEnum, ThrowsEnum> COMPARISON_MAP = Map.of(
            ThrowsEnum.ROCK, ThrowsEnum.PAPER,
            ThrowsEnum.PAPER, ThrowsEnum.SCISSORS,
            ThrowsEnum.SCISSORS, ThrowsEnum.ROCK
    );

    public static final String CURB_API = "https://5eddt4q9dk.execute-api.us-east-1.amazonaws.com/rps-stage/throw";

    //Endpoint Related Constants
    public static final String BASE_URL = "/api/v1";
    public static final String RPS = BASE_URL + "/rps";
}
