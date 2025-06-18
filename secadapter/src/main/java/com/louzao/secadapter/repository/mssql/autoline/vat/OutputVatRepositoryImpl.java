package com.louzao.secadapter.repository.mssql.autoline.vat;

import com.louzao.application.domain.model.domain.mssql.autoline.vat.OutputVatEntity;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.OutputVatRepository;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OutputVatRepositoryImpl implements OutputVatRepository {

  private static final String QUERY_FIND_BY_COMPANY_YEAR_MONTH = "SELECT * "
      + "FROM inf.vw_output_vat "
      + "WHERE company = :company AND YEAR(document_postdate) = :year AND MONTH(document_postdate) IN(:month)";

  @Autowired
  @Qualifier(value = "namedParameterJdbcTemplate")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<OutputVatEntity> findByCompanyYearMonth(String company, Integer year, List<Integer> month) {
    try {
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("company", company, Types.VARCHAR);
      namedParameters.addValue("year", year, Types.INTEGER);
      namedParameters.addValue("month", month, Types.INTEGER);

      return namedParameterJdbcTemplate.query(QUERY_FIND_BY_COMPANY_YEAR_MONTH, namedParameters,
          new BeanPropertyRowMapper<>(OutputVatEntity.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
