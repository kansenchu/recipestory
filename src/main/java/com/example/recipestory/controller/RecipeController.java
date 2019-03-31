package com.example.recipestory.controller;

import com.example.recipestory.datatransferobj.MultiRecipeResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.datatransferobj.SuccessResponse;

public interface RecipeController {
  /**
   * 全レシピを取得する.
   * 
   * @return 全部のレシピが含めているレスポンス
   */
  public MultiRecipeResponse getRecipes();

  /**
   * 定義したレシピを取得する.
   * @param id 取得したいレシピID
   * @return 一個のレシピのレスポンス
   */
  public SuccessResponse getRecipe(int id);

  /**
   * レシピを加えるメソッド.
   * @param newRecipe 新しいレシピの詳細
   * @return 新しいレシピを含めてるレスポンス
   */
  public SuccessResponse addRecipe(RecipeDto newRecipe);

  /**
   * レシピを変更するメソッド.
   * 指定していないフィルドは古いものを使うままにします。
   * @param id 変えたいレシピのid
   * @param recipe 変えたいもの詳細
   * @return 変更したレシピ詳細
   */
  public SuccessResponse editRecipe(int id, RecipeDto recipe);

  /**
   * レシピを削除するメソッド.
   * @param id 削除するレシピID
   * @return 削除成功レスポンス
   */
  public SuccessResponse removeRecipe(int id);
}