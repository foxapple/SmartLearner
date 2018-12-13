package com.java.smartleanrer.database.selection;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WordParser {

    private static StanfordCoreNLP mPipeLine;

    private static StanfordCoreNLP getInstance() {
        if (mPipeLine == null) {
            Properties props = new Properties();
            props.put("annotators", "tokenize, ssplit, pos, lemma");
//            props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
//            props.put("ner.useSUTime", "false");
//            props.put("ner.applyNumericClassifiers", "false");
            mPipeLine = new StanfordCoreNLP(props, false);
        }
        return mPipeLine;
    }

    public static void main(String arg[]) {
        System.out.print(lemmatization("Hello. Welcome to 6 Minute English, I'm Neil"));
    }

    public static ParseResult lemmatization(String text) {
        CoreDocument document = new CoreDocument(text);
        getInstance().annotate(document);
        List<CoreLabel> tokens = document.tokens();
        tokens.get(0).lemma();
        tokens.get(0);
        ParseResult result = new ParseResult();
        result.lemma = new ArrayList<>();
        result.ner = new ArrayList<>();
        for(CoreLabel token : tokens){
            result.lemma.add(token.lemma());
            result.ner.add(token.tag());
        }
        return result;
    }

    public static class ParseResult {
        public List<String> lemma;
        public List<String> ner;
    }
}
