package com.louzao.application.domain.ports.secondary.repository.mssql.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.VatAccountBalanceEntity;
import java.util.List;

public interface VatAccountBalanceRepository {

  List<VatAccountBalanceEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month);
}
