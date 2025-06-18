package com.louzao.priadapter.api.adapter;

import com.louzao.application.domain.ports.primary.dto.request.InputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.InputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.VatAccountBalanceDto;
import com.louzao.application.domain.ports.primary.dto.response.controller.ApiExceptionResponseDto;
import com.louzao.application.domain.ports.primary.dto.response.controller.ApiResponseDto;
import com.louzao.application.domain.service.exceptions.BadParamsException;
import com.louzao.application.domain.service.exceptions.ItemNotFoundException;
import com.louzao.application.domain.service.vat.VatService;
import com.louzao.application.util.aspect.LogExecution;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("controllerFiancialVatQuarterly")
@RequestMapping(path = "/financial/vat/quarterly")
@Tag(name = "Financial - Quarterly VAT")
public class ControllerFinancialVatQuarterly {

  @Autowired
  private VatService vatService;

  @GetMapping(value = "/input_vat/{company}/{year}/{quarterly}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get input vat", responses = {@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application"
          + "/json", schema = @Schema(implementation = ApiExceptionResponseDto.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiExceptionResponseDto.class)))})
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<InputVatDto>>> getInputVat(HttpServletRequest request,
      @PathVariable String company, @PathVariable Integer year, @PathVariable Integer quarterly)
      throws ItemNotFoundException, BadParamsException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            vatService.findInputVatByCompanyYearMonth(company, year, calculateMonths(quarterly))), HttpStatus.OK);
  }

  @GetMapping(value = "/output_vat/{company}/{year}/{quarterly}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get output vat", responses = {@ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application"
          + "/json", schema = @Schema(implementation = ApiExceptionResponseDto.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiExceptionResponseDto.class)))})
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<OutputVatDto>>> getOutputVat(HttpServletRequest request,
      @PathVariable String company, @PathVariable Integer year, @PathVariable Integer quarterly)
      throws ItemNotFoundException, BadParamsException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            vatService.findOutputVatByCompanyYearMonth(company, year, calculateMonths(quarterly))), HttpStatus.OK);
  }

  @GetMapping(value = "/input_vat_conciliation/{company}/{year}/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get input vat conciliation", responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application"
          + "/json", schema = @Schema(implementation = ApiExceptionResponseDto.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiExceptionResponseDto.class)))})
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<InputVatConciliationDto>>> getInputVatConciliation(
      HttpServletRequest request, @PathVariable String company, @PathVariable Integer year,
      @PathVariable Integer quarterly)
      throws ItemNotFoundException, BadParamsException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            vatService.findInputVatConciliationByCompanyYearMonth(company, year, calculateMonths(quarterly))),
        HttpStatus.OK);
  }

  @GetMapping(value = "/output_vat_conciliation/{company}/{year}/{quarterly}", produces =
      MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get output vat conciliation", responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application"
          + "/json", schema = @Schema(implementation = ApiExceptionResponseDto.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiExceptionResponseDto.class)))})
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<OutputVatConciliationDto>>> getOutputVatConciliation(
      HttpServletRequest request, @PathVariable String company, @PathVariable Integer year,
      @PathVariable Integer quarterly)
      throws ItemNotFoundException, BadParamsException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            vatService.findOutputVatConciliationByCompanyYearMonth(company, year, calculateMonths(quarterly))),
        HttpStatus.OK);
  }

  @GetMapping(value = "/vat_account_balance/{company}/{year}/{quarterly}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = "Get vat account balance", responses = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application"
          + "/json", schema = @Schema(implementation = ApiExceptionResponseDto.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
          "application/json", schema = @Schema(implementation = ApiExceptionResponseDto.class)))})
  @ResponseBody
  @LogExecution
  public ResponseEntity<ApiResponseDto<List<VatAccountBalanceDto>>> getVatAccountBalance(HttpServletRequest request,
      @PathVariable String company, @PathVariable Integer year, @PathVariable Integer quarterly)
      throws ItemNotFoundException, BadParamsException {
    return new ResponseEntity<>(
        new ApiResponseDto<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), request.getRequestURI(),
            vatService.findVatAccountBalanceByCompanyYearMonth(company, year, calculateMonths(quarterly))),
        HttpStatus.OK);
  }

  private List<Integer> calculateMonths(Integer quarterly)
      throws BadParamsException {
    switch (quarterly) {
      case 1:
        return List.of(1, 2, 3);
      case 2:
        return List.of(4, 5, 6);
      case 3:
        return List.of(7, 8, 9);
      case 4:
        return List.of(10, 11, 12);
      default:
        throw new BadParamsException("El parámetro de quarterly no es correcto. Valores posibles (1, 2, 3, 4).");
    }
  }
}
