package com.example.recipestory.datatransferobj;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 全レシピ返す用POJOクラス.
 */
@Data
@AllArgsConstructor
public class MultiRecipeResponse {
  List<RecipeDto> recipes;
}
