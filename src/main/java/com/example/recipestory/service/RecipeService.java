package com.example.recipestory.service;

import com.example.recipestory.datatransferobj.RecipeDto;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * レシピをデータベースから引き出す・入れ込むクラス.
 * 裏にJDBCドライバーが働いてる。
 */
@Service
public class RecipeService {
  /**
   * 全部のレシピを探し出す.
   * @return レシピのリスト
   */
  public List<RecipeDto> getAllRecipes() {
    return Arrays.asList(new RecipeDto());
  }

  public RecipeDto getRecipe(int id) {
    return new RecipeDto();
  }

}