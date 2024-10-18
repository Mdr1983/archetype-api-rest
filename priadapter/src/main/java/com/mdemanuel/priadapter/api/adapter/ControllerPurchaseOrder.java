package com.mdemanuel.priadapter.api.adapter;

import com.mdemanuel.application.domain.ports.primary.dto.request.PurchaseOrderDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.SearchCriteriaDto;
import com.mdemanuel.application.domain.ports.primary.dto.response.controller.ApiExceptionResponseDto;
import com.mdemanuel.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import com.mdemanuel.application.domain.service.exceptions.BadFormatException;
import com.mdemanuel.application.domain.service.exceptions.DuplicatedItemException;
import com.mdemanuel.application.domain.service.exceptions.ItemNotFoundException;
import com.mdemanuel.application.domain.service.purchase_order.PurchaseOrderService;
import com.mdemanuel.application.util.aspect.LogExecution;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController("controllerPurchaseOrder")
@RequestMapping(path = "/purchase_order")
@Tag(name = "PurchaseOrder")
public class ControllerPurchaseOrder {

  @Autowired
  private PurchaseOrderService purchaseOrderService;

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get all purchase order",
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
  public ResponseEntity<ApiResponseDto<List<PurchaseOrderDto>>> getAllPurchaseOrder(HttpServletRequest request) {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            purchaseOrderService.getAllPurchaseOrder()), HttpStatus.OK);
  }

  @PostMapping(value = "/filters", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get all purchase order with filters",
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
  public ResponseEntity<ApiResponseDto<Page<PurchaseOrderDto>>> getAllPurchaseOrder(
      @RequestBody @Valid @NotNull SearchCriteriaDto dto, HttpServletRequest request) {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            purchaseOrderService.getAllPurchaseOrder(dto)), HttpStatus.OK);
  }

  @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Get purchase order",
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
  public ResponseEntity<ApiResponseDto<PurchaseOrderDto>> getPurchaseOrder(HttpServletRequest request,
      @PathVariable String code)
      throws ItemNotFoundException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            purchaseOrderService.getPurchaseOrder(code)), HttpStatus.OK);
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Add purchase order",
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
  public ResponseEntity<ApiResponseDto> addPurchaseOrder(HttpServletRequest request,
      @RequestBody @Valid @NotNull PurchaseOrderDto dto)
      throws DuplicatedItemException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), request.getRequestURI(),
            purchaseOrderService.addPurchaseOrder(dto)), HttpStatus.CREATED);
  }

  @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Update purchase order",
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
  public ResponseEntity<ApiResponseDto> updatePurchaseOrder(HttpServletRequest request,
      @RequestBody PurchaseOrderDto dto,
      @PathVariable String code)
      throws ItemNotFoundException, BadFormatException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            purchaseOrderService.updatePurchaseOrder(dto, code)), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      description = "Delete purchase order",
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
  public ResponseEntity<ApiResponseDto> deletePurchaseOrder(HttpServletRequest request, @PathVariable String code)
      throws ItemNotFoundException {
    purchaseOrderService.deletePurchaseOrder(code);

    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(),
            request.getRequestURI(), null), HttpStatus.NO_CONTENT);
  }
}
