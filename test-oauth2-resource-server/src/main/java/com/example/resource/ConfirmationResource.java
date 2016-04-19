package com.example.resource;

import com.example.dto.ConfirmationDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by bertrand on 17/04/2016.
 */
@RestController
@RequestMapping("/api/v1")
public class ConfirmationResource {

    @ApiOperation(value = "getConfirmation", nickname = "getConfirmation")
    @RequestMapping(value = "/confirmation", method = GET, produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Set.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ConfirmationDTO confirmation(){
        ConfirmationDTO result = new ConfirmationDTO();
        result.setConfirmationStatus("Hello World !");
        return result;
    }
}
