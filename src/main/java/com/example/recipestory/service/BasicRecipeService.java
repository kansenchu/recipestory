package com.example.recipestory.service;

import com.example.recipestory.dataaccessobj.RecipeDao;
import com.example.recipestory.datatransferobj.ErrorResponse;
import com.example.recipestory.datatransferobj.RecipeDto;
import com.example.recipestory.exception.InvalidRecipeException;
import com.example.recipestory.exception.RecipeNotFoundException;
import com.example.recipestory.repository.RecipeRepo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * レシピをデータベースから引き出す・入れ込むクラス.
 * 裏にJDBCドライバーが働いてる。
 */
@Service
@RequiredArgsConstructor
public class BasicRecipeService implements RecipeService {

  final RecipeRepo repository;

  ModelMapper mapper = new ModelMapper();

  /**
   * 指定したIDのレシピを返す.
   * @param id 欲しいレシピ
   * @return 指定したレシピ
   * @throws RecipeNotFoundException レシピ見つかれない時
   */
  public RecipeDto getRecipe(int id) throws RecipeNotFoundException {
    return mapToRecipeDto(repository.findById(id).orElseThrow(RecipeNotFoundException::new));
  }

  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes() {
    return repository.findAll(Sort.by("id").ascending())
        .parallelStream()
        .peek(System.out::println)
        .map(this::mapToRecipeDto)
        .collect(Collectors.toList());
  }

  /**
   * レシピをDBに加える.
   * @param recipeDto 加えるレシピの情報
   * @return 実際にDBに存在してる新しいレシピ
   * @throws InvalidRecipeException レシピがあっていない時
   */
  public RecipeDto addRecipe(@Valid RecipeDto recipeDto) {
    try {
      RecipeDao toSave = mapToRecipeDao(recipeDto);
      System.out.println(toSave);
      RecipeDao recipeDao = repository.save(mapToRecipeDao(recipeDto));
      return mapToRecipeDto(repository.findById(recipeDao.getId())
          .orElseThrow(RecipeNotFoundException::new));
    } catch (ConstraintViolationException ex) {
      throw new InvalidRecipeException();
    }
  }

  /**
   * 現在存在してるレシピを更新.
   * @param id 変えたいレシピ
   * @param recipeDto 新しい詳細
   * @return 更新されたレシピ
   * @throws InvalidRecipeException レシピが見つからない時
   */
  public RecipeDto editRecipe(int id, RecipeDto recipeDto) throws InvalidRecipeException {
    return repository.findById(id).map((Function<RecipeDao, RecipeDto>) oldRecipe -> {
      if (recipeDto.getTitle() != null) {
        oldRecipe.setTitle(recipeDto.getTitle());
      }
      if (recipeDto.getMakingTime() != null) {
        oldRecipe.setMakingTime(recipeDto.getMakingTime());
      }
      if (recipeDto.getServes() != null) {
        oldRecipe.setServes(recipeDto.getServes());
      }
      if (recipeDto.getIngredients() != null) {
        oldRecipe.setIngredients(recipeDto.getIngredients());
      }
      if (recipeDto.getCost() != null) {
        oldRecipe.setCost(Integer.parseInt(recipeDto.getCost()));
      }
      return mapToRecipeDto(repository.save(oldRecipe));
    }).orElseThrow(() -> new InvalidRecipeException(ErrorResponse.Message.NotFound.getMessage()));
  }

  /**
   * レシピを削除する.
   * @param id 削除したいレシピID
   * @return 削除したレシピ
   * @throws RecipeNotFoundException レシピが見つからない時
   */
  public RecipeDto removeRecipe(int id) {
    return repository.findById(id)
        .map((Function<RecipeDao, RecipeDto>) recipe -> {
          repository.delete(recipe);
          return mapToRecipeDto(recipe);
        }).orElseThrow(RecipeNotFoundException::new);
  }

  /**
   * DAOからREST APIを投げるオブジェクトに変換.
   * @param recipeDao 変換したいDAO
   * @return 平等のAPIモデルオブジェクト
   */
  private RecipeDto mapToRecipeDto(RecipeDao recipeDao) {
    return mapper.map(recipeDao, RecipeDto.class);
  }

  /**
   * REST APIを投げるオブジェクに変換.
   * @param recipeDto 変換したいレシピ
   * @return 平等のDAOオブジェクト
   */
  private RecipeDao mapToRecipeDao(RecipeDto recipeDto) {
    return mapper.map(recipeDto, RecipeDao.class);
  }

}