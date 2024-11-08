package com.mdemanuel.priadapter.api.adapter;

import com.mdemanuel.application.domain.service.master.MasterService;
import com.mdemanuel.init.StartRestApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(value = {SpringExtension.class, MockitoExtension.class})
//@SpringBootTest(classes = StartRestApplication.class, properties = {"wiremock.server.httpsPort=-1"})
@SpringBootTest(classes = StartRestApplication.class)
@ActiveProfiles({"test"})
@EntityScan(basePackages = {
    "com.byl.mdemanuel.application.domain"
})
public class ControllerMaster2 {

  @LocalServerPort
  private int port;

  @MockBean
  private MasterService masterService;

  @Test
  void getAllCategory()
      throws Exception {
    System.out.println();
  }
}
