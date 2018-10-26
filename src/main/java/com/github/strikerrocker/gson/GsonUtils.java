package com.github.strikerrocker.gson;

import com.github.strikerrocker.MainRunner;
import com.github.strikerrocker.commands.CommandCustom;
import com.github.strikerrocker.utils.BotUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Map;

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

    public static void readCommandData(File file) {
        try {
            GsonUtils.handleJsonObject(new JsonReader(new StringReader(FileUtils.readFileToString(file, Charset.defaultCharset()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCustomCommandData(File file) {
        try {
            Type type = new TypeToken<Map<Long, Map<String, Map<String, String>>>>() {
            }.getType();
            Map<Long, Map<String, Map<String, String>>> map = gson.fromJson(new StringReader(FileUtils.readFileToString(file, Charset.defaultCharset())), type);
            MainRunner.INSTANCE.customCommands = map;
            for (Map.Entry<Long, Map<String, Map<String, String>>> entry : map.entrySet()) {
                Long serverID = entry.getKey();
                Map<String, Map<String, String>> nameReplyDesc = entry.getValue();
                for (Map.Entry<String, Map<String, String>> entry1 : nameReplyDesc.entrySet()) {
                    String name = entry1.getKey();
                    Map<String, String> replyDescMap = entry1.getValue();
                    String reply = replyDescMap.keySet().toString().substring(1).replaceAll("]", "");
                    String desc = replyDescMap.values().toString().substring(1).replaceAll("]", "");
                    MainRunner.INSTANCE.commands.put(name, new CommandCustom(name, reply, desc, serverID));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCommandData() {
        try {
            Files.deleteIfExists(MainRunner.DATA_PATH);
            PrintWriter printWriter = new PrintWriter(new FileWriter(MainRunner.DATA_PATH.toFile()));
            printWriter.print(gson.toJson(new Guilds().setAdminPrefix(BotUtils.ADMIN_PREFIX).setUserPrefix(BotUtils.USER_PREFIX)));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCustomCommandData() {
        try {
            Files.deleteIfExists(MainRunner.CUSTOM_DATA_PATH);
            PrintWriter printWriter = new PrintWriter(new FileWriter(MainRunner.CUSTOM_DATA_PATH.toFile()));
            printWriter.print(gson.toJson(MainRunner.INSTANCE.customCommands));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAll() {
        saveCommandData();
        saveCommandData();
    }

    public static void readAll() {
        readCommandData(MainRunner.DATA_PATH.toFile());
        readCustomCommandData(MainRunner.CUSTOM_DATA_PATH.toFile());
    }
}