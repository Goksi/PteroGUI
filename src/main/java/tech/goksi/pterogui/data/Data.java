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
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("data.json");
            assert is != null;
            try{
                try(OutputStream out = Files.newOutputStream(file.toPath())){
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = is.read(buffer)) > 0){
                        out.write(buffer, 0, len);
                    }
                }


            }catch (IOException e){
                logger.error("Error while writing data file!", e);
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
