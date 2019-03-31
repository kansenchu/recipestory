
package com.example.recipestory.datatransferobj.views;

/**
 * 普段メッセージレスポンスを返す用POJOクラス.
 * @author pikachoo
 *
 */
public class ResponseViews {
  public static interface MessageOnly {}
  
  public static interface MessageWithRecipe extends MessageOnly, RecipeViews.ExcludeId {}
}