package com.example.recipestory;

import static org.junit.Assert.assertEquals;

import com.example.recipestory.datatransferobj.ErrorResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.exception.InvalidRecipeException;
import com.example.recipestory.exception.RecipeNotFoundException;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeServiceTests {
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Autowired
  RecipeService recipeService;

  ObjectMapper jsonMapper = new ObjectMapper();

  @Test
  public void getOneRecipe() throws JsonParseException, JsonMappingException, IOException {
    //setup
    RecipeDto expected = TestObjectRepo.getOneRecipe();
    int testRecipeId = expected.getId();
    
    //act
    RecipeDto actual = recipeService.getRecipe(testRecipeId);
    
    //verify
    assertEquals(expected, actual);
  }

  @Test
  public void getOneRecipe_nonexistent() throws InvalidRecipeException {
    //setup
    expectedEx.expect(RecipeNotFoundException.class);
    int testRecipeId = -1;
    
    //act
    recipeService.getRecipe(testRecipeId);
    
    //verify -- done above
  }

  @Test
  public void getAllRecipes() throws JsonParseException, JsonMappingException, IOException {
    //setup
    List<RecipeDto> expected = TestObjectRepo.getAllRecipes();
    
    //act
    List<RecipeDto> actual = recipeService.getAllRecipes();
    
    //verify
    assertEquals(expected, actual);
  }

  @Test
  public void addRecipe() throws Exception {
    //setup
    List<RecipeDto> expectedRecipeList = TestObjectRepo.getAllRecipes();
    RecipeDto newRecipe = TestObjectRepo.getAdditionalRecipe();
    
    RecipeDto expectedRecipe = TestObjectRepo.getAdditionalRecipe();
    expectedRecipeList.add(expectedRecipe);
    expectedRecipe.setId(expectedRecipeList.size());
    
    //act
    RecipeDto actual = recipeService.addRecipe(newRecipe);
    List<RecipeDto> actualRecipeList = recipeService.getAllRecipes();
    
    //verify
    assertEquals(expectedRecipe, actual);
    assertEquals(expectedRecipeList, actualRecipeList);
  }

  @Test
  public void editRecipe() throws Exception  {
    //setup
    List<RecipeDto> expectedRecipeList = TestObjectRepo.getAllRecipes();
    RecipeDto newRecipe =  TestObjectRepo.getAdditionalRecipe();;
    
    RecipeDto expectedRecipe = TestObjectRepo.getAdditionalRecipe();;
    int targetId = expectedRecipeList.get(0).getId();
    expectedRecipeList.set(0, expectedRecipe);
    expectedRecipe.setId(targetId);
    
    //act
    RecipeDto actual = recipeService.editRecipe(targetId, newRecipe);
    List<RecipeDto> actualRecipeList = recipeService.getAllRecipes();
    
    //verify
    assertEquals(expectedRecipeList, actualRecipeList);
    assertEquals(expectedRecipe, actual);
  }

  @Test
  public void editRecipe_nonexistent() throws Exception {
    //setup
    expectedEx.expect(InvalidRecipeException.class);
    expectedEx.expectMessage(ErrorResponse.Message.NOT_FOUND.getMessage());

    RecipeDto newRecipe =  TestObjectRepo.getAdditionalRecipe();
    int testRecipeId = -1;
    
    //act
    recipeService.editRecipe(testRecipeId, newRecipe);
    
    //verify -- done above
  }

  @Test
  public void removeRecipe() throws Exception {
    //setup
    List<RecipeDto> expectedRecipeList = TestObjectRepo.getAllRecipes();
    RecipeDto expectedRecipe = expectedRecipeList.remove(0);
    
    //act
    RecipeDto actualRecipe = recipeService.removeRecipe(expectedRecipe.getId());
    List<RecipeDto> actualRecipeList = recipeService.getAllRecipes();
    
    //verify
    assertEquals(expectedRecipeList, actualRecipeList);
    assertEquals(expectedRecipe, actualRecipe);
  }

  @Test
  public void removeRecipe_nonexistent() throws InvalidRecipeException {
    //setup
    expectedEx.expect(RecipeNotFoundException.class);
    int testRecipeId = -1;
    
    //act
    recipeService.removeRecipe(testRecipeId);
    
    //verify -- done above
  }

}
