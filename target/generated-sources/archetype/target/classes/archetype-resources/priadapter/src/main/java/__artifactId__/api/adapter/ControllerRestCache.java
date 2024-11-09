#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${artifactId}.api.adapter;

import ${package}.application.domain.ports.primary.dto.response.controller.ApiExceptionResponseDto;
import ${package}.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import ${package}.application.domain.service.cache.CacheService;
import ${package}.application.util.aspect.LogExecution;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("controllerRestCache")
@RequestMapping(path = "/utils/cache")
@Tag(name = "Utils")
public class ControllerRestCache {

  private final CacheService cacheService;

  @Autowired
  public ControllerRestCache(final CacheService cacheService) {
    this.cacheService = cacheService;
  }

  @PostMapping(value = "/cleanCache", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Evict all caches",
      responses = {
          @ApiResponse(
              responseCode = "204",
              description = "No Content"
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
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto> cleanCache(HttpServletRequest request) {
    cacheService.evictAll();

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(),
            request.getRequestURI(), null), HttpStatus.NO_CONTENT);
  }
}
