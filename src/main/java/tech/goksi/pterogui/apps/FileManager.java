package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class FileManager {
    public static boolean STOP_RECURSIVE = false;  //really can't think of another way rn
    private final String[] textFiles = {"json", "txt", "yml", "yaml", "java", "tex", "js", "md", "py", "bat"};
    private static final Map<ClientServer, DefaultTreeModel> nodesCache = new HashMap<>();
    private final JTree tree;
    private final ClientServer server ;
    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
    }


    public void updateUI(){
        DefaultTreeModel model;
        if(nodesCache.containsKey(server)){
            model = nodesCache.get(server);
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() ->{
                nodesCache.remove(server);
            }, 2, TimeUnit.MINUTES);
        }else {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(server.getName());
            server.retrieveDirectory().executeAsync(f -> updateFiles(f, root));
            model = new DefaultTreeModel(root);
            nodesCache.put(server, model);
        }
        tree.setModel(model);

    }
   /*problem ako se izadje dok fajlovi nisu skroz ucitani*/
    private void updateFiles(GenericFile file, DefaultMutableTreeNode lastNode){
        if(STOP_RECURSIVE) return; //little faster, but it still freezes (probably make it sync)
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

    public void open(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        rawPath = rawPath.substring(1, rawPath.length() - 1);
        String[] path = rawPath.replaceAll(" ", "").split(",");
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        for(int i = 1; i<path.length; i++){
            sb.append(path[i]).append("/");
        }

    }
    /*TODO: metode za otvaranje, i brisanje fajla*/
}
