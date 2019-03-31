package com.example.recipestory.controller;

import java.util.List;

import com.example.recipestory.datatransferobj.MultiRecipeResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.service.RecipeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * レシピの各リクエストを受付するクラス。
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    final RecipeService recipeService;

    /**
     * 全レシピを取得する。
     * 
     * @return 全部のレシピが含めているレスポンス。
     */
    @GetMapping
    public MultiRecipeResponse getRecipes() {
        List<RecipeDto> recipeList = recipeService.getAllRecipes();
        return new MultiRecipeResponse(recipeList);
    }
}