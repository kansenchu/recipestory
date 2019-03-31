package com.example.recipestory.controller;

import com.example.recipestory.datatransferobj.ErrorResponse;
import com.example.recipestory.datatransferobj.MultiRecipeResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.datatransferobj.SuccessResponse;
import com.example.recipestory.datatransferobj.views.ResponseViews;
import com.example.recipestory.exception.InvalidRecipeException;
import com.example.recipestory.exception.RecipeNotFoundException;
import com.example.recipestory.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * レシピの各リクエストを受付するクラス.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class BasicRecipeController implements RecipeController {

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
   * レシピを加えるメソッド.
   * @param newRecipe 新しいレシピの詳細
   * @return 新しいレシピを含めてるレスポンス
   */
  @PostMapping
  public SuccessResponse addRecipe(@RequestBody RecipeDto newRecipe) {
    ArrayList<String> missing = new ArrayList<String>();
    if (newRecipe.getTitle() == null) {
      missing.add("title");
    }
    if (newRecipe.getMakingTime() == null) {
      missing.add("making_time");
    }
    if (newRecipe.getIngredients() == null) {
      missing.add("ingredients");
    }
    if (newRecipe.getServes() == null) {
      missing.add("serves");
    }
    if (newRecipe.getCost() == null) {
      missing.add("cost");
    }
    if (!missing.isEmpty()) {
      throw new InvalidRecipeException(String.join(",", missing));
    }
        
    RecipeDto actualRecipe = recipeService.addRecipe(newRecipe);
    return new SuccessResponse(SuccessResponse.Message.CreationSuccess, actualRecipe);
  }

  /**
   * @{inheritDoc}
   */
  @JsonView(ResponseViews.MessageWithRecipe.class)
  @PatchMapping(value = "/{id}")
  public SuccessResponse editRecipe(@PathVariable int id, @RequestBody RecipeDto recipe) {
    RecipeDto newRecipe = recipeService.editRecipe(id, recipe);
    return new SuccessResponse(SuccessResponse.Message.UpdateSuccess, newRecipe);
  }

  /**
   * レシピを削除するメソッド.
   * @param id 削除するレシピID
   * @return 削除成功レスポンス
   */
  @JsonView(ResponseViews.MessageOnly.class)
  @DeleteMapping(value = "/{id}")
  public SuccessResponse removeRecipe(@PathVariable int id) {
    recipeService.removeRecipe(id);
    return new SuccessResponse(SuccessResponse.Message.DeletionSuccess);
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
    return new ErrorResponse(ErrorResponse.Message.NotFound);
  }

  @ExceptionHandler(InvalidRecipeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse invalidRecipeHandler(InvalidRecipeException ex) {
    return new ErrorResponse(ErrorResponse.Message.CreationFailed, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse invalidRecipeHandler(Exception ex) {
    System.out.println(ex.getCause().getMessage());
    return new ErrorResponse(ErrorResponse.Message.CreationFailed, ex.getMessage());
  }
}