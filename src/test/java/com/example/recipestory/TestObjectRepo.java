package com.example.recipestory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.recipestory.datatransferobj.RecipeDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestObjectRepo {

    @Value("${json.config.allRecipesJson}")
    static File allRecipesJson;

    // public enum TestObject {
    // ALL_RECIPES(allRecipesJson, List.class, RecipeDto.class);

    // public Object data;

    // private TestObject(String content, Class... objType) {
    // ObjectMapper mapper = new ObjectMapper();
    // try {
    // data = mapper.readValue(content,
    // mapper.getTypeFactory().constructCollectionType(objType[0], objType[1]));
    // } catch (JsonParseException e) {
    // e.printStackTrace();
    // } catch (JsonMappingException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private static List<RecipeDto> allRecipes;

    public static List<RecipeDto> getAllRecipes() throws JsonParseException, JsonMappingException, IOException {
        if (allRecipes == null) {
            allRecipes = JSON_MAPPER.readValue(allRecipesJson, new TypeReference<List<RecipeDto>>() {
            });
        }
        return allRecipes;
    }
}