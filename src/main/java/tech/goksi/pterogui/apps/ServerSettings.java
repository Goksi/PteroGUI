package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import tech.goksi.pterogui.frames.ConsoleForm;
import tech.goksi.pterogui.frames.ServerSettingsFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class ServerSettings {
    private final ClientServer server;
    private final MainF mainForm;
    public ServerSettings(ClientServer server, MainF mainForm){
        this.server = server;
        this.mainForm = mainForm;
    }

    public void init(){
        JFrame jFrame = new JFrame("PteroGUI | " + server.getIdentifier());
        ServerSettingsFrame ssf = new ServerSettingsFrame();
        if(!server.isSuspended()){
            server.retrieveUtilization().executeAsync(utilization -> {
                ssf.getServerInfoLabel().setText(ssf.getServerInfoLabel().getText().replaceAll("%name", server.getName()).replaceAll("%id", server.getIdentifier())
                        .replaceAll("%ip", server.getPrimaryAllocation().getIP() + ":" + server.getPrimaryAllocation().getPort())
                        .replaceAll("%status", utilization.getState().name()).replaceAll("%egg", server.getEgg().getName()).replaceAll("%node", server.getNode()));
            });
        }else{
            ssf.getServerInfoLabel().setText(ssf.getServerInfoLabel().getText().replaceAll("%name", server.getName()).replaceAll("%id", server.getIdentifier())
                    .replaceAll("%ip", server.getPrimaryAllocation().getIP() + ":" + server.getPrimaryAllocation().getPort())
                    .replaceAll("%status", "SUSPENDED").replaceAll("%egg", server.getEgg().getName()).replaceAll("%node", server.getNode()));
        }
        /*making whole console frame here, will add websocket impl*/
        ssf.getConsoleBtn().addActionListener(e ->{
            ssf.getConsoleBtn().setEnabled(false);
            JFrame console = new JFrame("PteroGUI | Console");
            ConsoleForm cf = new ConsoleForm();
            console.setContentPane(cf);
            console.setResizable(false);
            console.pack();
            console.setVisible(true);
            console.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
            WebSocketManager wss = server.getWebSocketBuilder().addEventListeners(new Websocket(cf.getConsoleTxt())).build();
            cf.getCommandBtn().addActionListener(event -> {
                if(cf.getCommandTxt().getText().length() < 2) JOptionPane.showMessageDialog(cf, "Command can't be that short!", "Invalid command", JOptionPane.WARNING_MESSAGE);
                else wss.sendCommand(cf.getCommandTxt().getText());
                cf.getCommandTxt().setText(null);
            });
            console.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    console.dispose();
                    ssf.getConsoleBtn().setEnabled(true);
                    wss.shutdown();
                }
            });
                });
        /*end of console*/




        jFrame.setContentPane(ssf);
        jFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);


        /*just that server selector opens after closing this form*/
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
                mainForm.getMfFrame().setVisible(true);
            }
        });

    }
}
