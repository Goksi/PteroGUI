package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;



public class FileManager {
    private final JTree tree;
    private final ClientServer server ;
    private DefaultTreeModel model;


    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
    }


    public void updateUI(){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(server.getName());


        server.retrieveDirectory().executeAsync(e -> e.getFiles().forEach(f ->{
            addFiles("/", f);

            if(f.getMimeType().equals("inode/directory")){
                DefaultMutableTreeNode dir = new DefaultMutableTreeNode(f.getName());

                server.retrieveDirectory(f.getName()).executeAsync(dirFile -> {


                    dirFile.getFiles().forEach(file ->{
                        //nesto ovde idk
                        dir.add(new DefaultMutableTreeNode(file.getName()));
                    }); //mozda neka rekuzivna funkcija ?

                });

                root.add(dir);


            }else {
                root.add(new DefaultMutableTreeNode(f.getName()));
            }
        }));
        model = new DefaultTreeModel(root);

        tree.setModel(model);
    }

    private void addFiles(String directory, GenericFile f){
        DefaultMutableTreeNode dire = new DefaultMutableTreeNode(directory);

        if(f.getMimeType().equals("inode/directory")){
            server.retrieveDirectory(directory).executeAsync(dir -> {


                dir.getFiles().forEach(file -> {
                    System.out.println(file.getName());
                    addFiles(file.getName(), file);
                });


            });
        }else {
            dire.add(new DefaultMutableTreeNode(f.getName()));
        }


    }

}
