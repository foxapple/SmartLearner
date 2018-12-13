package com.java.smartleanrer.database.download.network;

import com.google.gson.Gson;
import com.java.smartleanrer.database.download.entity.NewsDataBean;
import com.java.smartleanrer.database.download.entity.SourceList;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.TextUtils;

import java.io.IOException;

public class NewsDownloader {

    static final String NEWS_URI = "https://newsapi.org/v2/";
    static final String SOURCE_URI = "https://newsapi.org/v2/sources?language=en&apiKey=aa19ddf66887417091f3ce2802a08f18";
    static final String APIKEY = "&apiKey=aa19ddf66887417091f3ce2802a08f18";

    public static void main(String arg[]) {
        NewsDataBean dataBean = getNews("bbc-news");
        System.out.println(dataBean.getArticles().size());
    }

    public static SourceList getSource() {
        String json = GetNews(SOURCE_URI);
        System.out.println(json);
        return new Gson().fromJson(json, SourceList.class);
    }

    public static NewsDataBean getNews(String sources) {
        return getNews(sources, "", "");
    }


    //From, to : (e.g. 2018-12-12 or 2018-12-12T17:28:01)
    public static NewsDataBean getNews(String sources, String from, String to) {
        String basicUrl;
        if (TextUtils.isEmpty(from)) {
            basicUrl = NEWS_URI + "everything?sources=" + sources + "&pageSize=100";
        } else {
            basicUrl = NEWS_URI + "everything?sources=" + sources + "&from=" + from + "&to=" + to + "&pageSize=100";
        }
        String json = GetNews(basicUrl + APIKEY);
        System.out.println(json);
        NewsDataBean sizeBean = new Gson().fromJson(json, NewsDataBean.class);
        int totalResults = sizeBean.getTotalResults();
        for (int page = 2; page < totalResults / 100; page++) {
            String pageUrl = "&page=" + page;
            json = GetNews(basicUrl + pageUrl + APIKEY);
            System.out.println(json);
            NewsDataBean temp = new Gson().fromJson(json, NewsDataBean.class);
            sizeBean.getArticles().addAll(temp.getArticles());
        }
        return sizeBean;
    }

    public static String GetNews(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Call call = okHttpClient.newCall(request);
        Response response = null;

        try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
