package com.louzao.priadapter.api.adapter;

import com.louzao.priadapter.BaseIntegrationTest;

public class ControllerMaster extends BaseIntegrationTest {
/*
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

 */
}
