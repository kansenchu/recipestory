package com.example.recipestory.service;

import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.exception.InvalidRecipeException;
import com.example.recipestory.exception.RecipeNotFoundException;

import java.util.List;

public interface RecipeService {
  /**
   * 指定したIDのレシピを返す.
   * @param id 欲しいレシピ
   * @return 指定したレシピ
   * @throws RecipeNotFoundException レシピ見つかれない時
   */
  public RecipeDto getRecipe(int id) throws RecipeNotFoundException;

  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes();

  /**
   * レシピをDBに加える.
   * @param recipeDto 加えるレシピの情報
   * @return 実際にDBに存在してる新しいレシピ
   * @throws InvalidRecipeException レシピがあっていない時
   */
  public RecipeDto addRecipe(RecipeDto recipeDto);

  /**
   * 現在存在してるレシピを更新.
   * @param id 変えたいレシピ
   * @param recipeDto 新しい詳細
   * @return 更新されたレシピ
   * @throws InvalidRecipeException レシピが見つからない時
   */
  public RecipeDto editRecipe(int id, RecipeDto recipeDto) throws InvalidRecipeException;

  /**
   * レシピを削除する.
   * @param id 削除したいレシピID
   * @return 削除したレシピ
   * @throws RecipeNotFoundException レシピが見つからない時
   */
  public RecipeDto removeRecipe(int id);
}