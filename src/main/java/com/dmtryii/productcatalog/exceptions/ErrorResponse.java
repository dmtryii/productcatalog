package com.dmtryii.productcatalog.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Custom error response for validation errors")
public class ErrorResponse {
    @Schema(
            description = "Timestamp when the error occurred",
            example = "2024-12-14T20:39:48.716285"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "400"
    )
    private int status;

    @Schema(
            description = "Error name",
            example = "Validation Error"
    )
    private String error;

    @Schema(
            description = "Description of the error",
            example = "Validation errors"
    )
    private String message;
}
