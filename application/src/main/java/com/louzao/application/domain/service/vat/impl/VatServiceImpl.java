package com.louzao.application.domain.service.vat.impl;

import com.louzao.application.domain.ports.primary.dto.request.InputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.InputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatConciliationDto;
import com.louzao.application.domain.ports.primary.dto.request.OutputVatDto;
import com.louzao.application.domain.ports.primary.dto.request.VatAccountBalanceDto;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.InputVatConciliationRepository;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.InputVatRepository;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.OutputVatConciliationRepository;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.OutputVatRepository;
import com.louzao.application.domain.ports.secondary.repository.mssql.vat.VatAccountBalanceRepository;
import com.louzao.application.domain.service.mapper.InputVatConciliationDtoMapper;
import com.louzao.application.domain.service.mapper.InputVatDtoMapper;
import com.louzao.application.domain.service.mapper.OutputVatConciliationDtoMapper;
import com.louzao.application.domain.service.mapper.OutputVatDtoMapper;
import com.louzao.application.domain.service.mapper.VatAccountBalanceDtoMapper;
import com.louzao.application.domain.service.vat.VatService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VatServiceImpl implements VatService {

  @Autowired
  private InputVatRepository inputVatRepository;
  @Autowired
  private OutputVatRepository outputVatRepository;
  @Autowired
  private InputVatConciliationRepository inputVatConciliationRepository;
  @Autowired
  private OutputVatConciliationRepository outputVatConciliationRepository;
  @Autowired
  private VatAccountBalanceRepository vatAccountBalanceRepository;
  @Autowired
  private InputVatDtoMapper inputVatDtoMapper;
  @Autowired
  private OutputVatDtoMapper outputVatDtoMapper;
  @Autowired
  private InputVatConciliationDtoMapper inputVatConciliationDtoMapper;
  @Autowired
  private OutputVatConciliationDtoMapper outputVatConciliationDtoMapper;
  @Autowired
  private VatAccountBalanceDtoMapper vatAccountBalanceDtoMapper;

  @Override
  public List<InputVatDto> findInputVatByCompanyYearMonth(String company, Integer year, List<Integer> month) {
    return inputVatDtoMapper.toInputVatDtoList(
        new ArrayList<>((Collection) inputVatRepository.findByCompanyYearMonth(company, year, month)));
  }

  @Override
  public List<OutputVatDto> findOutputVatByCompanyYearMonth(String company, Integer year, List<Integer> month) {
    return outputVatDtoMapper.toOutputVatDtoList(
        new ArrayList<>((Collection) outputVatRepository.findByCompanyYearMonth(company, year, month)));
  }

  @Override
  public List<InputVatConciliationDto> findInputVatConciliationByCompanyYearMonth(String company, Integer year,
      List<Integer> month) {
    return inputVatConciliationDtoMapper.toInputVatConciliationDtoList(
        new ArrayList<>((Collection) inputVatConciliationRepository.findByCompanyYearMonth(company, year, month)));
  }

  @Override
  public List<OutputVatConciliationDto> findOutputVatConciliationByCompanyYearMonth(String company, Integer year,
      List<Integer> month) {
    return outputVatConciliationDtoMapper.toOutputVatConciliationDtoList(
        new ArrayList<>((Collection) outputVatConciliationRepository.findByCompanyYearMonth(company, year, month)));
  }

  @Override
  public List<VatAccountBalanceDto> findVatAccountBalanceByCompanyYearMonth(String company, Integer year,
      List<Integer> month) {
    return vatAccountBalanceDtoMapper.toVatAccountBalanceDtoList(
        new ArrayList<>((Collection) vatAccountBalanceRepository.findByCompanyYearMonth(company, year, month)));
  }
}
