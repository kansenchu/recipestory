package com.example.recipestory.datatransferobj;

import com.example.recipestory.datatransferobj.views.RecipeViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * REST API でやりとりする時使うレシピクラス.
 * ポイントとして、値段がStringになります。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("recipe")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto {
  @JsonView(RecipeViews.IncludeId.class)
  private int id;

  /** レシピの名前. */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String title;
  
  /** レシピの作り時間。実際JSONではmaking_timeになります. */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String makingTime;
  
  /** レシピに対応する人数. */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String serves; 
  
  /** 材料リスト。Listではなく、String扱いとしています. */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String ingredients; 

  /** レシピの予測値段。intではなく, Stringです. */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String cost; 
  
}
