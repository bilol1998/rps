package com.bilol.rps.dto;

import com.bilol.rps.enums.ResultEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ThrowResponse {
    private String serversThrow;
    private ResultEnum result;
}
