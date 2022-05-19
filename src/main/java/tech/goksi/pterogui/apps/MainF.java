package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.Account;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.exceptions.LoginException;
import tech.goksi.pterogui.frames.MainFrame;
import tech.goksi.pterogui.frames.MassActionDialog;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        Account acc;
        try{
            acc = FirstTime.getInstance().getApi().retrieveAccount().execute();
        }catch (LoginException e){
            JOptionPane.showMessageDialog(mf, "Wrong API key or APP url, delete data.json and try again!", "Critical error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }
        mf.getNameLbl().setText("Welcome %name !".replaceAll("%name", acc.getUserName()));
        final boolean[] rmFirst = {true};  //just so i can remove first default item
        if(acc.isRootAdmin()){
            FirstTime.getInstance().getApi().retrieveServers(true).forEachAsync(srv -> loadServers(mf, rmFirst, srv));
        }else {
            FirstTime.getInstance().getApi().retrieveServers(false).forEachAsync(srv -> loadServers(mf, rmFirst, srv));
        }

        mfFrame = new JFrame("PteroGUI | Main");
        mfFrame.setResizable(false);
        mfFrame.setContentPane(mf);
        mfFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        mfFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mfFrame.pack();
        mfFrame.setLocationRelativeTo(null);
        mfFrame.setVisible(true);
        mf.getMassBtn().addActionListener(e ->{
            MassActionDialog mad = new MassActionDialog(mfFrame, mf);
            mad.setResizable(false);
            mad.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            mad.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mf.getMassBtn().setEnabled(true);
                }
            });
            mad.setVisible(true);
            mf.getMassBtn().setEnabled(false);
            mad.getOkButton().addActionListener(ev -> {
                int index = mad.getPowerAction().getSelectedIndex();
                ServerSettings.State state;
                switch (index){
                    case 1: state = ServerSettings.State.STOP;
                            break;
                    case 2: state = ServerSettings.State.KILL;
                            break;
                    case 3: state = ServerSettings.State.RESTART;
                            break;
                    default: state = ServerSettings.State.START;
                }
                for(Map.Entry<String, ClientServer> srv : servers.entrySet()){
                    Objects.requireNonNull(ServerSettings.switchState(state, srv.getValue())).executeAsync();
                }
                mad.dispose();
                mf.getMassBtn().setEnabled(true);

            });
        });
        mf.getEditBtn().addActionListener(e ->{
            ServerSettings ss = new ServerSettings(servers.get(Objects.requireNonNull(mf.getServersComboBox().getSelectedItem()).toString()), this);
            ss.init();
            mfFrame.setVisible(false);
        });
    }

    private boolean loadServers(MainFrame mf, boolean[] rmFirst, ClientServer srv) {
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
    }

    public JFrame getMfFrame() {
        return mfFrame;
    }
}
