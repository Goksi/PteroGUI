package tech.goksi.pterogui;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.requests.PteroActionImpl;

import java.util.function.Consumer;

public enum ServerState {
    START(0),
    STOP(1),
    KILL(2),
    RESTART(3);

    private final int id;
    ServerState(int id){
        this.id = id;
    }

    public void executeAction(ClientServer server, Consumer<Void> success, Consumer<? super Throwable> failure){
        PteroAction<Void> action;
        switch (id){
            case 1: action = server.stop();
                break;
            case 2: action = server.kill();
                break;
            case 3: action = server.restart();
                break;
            default:
                action = server.start();
        }
        action.executeAsync(success, failure);
    }
    public void executeAction(ClientServer server) {
        executeAction(server, null, null);
    }

    public void executeAction(ClientServer server, Consumer<Void> success){
        executeAction(server,success,null);
    }


}
