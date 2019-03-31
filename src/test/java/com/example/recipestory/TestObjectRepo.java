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

  private static Path objectsPath = basePath.resolve("objects");
  private static Path allRecipesJson = objectsPath.resolve("allRecipes.json");
  private static Path oneRecipeJson = objectsPath.resolve("oneRecipe.json");
  private static Path additionalRecipeJson = objectsPath.resolve("additionalRecipe.json");

  private static Path responsesPath = basePath.resolve("responses");
  private static Path allRecipesResponse = responsesPath.resolve("allRecipesResponse.json");
  private static Path creationFailedResponse = responsesPath.resolve("creationFailedResponse.json");
  private static Path notFoundResponse = responsesPath.resolve("notFoundResponse.json");
  private static Path oneRecipeResponse = responsesPath.resolve("oneRecipeResponse.json");
  private static Path updateSuccessResponse = responsesPath.resolve("updateSuccessResponse.json");
  private static Path creationSuccessResponse = 
        responsesPath.resolve("creationSuccessResponse.json");
  private static Path deletionSuccessResponse = 
        responsesPath.resolve("deletionSuccessResponse.json");

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

  public static RecipeDto getAdditionalRecipe() throws JsonParseException,
                                        JsonMappingException, IOException {
    return JSON_MAPPER.readValue(additionalRecipeJson.toFile(), RecipeDto.class);
  }

  public static String getAllRecipesResponseAsString() throws IOException {
    return new String(Files.readAllBytes(allRecipesResponse));
  }

  public static String getOneRecipeResponseAsString() throws IOException {
    return new String(Files.readAllBytes(oneRecipeResponse));
  }

  public static String getCreationSuccessResponseAsString() throws IOException {
    return new String(Files.readAllBytes(creationSuccessResponse));
  }

  public static String getcreationFailedResponseAsString() throws IOException {
    return new String(Files.readAllBytes(creationFailedResponse));
  }

  public static String getDeletionSuccessResponseAsString() throws IOException {
    return new String(Files.readAllBytes(deletionSuccessResponse));
  }

  public static String getUpdateSuccessResponseAsString() throws IOException {
    return new String(Files.readAllBytes(updateSuccessResponse));
  }

  public static String getNotFoundResponseAsString() throws IOException {
    return new String(Files.readAllBytes(notFoundResponse));
  }
}