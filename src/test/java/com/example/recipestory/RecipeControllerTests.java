package com.example.recipestory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.recipestory.TestObjectRepo;
import com.example.recipestory.controller.RecipeController;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.exception.RecipeNotFoundException;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

  @Mock
  RecipeService mockRecipeService;

  @InjectMocks
  RecipeController recipeController;

  @Autowired
  WebApplicationContext wac;

  @LocalServerPort
  int port;

  @Value("${local.api.url.template}")
  String urlTemplate;

  static MockMvc mockMvc;
  ObjectMapper jsonMapper = new ObjectMapper();

  @Before
  public void setup() throws IOException {
    mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
  }

  @Test
  public void getAllRecipes() throws Exception {
    // setup
    List<RecipeDto> expectedRecipes = TestObjectRepo.getAllRecipes();
    when(mockRecipeService.getAllRecipes()).thenReturn(expectedRecipes);

    String requestUrl = String.format(urlTemplate, port, "");
    String expected = TestObjectRepo.getAllRecipesResponseAsString();

    // act
    mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
        // verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).getAllRecipes();
  }

  @Test
  public void getOneRecipe() throws Exception {
    //setup
    int recipeId = 1;
    when(mockRecipeService.getRecipe(recipeId)).thenReturn(TestObjectRepo.getOneRecipe());
    
    String requestUrl = String.format(urlTemplate, port, recipeId);
    String expected = TestObjectRepo.getOneRecipeResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
      //verify
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(content().json(expected));

    verify(mockRecipeService).getRecipe(recipeId);
  }

  @Test
  public void getNonexistentRecipe() throws Exception {
    //setup
    int recipeId = 999;
    when(mockRecipeService.getRecipe(recipeId)).thenThrow(new RecipeNotFoundException());
    
    String requestUrl = String.format(urlTemplate, port, recipeId);
    String expected = TestObjectRepo.getNotFoundResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.get(requestUrl))
        //verify
        .andExpect(status().is(404))
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).getRecipe(recipeId);
  }

  @Test
  public void addRecipe() throws Exception {
    //setup
    String requestUrl = String.format(urlTemplate, port, "");
    RecipeDto recipe = TestObjectRepo.getAdditionalRecipe();
    String parameter = jsonMapper.writeValueAsString(recipe);
    String expected = TestObjectRepo.getCreationSuccessResponseAsString();
    
    //act
    MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post(requestUrl)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(parameter))
    
        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        //.andExpect(content().json(expected))
        .andReturn();
    verify(mockRecipeService).addRecipe(recipe);
  }

  @Test
  public void editRecipe() throws Exception {
    //setup
    String requestUrl = String.format(urlTemplate, port, 1);
    RecipeDto recipe = TestObjectRepo.getAdditionalRecipe();
    String parameter = jsonMapper.writeValueAsString(recipe);
    System.out.println(parameter);
    String expected = TestObjectRepo.getUpdateSuccessResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(parameter))
    
        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).addRecipe(recipe);
  }

  @Test
  public void updateNonexistentRecipe() throws Exception {
    //setup
    int recipeId = 999;
    String requestUrl = String.format(urlTemplate, port, recipeId);
    RecipeDto recipe = TestObjectRepo.getAdditionalRecipe();
    String parameter = jsonMapper.writeValueAsString(recipe);
    System.out.println(parameter);
    String expected = TestObjectRepo.getNotFoundResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.patch(requestUrl)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(parameter))
    
        //verify
        .andExpect(status().is(404))
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).editRecipe(eq(recipeId), any(RecipeDto.class));
  }
  
  @Test
  public void deleteOneRecipe() throws Exception {
    //setup
    RecipeDto expectedRecipe = TestObjectRepo.getOneRecipe();
    int recipeId = expectedRecipe.getId();
    when(mockRecipeService.removeRecipe(recipeId)).thenReturn(expectedRecipe);
    
    String requestUrl = String.format(urlTemplate, port, recipeId);
    String expected = TestObjectRepo.getDeletionSuccessResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
    
        //verify
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).removeRecipe(recipeId);
  }

  @Test
  public void deleteNonexistentRecipe() throws Exception {
    //setup
    int recipeId = 999;
    when(mockRecipeService.removeRecipe(recipeId)).thenThrow(new RecipeNotFoundException());
    
    String requestUrl = String.format(urlTemplate, port, recipeId);
    String expected = TestObjectRepo.getNotFoundResponseAsString();
    
    //act
    mockMvc.perform(MockMvcRequestBuilders.delete(requestUrl))
    
        //verify
        .andExpect(status().is(404))
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().json(expected));
    verify(mockRecipeService).removeRecipe(recipeId);
  }
}
