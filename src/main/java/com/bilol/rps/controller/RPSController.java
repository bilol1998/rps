package com.bilol.rps.controller;

import com.bilol.rps.constants.Constants;
import com.bilol.rps.dto.Response;
import com.bilol.rps.dto.ThrowResponse;
import com.bilol.rps.enums.ThrowsEnum;
import com.bilol.rps.exception.BadRequestException;
import com.bilol.rps.service.RPSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.bilol.rps.constants.Constants.THROWS;

@RestController
@RequestMapping(Constants.RPS)
@Api(tags = "RPS Controller", description = "operations related to RPS")
@Slf4j
public class RPSController {

    private final RPSService rpsService;

    public RPSController(RPSService rpsService) {
        this.rpsService = rpsService;
    }

    @ApiOperation(value = "Retrieve and Compare Servers Throw")
    @GetMapping
    public Response<ThrowResponse> getServersThrow(@RequestParam("throw") String userThrow) {
        ThrowsEnum throwsEnum = ThrowsEnum.fromString(userThrow);
        if (!THROWS.contains(throwsEnum)) {
            throw new BadRequestException("Invalid throw");
        }
        ThrowResponse throwResponse = rpsService.getAndCompareServersThrow(throwsEnum);
        return Response.ok(throwResponse);
    }
}
