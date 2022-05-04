package tech.goksi.pterogui.apps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.goksi.pterogui.frames.MainFrame;

import javax.swing.*;
import java.util.Objects;

public class MainF {
    private Logger logger;
    public MainF(){
        logger  = LoggerFactory.getLogger(this.getClass().getName());
    }

    public void init(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            //as you already know
            e.printStackTrace();
        }
        MainFrame mf = new MainFrame();
        FirstTime.getInstance().getApi().retrieveAccount().executeAsync(acc ->{
            mf.getNameLbl().setText("Welcome %name !".replaceAll("%name", acc.getUserName()));
        });
        JFrame mfFrame = new JFrame("PteroGUI | Main");
        mfFrame.setResizable(false);
        mfFrame.setContentPane(mf);
        mfFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        mfFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mfFrame.pack();
        mfFrame.setVisible(true);
    }
}
