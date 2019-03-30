package com.example.recipestory.datatransferobj.views;

/**
 * レシピのRESTやるとりするViewを指定するクラス。 実際に管理するロジックは、Jacksonのライブラリにあります。
 * Recipeクラスの中のAnnotationによってViewの項目が決める。
 * 
 * @see <a href=
 *      "https://www.techscore.com/blog/2016/12/18/java-jackson-serialize-deserialize/">
 *      https://www.techscore.com/blog/2016/12/18/java-jackson-serialize-deserialize/</a>
 *
 */
public class RecipeViews {
    public static interface ExcludeId {
    }

    public static interface IncludeId extends ExcludeId {
    }
}
