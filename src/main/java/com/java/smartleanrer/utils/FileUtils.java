package com.java.smartleanrer.utils;

import java.io.*;

public class FileUtils {

    public static String readFile(String file) {
        return readFile(new File(file));
    }

    public static String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeFile(String file, String content) {
        writeFile(new File(file), content);
    }

    public static void writeFile(File file, String content) {
        try {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(content);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
