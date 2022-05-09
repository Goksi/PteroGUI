package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import tech.goksi.pterogui.frames.ConsoleForm;
import tech.goksi.pterogui.frames.ServerSettingsFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerSettings {
    private final ClientServer server;
    private final MainF mainForm;
    private ScheduledExecutorService executorService;
    public ServerSettings(ClientServer server, MainF mainForm){
        this.server = server;
        this.mainForm = mainForm;
    }
    enum State{
        START,
        STOP,
        KILL,
        RESTART
    }

    public void init(){
        JFrame jFrame = new JFrame("PteroGUI | " + server.getIdentifier());
        ServerSettingsFrame ssf = new ServerSettingsFrame();
        if(!server.isSuspended()){
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(()->{
                server.retrieveUtilization().executeAsync(utilization -> {
                    ssf.getServerInfoLabel().setText(ssf.getServerInfoLabel().getText().replaceAll("%name", server.getName()).replaceAll("%id", server.getIdentifier())
                            .replaceAll("%ip", server.getPrimaryAllocation().getIP() + ":" + server.getPrimaryAllocation().getPort())
                            .replaceAll("%egg", server.getEgg().getName()).replaceAll("%node", server.getNode())
                            .replaceAll("RUNNING|OFFLINE|STARTING|STOPPING|%status", utilization.getState().name()));
                    ssf.getMemoryUsageLbl().setText("Memory usage: %u / %a MB".replaceAll("%a", server.getLimits().getMemory()).
                            replaceAll("%u", String.format("%.2f", (float)utilization.getMemory()/ 1024F / 1024F)));
                });
            }, 0, 5, TimeUnit.SECONDS);

        }else{
            ssf.getServerInfoLabel().setText(ssf.getServerInfoLabel().getText().replaceAll("%name", server.getName()).replaceAll("%id", server.getIdentifier())
                    .replaceAll("%ip", server.getPrimaryAllocation().getIP() + ":" + server.getPrimaryAllocation().getPort())
                    .replaceAll("%egg", server.getEgg().getName()).replaceAll("%node", server.getNode()).replaceAll("%status", "SUSPENDED"));
            ssf.getConsoleBtn().setEnabled(false);
            ssf.getChangeStateBtn().setEnabled(false);
            ssf.getStateComboBox().setEnabled(false);
        }
        /*making whole console frame here*/
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

        ssf.getChangeStateBtn().addActionListener(e ->{
            switch (ssf.getStateComboBox().getSelectedIndex()){
                case 0: Objects.requireNonNull(switchState(State.START)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent START signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 1: Objects.requireNonNull(switchState(State.STOP)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent STOP signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 2: Objects.requireNonNull(switchState(State.KILL)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent KILL signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 3: Objects.requireNonNull(switchState(State.RESTART)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent RESTART signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
            }
                });






        jFrame.setContentPane(ssf);
        jFrame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setLocationRelativeTo(mainForm.getMfFrame());
        jFrame.setVisible(true);


        /*just that server selector opens after closing this form*/
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                jFrame.dispose();
                mainForm.getMfFrame().setVisible(true);
                if(executorService != null &&!executorService.isShutdown())executorService.shutdown();
            }
        });

    }

    private PteroAction<Void> switchState(State state){
        switch (state){
            case KILL: return server.kill();
            case STOP: return server.stop();
            case START: return server.start();
            case RESTART: return server.restart();
        }
        return null;
    }
}