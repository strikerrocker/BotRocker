package com.github.strikerrocker.gson;

import com.github.strikerrocker.MainRunner;
import com.github.strikerrocker.utils.BotUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class GsonUtils {

    public static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create();

    public static void handleJsonObject(JsonReader reader) throws IOException {
        reader.beginObject();
        String fieldname = null;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.BEGIN_ARRAY)) {
                handleJsonArray(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                return;
            } else {
                if (token.equals(JsonToken.NAME)) {
                    fieldname = reader.nextName();
                }
                if ("ADMIN_PREFIX".equals(fieldname)) {
                    token = reader.peek();
                    BotUtils.ADMIN_PREFIX = reader.nextString();
                    System.out.println("ADMIN Prefix has been set to " + BotUtils.ADMIN_PREFIX);
                }
                if ("USER_PREFIX".equals(fieldname)) {
                    token = reader.peek();
                    BotUtils.USER_PREFIX = reader.nextString();
                    System.out.println("User Prefix has been set to " + BotUtils.USER_PREFIX);
                }
            }
        }
    }

    public static void handleJsonArray(JsonReader reader) throws IOException {
        reader.beginArray();
        String fieldname = null;
        while (true) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleJsonObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else {
                System.out.print(reader.nextInt() + " ");
            }
        }
    }

    public static void read(File file) {
        try {
            GsonUtils.handleJsonObject(new JsonReader(new StringReader(FileUtils.readFileToString(file, Charset.defaultCharset()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Files.deleteIfExists(MainRunner.FILE_PATH);
            PrintWriter printWriter = new PrintWriter(new FileWriter(MainRunner.FILE_PATH.toFile()));
            printWriter.print(gson.toJson(new Guilds().setADMIN_PREFIX(BotUtils.ADMIN_PREFIX).setUSER_PREFIX(BotUtils.USER_PREFIX)));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}