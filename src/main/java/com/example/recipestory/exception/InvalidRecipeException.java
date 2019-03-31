package com.example.recipestory.exception;

/**
 * レシピサービスがinvalidレシピを取得しようとしてるexception.
 */
public class InvalidRecipeException extends RuntimeException {

  private static final long serialVersionUID = -769737726528436633L;

  /**
   * 基本なException constructor.
   */
  public InvalidRecipeException() {
    super();
  }

  /**
   * 指定したメッセージを入れます.
   * @param msg エラーメッセージ
   */
  public InvalidRecipeException(String msg) {
    super(msg);
  }

}
