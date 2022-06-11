package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.goksi.pterogui.data.Data;
import tech.goksi.pterogui.frames.FirstTimeFrame;
import tech.goksi.pterogui.frames.GenericFrame;

import javax.swing.*;

public class FirstTime {
    private Data data;
    private static FirstTime instance;
    private Logger logger;
    private PteroClient api;

    public void init(){
        logger = LoggerFactory.getLogger(this.getClass().getName());
        instance = this;
        data = new Data();
        data.saveDefaultJson();
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            //also will never happen
            e.printStackTrace();
        }
        /*firs time panel */
        if(data.getApiKey().equals("Your api token here") || data.getAppUrl().equals("Your pterodactyl panel url here")){
            FirstTimeFrame ft = new FirstTimeFrame();
            GenericFrame firstTimeFrame = new GenericFrame("PteroGUI | First time setup", ft, null);
            firstTimeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            firstTimeFrame.setVisible(true);
            synchronized (ft.getButton1()){
                try{
                    ft.getButton1().wait();
                }catch (InterruptedException e){
                    logger.error("Error while waiting for button click", e);
                }
            }
            data.save(ft.getAppUrlBox().getText(), new String(ft.getApiKeyBox().getPassword()));
            firstTimeFrame.dispose();
            /*finish of first time panel*/
        }
        api = PteroBuilder.createClient(data.getAppUrl(), data.getApiKey());

    }

    public static FirstTime getInstance() {
        return instance;
    }

    public PteroClient getApi() {
        return api;
    }



}
