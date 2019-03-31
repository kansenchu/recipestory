package com.example.recipestory.datatransferobj;

import com.example.recipestory.datatransferobj.views.ResponseViews;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * エラーのレスポンス用POJOクラス.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  public enum Message {
    NotFound("No Recipe found"),
    CreationFailed("Recipe creation failed!");

    private String message;
    
    private Message(String message) {
      this.message = message;
    }

    @JsonValue
    public String getMessage() {
      return message;
    }
  }

  /** リクエストに返信メッセージ. */
  @JsonView(ResponseViews.MessageOnly.class)
  final Message message;

  /** 欠けてるパラメータのString. */
  String required;

}
