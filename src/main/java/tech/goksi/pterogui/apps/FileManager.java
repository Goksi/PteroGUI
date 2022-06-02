package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


public class FileManager {
    private final JTree tree;
    private final ClientServer server;
    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
    }

    public void updateUI(){
        tree.setModel(new DefaultTreeModel(
                new DefaultMutableTreeNode(server.getName()) {
                    {
                       // add(new DefaultMutableTreeNode());
                        server.retrieveDirectory().execute().getFiles().forEach(e -> System.out.println(e.getName())); //vraca sve fajlove, bilo oni dir ili fajlove


                    }
                }));


    }
}
