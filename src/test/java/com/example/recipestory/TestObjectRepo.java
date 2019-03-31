package com.example.recipestory;

import com.example.recipestory.datatransferobj.RecipeDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class TestObjectRepo {
  private static Path basePath = Paths.get("src/test/resources");

  private static Path allRecipesJson = basePath.resolve("allRecipes.json");
  private static Path oneRecipeJson = basePath.resolve("oneRecipe.json");

  private static Path allRecipesResponse = basePath.resolve("allRecipesResponse.json");
  private static Path oneRecipeResponse = basePath.resolve("oneRecipeResponse.json");
  private static Path notFoundResponse = basePath.resolve("notFoundResponse.json");

  private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
  
  public static List<RecipeDto> getAllRecipes() throws JsonParseException,
                                        JsonMappingException, IOException {
    return JSON_MAPPER.readValue(allRecipesJson.toFile(),
                                new TypeReference<List<RecipeDto>>() {});
  }

  public static RecipeDto getOneRecipe() throws JsonParseException,
                                        JsonMappingException, IOException {
    return JSON_MAPPER.readValue(oneRecipeJson.toFile(), RecipeDto.class);
  }

  public static String getAllRecipesResponseAsString() throws IOException {
    return new String(Files.readAllBytes(allRecipesResponse));
  }

  public static String getOneRecipeResponseAsString() throws IOException {
    return new String(Files.readAllBytes(oneRecipeResponse));
  }

  public static String getNotFoundResponseAsString() throws IOException {
    return new String(Files.readAllBytes(notFoundResponse));
  }
}