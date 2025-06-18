package com.louzao.application.domain.ports.secondary.repository.mssql.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.OutputVatEntity;
import java.util.List;

public interface OutputVatRepository {

  List<OutputVatEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
