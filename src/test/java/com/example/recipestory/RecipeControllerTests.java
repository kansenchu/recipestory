package com.example.recipestory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.recipestory.controller.RecipeController;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * RecipeControllerのエンドポイントが叩けられるかどうかのテスト.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "local.api.url.template=http://localhost:%d/recipes/%s" })
public class RecipeControllerTests {

  @Value("${json.config.allRecipesJson}")
  File allRecipesJson;

  @Value("${json.config.allRecipesResponse}")
  File allRecipesResponse;

  @Spy
  RecipeService mockRecipeService;

  @InjectMocks
  RecipeController recipeController;

  @Autowired
  WebApplicationContext wac;

  @LocalServerPort
  int port;

  @Value("${local.api.url.template}")
  String urlTemplate;

  MockMvc mockMvc;
  ObjectMapper jsonMapper = new ObjectMapper();

  @Before
  public void setup() throws IOException {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  /**
   * 全件検索できること.
   */
  @Test
  public void getAllRecords() throws Exception {
    // setup
    List<RecipeDto> expectedRecipe = jsonMapper.readValue(allRecipesJson,
          new TypeReference<List<RecipeDto>>() {});
    when(mockRecipeService.getAllRecipes()).thenReturn(expectedRecipe);

    String requestUrl = String.format(urlTemplate, port, "");
    String expected = new String(Files.readAllBytes(allRecipesResponse.toPath()));

    // act
    mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
        // verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).getAllRecipes();
  }
}
