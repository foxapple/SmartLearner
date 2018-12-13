package com.java.smartleanrer.database.selection;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.java.smartleanrer.database.Vocabulary;
import com.java.smartleanrer.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.java.smartleanrer.utils.Constant.*;

public class VocabularyDao {
    public static final int MAX_LEARN_COUNT = 100;
    public static final int LEARN_TIMES = 5;
    private static Map<String, Vocabulary> mSimpleWordList;
    private static Map<String, Vocabulary> mLearnWordList;
    private static Map<String, Vocabulary> mCurrentWordList;
    private static Map<String, Vocabulary> mFinishWordList;
    private static Map<String, Vocabulary> mPendingWordList;
    private static VocabularyDao mDao;

    private VocabularyDao() {
        loadAll();
    }

    public static VocabularyDao getInstance() {
        if (mDao == null) {
            mDao = new VocabularyDao();
        }
        return mDao;
    }

    public static void saveAll() {
        saveList(mSimpleWordList, DICT_PATH + SIMPLE_LIST);
        saveList(mLearnWordList, DICT_PATH + LEARNING_LIST);
        saveList(mCurrentWordList, DICT_PATH + CURRENT_LIST);
        saveList(mFinishWordList, DICT_PATH + FINISH_LIST);
        saveList(mPendingWordList, DICT_PATH + PENDING_LIST);
    }

    private static void saveList(Map<String, Vocabulary> list, String name) {
        if (list == null) {
            return;
        }
        File file = new File(name);
        String json = new Gson().toJson(list);
        FileUtils.writeFile(file, json);
    }

    private static void loadList(Map<String, Vocabulary> list, String name) {
        File file = new File(name);
        if (!file.exists()) {
            FileUtils.writeFile(file, "{}");
        }
        String json = FileUtils.readFile(file);
        Map<String, Vocabulary> data = new Gson().fromJson(json, new TypeToken<HashMap<String, Vocabulary>>() {
        }.getType());
        if (list != null && data != null) {
            list.clear();
            list.putAll(data);
        }
    }

    public void loadAll() {
        mSimpleWordList = new HashMap<>();
        mLearnWordList = new HashMap<>();
        mCurrentWordList = new HashMap<>();
        mFinishWordList = new HashMap<>();
        mPendingWordList = new HashMap<>();
        loadList(mSimpleWordList, DICT_PATH + SIMPLE_LIST);
        loadList(mLearnWordList, DICT_PATH + LEARNING_LIST);
        loadList(mCurrentWordList, DICT_PATH + CURRENT_LIST);
        loadList(mFinishWordList, DICT_PATH + FINISH_LIST);
        loadList(mPendingWordList, DICT_PATH + PENDING_LIST);
    }

    public Map<String, Vocabulary> getmCurrentWordList() {
        return mCurrentWordList;
    }

    public void addLearnWord(List<Vocabulary> list) {
        int size = mCurrentWordList.size();
        for (Vocabulary word : list) {
            String content = word.getContent();
            if (mLearnWordList.containsKey(content)) {
                if (mCurrentWordList.containsKey(content)) {
                    if (mCurrentWordList.get(content).getReadTime() > LEARN_TIMES) {
                        mFinishWordList.put(content, mCurrentWordList.get(content));
                        mCurrentWordList.remove(content);
                    } else {
                        mCurrentWordList.get(content).addReadTime(1);
                    }
                } else if (size < MAX_LEARN_COUNT) {
                    mCurrentWordList.put(word.getContent(), word);
                    word.addReadTime(1);
                    size++;
                }
            }
        }
        saveAll();
    }

    public Level classifyWord(String word) {
        if (mLearnWordList.containsKey(word)) {
            return Level.LEARN;
        } else if (mSimpleWordList.containsKey(word) || word.length() < 5) {
            return Level.SIMPLE;
        } else {
            return Level.HARD;
        }
    }

    public Vocabulary getWord(String word) {
        if (mLearnWordList.containsKey(word)) {
            return mLearnWordList.get(word);
        } else {
            Vocabulary ret = new Vocabulary();
            ret.setContent(word);
            return ret;
        }
    }

    public enum Level {
        SIMPLE, LEARN, HARD
    }
}
