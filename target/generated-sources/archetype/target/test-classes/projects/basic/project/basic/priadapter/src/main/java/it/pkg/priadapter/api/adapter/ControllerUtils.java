package it.pkg.priadapter.api.adapter;

import it.pkg.application.domain.ports.primary.dto.response.controller.ApiExceptionResponseDto;
import it.pkg.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import it.pkg.application.util.aspect.LogExecution;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("controllerUtils")
@RequestMapping(path = "/utils")
@Tag(name = "Utils")
public class ControllerUtils {

  @Value("${project.version:0.0.0}")
  private String version;

  @GetMapping(path = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get application version",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK"
          ),
          @ApiResponse(
              responseCode = "400",
              description = "Bad Request",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                  ApiExceptionResponseDto.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                  ApiExceptionResponseDto.class))
          )
      }
  )
  @LogExecution
  public ResponseEntity<ApiResponseDto<String>> getVersion(HttpServletRequest request) {
    return new ResponseEntity<>(
        new ApiResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(), version),
        HttpStatus.OK);
  }
}
