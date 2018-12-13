package com.java.smartleanrer.database.download;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import cn.edu.hfut.dmic.contentextractor.News;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.java.smartleanrer.database.ArticleData;
import com.java.smartleanrer.database.Vocabulary;
import com.java.smartleanrer.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContentResolver {
    public static final String SAVE_PATH = "E:/workspace/java/SmartLearner/java_json/article";
    public static final String SAVE_PATH_TEST = "E:/workspace/java/SmartLearner/java_json/test";
    public static final int PAGE_SIZE = 10;


    public static void main(String arg[]) {
        System.out.print(NewsLoader.getAllArticlesFromFile().size());
        downloadArticle();

        System.out.print(getContent("https://www.abc.net.au/radionational/programs/philosopherszone/reparation/10590792").toString());
    }

    public static void downloadArticle() {
        List<String> urlList = NewsLoader.getAllArticles();
        int pageIndex = 0;
        int listIndex = 0;
        int total = 0;
        ArrayList<ArticleData> dataList = new ArrayList<>();
        for (String url : urlList) {
            System.out.printf("Current status: page: %s, list: %s, total: %s", pageIndex + "", listIndex + "", (total++) + "");
            System.out.println();
            ArticleData data = getContent(url);
            if (data != null) {
                System.out.println("Data get Finished!");
                dataList.add(data);
                listIndex++;
                if (listIndex >= PAGE_SIZE) {
                    saveList(dataList, pageIndex);
                    listIndex = 0;
                    pageIndex++;
                }
            }
        }
        if (dataList.size() > 0) {
            saveList(dataList, pageIndex);
        }
    }

    public static void saveList(List<ArticleData> list, int page) {
        String filename = String.format("%05d", page) + ".txt";
        File saveFile = new File(SAVE_PATH, filename);
        FileUtils.writeFile(saveFile, new Gson().toJson(list));
        list.clear();
    }

    public static ArticleData getContent(String url) {
        ArticleData data = new ArticleData();
        try {
            News news = ContentExtractor.getNewsByUrl(url);
            data.setTitle(news.getTitle());
            data.setContent(news.getContent());
            data.setUrl(news.getUrl());
            data.setReadTimes(0);
            try {
                data.setGetDate(news.getTime());
            } catch (Exception e) {
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ArticleData> getBasicArticleSet() {
        List<ArticleData> dataSet = new ArrayList<>();
        File root = new File(SAVE_PATH_TEST);
        for (File item : root.listFiles()) {
            String json = FileUtils.readFile(item);
            ArrayList<ArticleData> list = new Gson().fromJson(json, new TypeToken<ArrayList<ArticleData>>() {
            }.getType());
            dataSet.addAll(list);
        }
        for(ArticleData item: dataSet){
            item.setHardWord(new ArrayList<Vocabulary>());
            item.setLearningWord(new ArrayList<Vocabulary>());
        }
        return dataSet;
    }

}
