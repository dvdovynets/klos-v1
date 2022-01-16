package com.springboot.klos.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "JWT authentication response")
@Data
public class JWTAuthResponse {
    @ApiModelProperty(value = "Actual token")
    private String accessToken;

    @ApiModelProperty(value = "Token type, 'Bearer'")
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
