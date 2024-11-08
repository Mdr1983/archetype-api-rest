package com.mdemanuel.priadapter.api.adapter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDataDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.CategoryDto;
import com.mdemanuel.application.domain.ports.primary.dto.request.MetadataDto;
import com.mdemanuel.application.domain.service.master.MasterService;
import com.mdemanuel.priadapter.BaseIntegrationTest;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

public class ControllerMaster extends BaseIntegrationTest {

  @LocalServerPort
  private int port;

  @MockBean
  private MasterService masterService;

  @Test
  void getAllCategory()
      throws Exception {
    // Given
    List<CategoryDto> categories = List.of(
        CategoryDto.builder().metadata(MetadataDto.builder().executedBy("testuser").build())
            .data(CategoryDataDto.builder().code("CATEGORY1").description("Category 1").build()).build(),
        CategoryDto.builder().metadata(MetadataDto.builder().executedBy("testuser").build())
            .data(CategoryDataDto.builder().code("CATEGORY2").description("Category 2").build()).build());

    when(masterService.getAllCategory()).thenReturn(categories);

    // When
    given()
        .baseUri("http://localhost:" + port)
        .contentType(ContentType.JSON)
        .when()
        .get("/master/category")
        .then()
        .statusCode(200)
        .body("data", hasSize(2))
        .body("data[0].data.code", equalTo("CATEGORY1"))
        .body("data[0].data.description", equalTo("Category 1"))
        .body("data[1].data.code", equalTo("CATEGORY2"))
        .body("data[1].data.description", equalTo("Category 2"));
  }

  @Test
  void getAllCategory_WhenServiceThrowsException_ThenReturns500()
      throws Exception {
    // Given
    when(masterService.getAllCategory()).thenThrow(new RuntimeException("Something went wrong"));

    // When
    given()
        .contentType(ContentType.JSON)
        .when()
        .get("/master/category")
        .then()
        .statusCode(500);
  }
}
