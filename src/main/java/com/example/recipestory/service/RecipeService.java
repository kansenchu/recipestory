package com.example.recipestory.service;

import java.util.Arrays;
import java.util.List;

import com.example.recipestory.datatransferobj.RecipeDto;

/**
 * レシピをデータベースから引き出す・入れ込むクラス。
 * 
 * 裏にJDBCドライバーが働いてる。
 */
public class RecipeService {

    /**
     * 全部のレシピを探し出す。
     */
    public List<RecipeDto> getAllRecipes() {
        return Arrays.asList(new RecipeDto());
    }

}