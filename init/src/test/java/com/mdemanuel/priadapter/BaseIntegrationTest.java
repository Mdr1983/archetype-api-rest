package com.mdemanuel.priadapter;

import com.mdemanuel.application.domain.ports.secondary.repository.postgres.master.CategoryRepository;
import com.mdemanuel.application.domain.service.mapper.MasterDtoMapper;
import com.mdemanuel.init.StartRestApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = {SpringExtension.class, MockitoExtension.class})
//@SpringBootTest(classes = StartRestApplication.class, properties = {"wiremock.server.httpsPort=-1"})
@SpringBootTest(classes = StartRestApplication.class)
@ActiveProfiles({"test"})
@EntityScan(basePackages = {
    "com.mdemanuel.application.domain"
})
//@AutoConfigureWireMock(port = 0, files = {"classpath:/wiremock/"})
public class BaseIntegrationTest {

  @SpyBean
  protected MasterDtoMapper masterDtoMapper;
  @SpyBean
  protected CategoryRepository categoryRepository;
  @SpyBean
  @Qualifier("namedParameterJdbcTemplate")
  protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
