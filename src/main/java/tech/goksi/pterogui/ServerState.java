package tech.goksi.pterogui;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;

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

    public void executeAction(ClientServer server, Consumer<Void> callback){
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
        action.executeAsync(callback);
    }
    public void executeAction(ClientServer server) {
        executeAction(server, null);
    }


}
