package com.example.recipestory.dataaccessobj;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * DBとやりとりする時使うレシピクラス.
 * ポイントとして、cost (値段)がintの基準でしています。
 */
@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDao {
  @Id
  private int id;

  /** レシピの名前. */
  @NotNull
  private String title;

  /** レシピの作り時間. */
  @NotNull
  @Column(name = "making_time")
  private String makingTime;

  /** レシピに対応する人数. */
  @NotNull
  private String serves;

  /** 材料リスト。Listではなく、String扱いとしています. */
  @NotNull
  private String ingredients;

  /** レシピの予測値段. */
  @NotNull
  private int cost;

  /** レシピの作成時間. */
  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  /** レシピの作成時間. */
  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}  