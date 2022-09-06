package tech.goksi.pterogui.events;

import com.mattmalec.pterodactyl4j.client.managers.WebSocketManager;
import com.mattmalec.pterodactyl4j.client.ws.events.AuthSuccessEvent;
import com.mattmalec.pterodactyl4j.client.ws.events.output.OutputEvent;
import com.mattmalec.pterodactyl4j.client.ws.hooks.ClientSocketListenerAdapter;

import javax.swing.*;

public class Websocket extends ClientSocketListenerAdapter {
    private final JTextArea console;
    public Websocket(JTextArea console){
        this.console = console;
    }
    @Override
    public void onAuthSuccess(AuthSuccessEvent event){
        event.getWebSocketManager().request(WebSocketManager.RequestAction.LOGS);
    }

    @Override
    public void onOutput(OutputEvent event){
        console.append(event.getLine().replaceAll("\u001B", ""));
        console.append("\n");
    }
}
