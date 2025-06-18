package com.louzao.application.domain.ports.secondary.repository.mssql.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.InputVatConciliationEntity;
import java.util.List;

public interface InputVatConciliationRepository {

  List<InputVatConciliationEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
