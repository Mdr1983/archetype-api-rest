package com.louzao.application.domain.ports.secondary.repository.mssql.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.InputVatEntity;
import java.util.List;

public interface InputVatRepository {

  List<InputVatEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
