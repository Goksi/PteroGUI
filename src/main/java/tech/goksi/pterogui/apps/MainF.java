package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import tech.goksi.pterogui.frames.MainFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainF {
    private Map<String, ClientServer> servers;
    private JFrame mfFrame;

    public void init(){
        servers = new HashMap<>();
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){
            //as you already know
            e.printStackTrace();
        }
        MainFrame mf = new MainFrame();

        FirstTime.getInstance().getApi().retrieveAccount().executeAsync(acc ->{
            mf.getNameLbl().setText("Welcome %name !".replaceAll("%name", acc.getUserName()));
        }, error ->{
            JOptionPane.showMessageDialog(mf, "Wrong API key or APP url, delete data.json and try again!", "Critical error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        });
        final boolean[] rmFirst = {true};  //just so i can remove first default item
        FirstTime.getInstance().getApi().retrieveServers(true).forEachAsync(srv ->{
            if(rmFirst[0]){
                mf.getServersComboBox().removeItemAt(0);
                rmFirst[0] = false;
            }
            servers.put(srv.getName(), srv);
            if(servers.isEmpty()){
                mf.getEditBtn().setEnabled(false);
                mf.getMassBtn().setEnabled(false);
            }
            mf.getServersComboBox().addItem(srv.getName());
            return true;
        });
        mfFrame = new JFrame("PteroGUI | Main");
        mfFrame.setResizable(false);
        mfFrame.setContentPane(mf);
        mfFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        mfFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mfFrame.pack();
        mfFrame.setLocationRelativeTo(null);
        mfFrame.setVisible(true);
        mf.getEditBtn().addActionListener(e ->{
            ServerSettings ss = new ServerSettings(servers.get(Objects.requireNonNull(mf.getServersComboBox().getSelectedItem()).toString()), this);
            ss.init();
            mfFrame.setVisible(false);
        });
    }

    public JFrame getMfFrame() {
        return mfFrame;
    }
}
