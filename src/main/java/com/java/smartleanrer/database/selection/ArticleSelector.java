package com.java.smartleanrer.database.selection;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.java.smartleanrer.database.ArticleData;
import com.java.smartleanrer.database.Vocabulary;
import com.java.smartleanrer.database.download.ContentResolver;
import com.java.smartleanrer.utils.FileUtils;

import java.util.*;

public class ArticleSelector {
    public static final String SAVE_PATH = "E:/workspace/java/SmartLearner/java_json/selected/";
    public static int ShortestWord = 30;
    public static int AllowedHardWord = 100;
    public static List<ArticleData> mSelectedList = new ArrayList<>();

    public static void main(String arg[]) {

        String json = FileUtils.readFile(SAVE_PATH + "seleted_news.txt");
        List<ArticleData> data = new Gson().fromJson(json, new TypeToken<ArrayList<ArticleData>>() {
        }.getType());
        for (int i = 0; i < 100; i++) {
            ArticleData selected = selectArticleByWord(data, VocabularyDao.getInstance().getmCurrentWordList());
            VocabularyDao.getInstance().addLearnWord(selected.getLearningWord());
            System.out.println(selected.getContent());
        }
    }

    public static void setWordListForArticle() {
        List<ArticleData> list = ContentResolver.getBasicArticleSet();
        List<ArticleData> selected = new ArrayList<>();
        int id = 0;
        int process = 0;
        for (ArticleData item : list) {
            ArticleData data = selectArticle(item);
            if (data.getHardWord().size() <= AllowedHardWord && data.getLearningWord().size() > 0) {
                data.setId(id);
                selected.add(data);
                System.out.printf("Article is selceted! title: %s id: %s process: %s", data.getTitle(), id + "", process + "");
                System.out.println();
                id++;
            } else {
                System.out.println("Article is not suitable for use, process: " + process);
            }
            process = process + 1;
        }
        String json = new Gson().toJson(selected);
        FileUtils.writeFile(SAVE_PATH + "seleted_news.txt", json);
    }


    public static ArticleData selectArticleByWord(List<ArticleData> articleList, Map<String, Vocabulary> learningWords) {
        int maxScore = -1000000;
        int bestRepeat = 0;
        int bestHard = 0;
        ArticleData goodArticle = null;
        for (ArticleData article : articleList) {
            if (article.getReadTimes() == 0) {
                int score = 0;
                int repeat = 0;
                for (Vocabulary word : article.getLearningWord()) {
                    if (learningWords.containsKey(word.getContent())) {
                        score++;
                        repeat++;
                    }
                }
                score -= article.getHardWord().size() / 2;
                if (score > maxScore) {
                    maxScore = score;
                    goodArticle = article;
                    bestHard = article.getHardWord().size();
                    bestRepeat = repeat;
                }
            }
        }
        goodArticle.addReadTimes(1);
        System.out.println("Repeat words: " + bestRepeat + " HardWord List: " + bestHard);
        return goodArticle;
    }


    public static ArticleData selectArticle(ArticleData paper) {
        if (paper.getContent().length() < 100) {
            return paper;
        }
        WordParser.ParseResult result = WordParser.lemmatization(paper.getContent());
        System.out.println("WordParser is done, news: " + paper.getContent());
        if (result.lemma.size() < ShortestWord) {
            return paper;
        }
        Set<String> allword = new HashSet<>();
        Set<String> learnWord = new HashSet<>();
        Set<String> hardWord = new HashSet<>();
        for (int i = 0; i < result.lemma.size(); i++) {
            String tag = result.ner.get(i);
            String word = result.lemma.get(i);
            allword.add(word);
            switch (VocabularyDao.getInstance().classifyWord(word)) {
                case HARD:
                    if (tag.charAt(0) == 'V') {
                        hardWord.add(word);
                    }
                    break;
                case LEARN:
                    learnWord.add(word);
                    break;
            }
        }
        ArrayList<Vocabulary> hardList = new ArrayList<>();
        for (String str : hardWord) {
            hardList.add(VocabularyDao.getInstance().getWord(str));
        }
        ArrayList<Vocabulary> learnList = new ArrayList<>();
        for (String str : learnWord) {
            learnList.add(VocabularyDao.getInstance().getWord(str));
        }
        System.out.printf("hardList %s, learnList %s", hardList.size(), learnList.size());
        System.out.println();
        paper.setHardWord(hardList);
        paper.setLearningWord(learnList);
        return paper;
    }

}
