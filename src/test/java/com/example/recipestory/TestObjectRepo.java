package com.example.recipestory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.example.recipestory.datatransferobj.RecipeDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestObjectRepo {

    public enum TestObject {
        ALL_RECIPES("allRecipes.json", List.class, RecipeDto.class);

        public Object data;

        private TestObject(String content, Class... objType) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                data = mapper.readValue(content,
                        mapper.getTypeFactory().constructCollectionType(objType[0], objType[1]));
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // private static List<RecipeDto> allRecipes;

    // public static List<RecipeDto> getAllRecipes() throws JsonParseException,
    // JsonMappingException, IOException {
    // if (allRecipes == null) {
    // allRecipes = JSON_MAPPER.readValue("allRecipes.json", new
    // TypeReference<List<RecipeDto>>() {
    // });
    // }
    // return allRecipes;
    // }
}