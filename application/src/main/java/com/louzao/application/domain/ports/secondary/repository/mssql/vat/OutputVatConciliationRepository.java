package com.louzao.application.domain.ports.secondary.repository.mssql.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.OutputVatConciliationEntity;
import java.util.List;

public interface OutputVatConciliationRepository {

  List<OutputVatConciliationEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
