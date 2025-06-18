package com.louzao.application.domain.service.vat;

import com.louzao.application.domain.ports.primary.dto.request.InputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.InputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.VatAccountBalanceDto;
import java.util.List;

public interface VatService {

  List<InputVatDto> findInputVatByCompanyYearMonth(String company, Integer year, List<Integer> month);

  List<OutputVatDto> findOutputVatByCompanyYearMonth(String company, Integer year, List<Integer> month);

  List<InputVatConciliationDto> findInputVatConciliationByCompanyYearMonth(String company, Integer year,
      List<Integer> month);

  List<OutputVatConciliationDto> findOutputVatConciliationByCompanyYearMonth(String company, Integer year,
      List<Integer> month);

  List<VatAccountBalanceDto> findVatAccountBalanceByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
