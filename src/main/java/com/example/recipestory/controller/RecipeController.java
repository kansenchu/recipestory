package com.example.recipestory.controller;

import com.example.recipestory.datatransferobj.ErrorResponse;
import com.example.recipestory.datatransferobj.MultiRecipeResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.datatransferobj.SuccessResponse;
import com.example.recipestory.datatransferobj.views.ResponseViews;
import com.example.recipestory.exception.RecipeNotFoundException;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * レシピの各リクエストを受付するクラス.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

  final RecipeService recipeService;
  
  /**
   * 全レシピを取得する.
   * 
   * @return 全部のレシピが含めているレスポンス
   */
  @GetMapping
  public MultiRecipeResponse getRecipes() {
    List<RecipeDto> recipeList = recipeService.getAllRecipes();
    return new MultiRecipeResponse(recipeList);
  }

  /**
   * 定義したレシピを取得する.
   * @param id 取得したいレシピID
   * @return 一個のレシピのレスポンス
   */
  @JsonView(ResponseViews.MessageWithRecipe.class)
  @GetMapping(value = "/{id}")
  public SuccessResponse getRecipe(@PathVariable int id) {
    return new SuccessResponse(SuccessResponse.Message.RetrievalSuccess,
        recipeService.getRecipe(id));
  } 

  /**
   * レシピが見つかれない時対応するハンドラー.
   * @param ex もらったエクセプション
   * @return 決定のエラーメッセージ
   */
  @JsonView(ResponseViews.MessageOnly.class)
  @ExceptionHandler(RecipeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex) {
    return new ErrorResponse(ErrorResponse.Message.NOT_FOUND);
  }
}