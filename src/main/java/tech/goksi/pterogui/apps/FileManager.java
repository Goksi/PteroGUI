package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;


public class FileManager {
    public static boolean STOP_RECURSIVE = false;  //really can't think of another way rn
    private final JTree tree;
    private final ClientServer server ;
    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
    }


    public void updateUI(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(server.getName());
        server.retrieveDirectory().executeAsync(f -> updateFiles(f, root));

        DefaultTreeModel model = new DefaultTreeModel(root);

        tree.setModel(model);
    }
   /*problem ako se izadje dok fajlovi nisu skroz ucitani*/
    private void updateFiles(GenericFile file, DefaultMutableTreeNode lastNode){
        if(STOP_RECURSIVE) return; //little faster, but it still freezes (probably will add async)
        if(!file.isFile()){
            DefaultMutableTreeNode dir;
            if(!Objects.equals(file.getName(), "Root Directory")){
                dir = new DefaultMutableTreeNode(file.getName());
            }else {
                dir = lastNode;
            }
            server.retrieveDirectory(file.getPath()).executeAsync(subFolder -> {
                if(!Objects.equals(subFolder.getName(), file.getName())){
                    dir.add(new DefaultMutableTreeNode(subFolder.getName()));
                }
                subFolder.getFiles().forEach(files -> {
                    updateFiles(files, dir);
                });
            });
            if(!Objects.equals(dir, lastNode)) lastNode.add(dir);
        }else {
            lastNode.add(new DefaultMutableTreeNode(file.getName()));
        }
    }
    /*TODO: metode za otvaranje, i brisanje fajla*/
}
