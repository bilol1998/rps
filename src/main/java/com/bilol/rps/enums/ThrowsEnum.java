package com.bilol.rps.enums;

import lombok.Getter;

import java.util.stream.Stream;

public enum ThrowsEnum {
    ROCK("rock"),
    PAPER("paper"),
    SCISSORS("scissors");

    @Getter
    final String title;

    ThrowsEnum(String name) {
        this.title = name;
    }

    public static ThrowsEnum fromString(String title) {
        return Stream.of(values())
                .filter(throwsEnum -> throwsEnum.getTitle().equalsIgnoreCase(title))
                .findAny()
                .orElse(null);
    }
}
