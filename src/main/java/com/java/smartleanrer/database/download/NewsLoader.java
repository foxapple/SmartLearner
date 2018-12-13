package com.java.smartleanrer.database.download;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.java.smartleanrer.database.download.entity.Articles;
import com.java.smartleanrer.database.download.entity.NewsDataBean;
import com.java.smartleanrer.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader {
    public static final String PATH = "E:/workspace/java/SmartLearner/python_json/";
    public static final String SAVE_PATH = "E:/workspace/java/SmartLearner/java_json/";
    public static final String SOURCE = "sources.txt";

    public static void main(String arg[]) {
        System.out.print(String.format("%05d", 99));
    }

    public static List<String> getAllArticles() {
        File root = new File(PATH);
        List<String> allUrl = new ArrayList<>();
        for (File item : root.listFiles()) {
            String json = FileUtils.readFile(item);
            NewsDataBean data = new Gson().fromJson(json, NewsDataBean.class);
            List<Articles> datalist = data.getArticles();
            for (Articles paper : datalist) {
                allUrl.add(paper.getUrl());
            }
            System.out.println(item.getName() + " is done!");
        }
        File saveUrl = new File(SAVE_PATH, "all_url.txt");
        FileUtils.writeFile(saveUrl, new Gson().toJson(allUrl));
        return allUrl;
    }

    public static List<String> getAllArticlesFromFile() {
        File file = new File(SAVE_PATH + "all_url.txt");
        String json = FileUtils.readFile(file);
        return (List<String>) new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
    }
}
