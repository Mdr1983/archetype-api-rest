package com.mdemanuel.priadapter.api.adapter;

import com.mdemanuel.application.domain.ports.primary.dto.request.GenericDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.primary.dto.response.controller.ApiExceptionResponseDto;
import com.mdemanuel.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
import com.mdemanuel.application.domain.service.exceptions.ValidateJsonException;
import com.mdemanuel.application.domain.service.master.MasterGenericMongoService;
import com.mdemanuel.application.util.aspect.LogExecution;
import com.mdemanuel.application.util.json.JsonValidationSchema;
import com.mdemanuel.application.util.json.JsonValidationSchema.TypeSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("controllerMongoMasterGeneric")
@RequestMapping(path = "/mongo/master/generic")
@Tag(name = "MongoMasterGeneric")
public class ControllerMongoMasterGeneric {

  @Autowired
  private JsonValidationSchema jsonValidationSchema;
  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private MasterGenericMongoService masterGenericMongoService;

  @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get all category",
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
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<GenericDto>>> getAllCategory(HttpServletRequest request) {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            masterGenericMongoService.getAllCategory()), HttpStatus.OK);
  }

  @PostMapping(value = "/category/filters", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get all category with filters",
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
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<Page<GenericDto>>> getAllCategory(HttpServletRequest request,
      @RequestBody @Valid @NotNull SearchCriteriaDto dto)
      throws IOException, ValidateJsonException {
    jsonValidationSchema.validateJson(TypeSchema.SEARCH_CRITERIA, dto);

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            masterGenericMongoService.getAllCategory(dto)), HttpStatus.OK);
  }

  @GetMapping(value = "/category/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get category",
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
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<GenericDto>> getCategory(HttpServletRequest request, @PathVariable String code)
      throws ItemNotFoundException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            masterGenericMongoService.getCategory(code)), HttpStatus.OK);
  }

  @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Add category",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Created"
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
  public ResponseEntity<ApiResponseDto> addCategory(HttpServletRequest request,
      @RequestBody @Valid @NotNull GenericDto dto)
      throws DuplicatedItemException, IOException, ValidateJsonException {
    jsonValidationSchema.validateJson(TypeSchema.CATEGORY, dto);

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), request.getRequestURI(),
            masterGenericMongoService.addCategory(dto)), HttpStatus.CREATED);
  }

  @PutMapping(value = "/category/{code}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Update category",
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
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto> updateCategory(HttpServletRequest request,
      @RequestBody @Valid @NotNull GenericDto dto, @PathVariable String code)
      throws ItemNotFoundException, BadFormatException, IOException, ValidateJsonException {
    jsonValidationSchema.validateJson(TypeSchema.CATEGORY, dto);

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            masterGenericMongoService.updateCategory(dto, code)), HttpStatus.OK);
  }

  @DeleteMapping(value = "/category/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Delete category",
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
  public ResponseEntity<ApiResponseDto> deleteCategory(HttpServletRequest request, @PathVariable String code)
      throws ItemNotFoundException {
    masterGenericMongoService.deleteCategory(code);

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(),
            request.getRequestURI(), null), HttpStatus.NO_CONTENT);
  }
}
