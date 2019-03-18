package com.netcracker.edu.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netcracker.edu.fxmodel.Root;
import java.io.*;

public class JsonUtil {
    public static void save(Object object, String fileName) {

        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(object, writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Root load(String path){

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path));) {
            Gson gson = new Gson();
            //Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            Root json = gson.fromJson(bufferedReader, Root.class);
            return json;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
