package com.example.recipestory.repository;

import com.example.recipestory.dataaccessobj.RecipeDao;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * レシピをデータベースで管理するクラス.
 * JpaRepositoryを継承しているため、ただでデフォルトのデータベースからレシピ取り出すことができる。
 */
public interface RecipeRepo extends JpaRepository<RecipeDao, Integer> {

}