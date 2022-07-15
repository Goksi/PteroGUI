package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;

import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import tech.goksi.pterogui.events.ClickEvent;
import tech.goksi.pterogui.events.TreeExpandEvent;
import tech.goksi.pterogui.frames.ConsoleForm;
import tech.goksi.pterogui.frames.FileManagerUI;
import tech.goksi.pterogui.frames.GenericFrame;
import tech.goksi.pterogui.frames.ServerSettingsFrame;

import javax.swing.*;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
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

        ServerSettingsFrame ssf = new ServerSettingsFrame();
        GenericFrame jFrame = new GenericFrame("PteroGUI | " + server.getIdentifier(), ssf, mainForm.getMfFrame());
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
            }, 0, 15, TimeUnit.SECONDS);

        }else{
            ssf.getServerInfoLabel().setText(ssf.getServerInfoLabel().getText().replaceAll("%name", server.getName()).replaceAll("%id", server.getIdentifier())
                    .replaceAll("%ip", server.getPrimaryAllocation().getIP() + ":" + server.getPrimaryAllocation().getPort())
                    .replaceAll("%egg", server.getEgg().getName()).replaceAll("%node", server.getNode()).replaceAll("%status", "SUSPENDED"));
            ssf.getConsoleBtn().setEnabled(false);
            ssf.getChangeStateBtn().setEnabled(false);
            ssf.getStateComboBox().setEnabled(false);
            ssf.getMemoryUsageLbl().setText("Memory usage: SUSPENDED");
        }
        /*file manager*/
        ssf.getFileManagerButton().addActionListener(e -> {
            FileManagerUI fmUI = new FileManagerUI();
            GenericFrame fileManagerFrame = new GenericFrame("PteroGUI | FileManager", fmUI, ssf);
            ssf.getFileManagerButton().setEnabled(false);
            FileManager fileManager = new FileManager(fmUI.getTree1(), server);
            ContextMenu contextMenu = new ContextMenu(fileManager);
            fmUI.getTree1().setComponentPopupMenu(contextMenu);
            fmUI.getTree1().addMouseListener(new ClickEvent(fmUI.getTree1(), fileManager));
            fmUI.getTree1().collapseRow(0);
            fmUI.getTree1().setRootVisible(true);
            fileManager.updateUI();
            fileManagerFrame.setVisible(true);
            fmUI.getTree1().addTreeWillExpandListener(new TreeExpandEvent(fileManager, server));

            fileManagerFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ssf.getFileManagerButton().setEnabled(true);

                }
            });

        });
        /*making whole console frame here*/
        ssf.getConsoleBtn().addActionListener(e ->{
            ssf.getConsoleBtn().setEnabled(false);
            ConsoleForm cf = new ConsoleForm();
            GenericFrame console = new GenericFrame("PteroGUI | Console", cf, null);
            console.setVisible(true);
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
                case 0: Objects.requireNonNull(switchState(State.START, server)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent START signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 1: Objects.requireNonNull(switchState(State.STOP, server)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent STOP signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 2: Objects.requireNonNull(switchState(State.KILL, server)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent KILL signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
                        break;
                case 3: Objects.requireNonNull(switchState(State.RESTART, server)).executeAsync(successful ->
                        JOptionPane.showMessageDialog(mainForm.getMfFrame(), "Successfully sent RESTART signal", "Action completed", JOptionPane.INFORMATION_MESSAGE));
            }
                });







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

    public static PteroAction<Void> switchState(State state, ClientServer server){
        switch (state){
            case KILL: return server.kill();
            case STOP: return server.stop();
            case START: return server.start();
            case RESTART: return server.restart();
        }
        return null;
    }
}
