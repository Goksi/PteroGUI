package tech.goksi.pterogui.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

public class Data {
    private File file;
    private JsonObject jsonObject;
    private Logger logger;

    public Data(){
        file = new File("data.json");
        logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    public void saveDefaultJson(){
        if(!file.exists()){
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("app_url", "Your pterodactyl panel url here");
                jsonObject.addProperty("token", "Your api token here");
                if(file.createNewFile()){
                    try(OutputStream out = Files.newOutputStream(file.toPath())){
                        out.write(jsonObject.toString().getBytes());
                    }
                }
            } catch (IOException e) {
                logger.error("Error while creating data file!", e);
            }
        }
        try {
            jsonObject = (JsonObject) JsonParser.parseReader(new FileReader("data.json"));
        } catch (FileNotFoundException e) {
            //basically will never happen but ok
            e.printStackTrace();
        }
    }
    public String getAppUrl(){
        return jsonObject.get("app_url").getAsString();
    }
    public String getApiKey(){
        return jsonObject.get("token").getAsString();

    }
    public void save(String appUrl, String token){
        jsonObject.addProperty("app_url", appUrl);
        jsonObject.addProperty("token", token);
        try {
            Writer writer = new FileWriter("data.json");
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Error while saving data file", e);
        }
    }

}
