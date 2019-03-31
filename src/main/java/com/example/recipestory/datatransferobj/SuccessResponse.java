package com.example.recipestory.datatransferobj;

import com.example.recipestory.datatransferobj.views.ResponseViews;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 全レシピ返す用POJOクラス.
 */
@Data
@RequiredArgsConstructor
public class SuccessResponse {
  /**
   * 可能なメッセージを代表するenum.
   */
  public enum Message {
    RetrievalSuccess("Recipe details by id"),
    CreationSuccess("Recipe successfully created!"),
    DeletionSuccess("Recipe successfully removed!"),
    UpdateSuccess("Recipe successfully updated!");


    private String message;
    
    private Message(String message) {
      this.message = message;
    }

    @JsonValue
    public String getMessage() {
      return message;
    }
  }
  
  @JsonView(ResponseViews.MessageOnly.class)
  final Message message;
  
  @JsonView(ResponseViews.MessageWithRecipe.class)
  List<RecipeDto> recipe;

  /**
   * 一個のレシピを受けて直接リストに入れるコンスラクタ.
   * @param message 送りたいメッセージ
   * @param recipe 入れたいレシピ
   */
  public SuccessResponse(Message message, RecipeDto recipe) {
    this.message = message;
    this.recipe = Arrays.asList(recipe);
  }
}
