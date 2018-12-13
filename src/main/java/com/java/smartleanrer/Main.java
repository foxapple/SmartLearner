package com.java.smartleanrer;

import com.google.gson.Gson;
import com.java.smartleanrer.database.ArticleData;
import com.java.smartleanrer.database.Vocabulary;

public class Main {

    public static void main(String[] args) {
        Vocabulary word = new Vocabulary();
        word.setContent("apple");
        ArticleData text = new ArticleData();
        text.setContent("this is a test text");
        System.out.println(new Gson().toJson(text));
    }
}
