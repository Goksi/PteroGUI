package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import tech.goksi.pterogui.frames.FileEditPanel;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class FileManager {
    public static boolean STOP_RECURSIVE = false;  //really can't think of another way rn
    private static final Map<ClientServer, DefaultTreeModel> nodesCache = new HashMap<>();
    private Map<String, File> files = new HashMap<>();
    /*probably will also save all files and remove them on windows close event*/
    private final JTree tree;
    private final ClientServer server ;
    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
    }

    public Map<String, File> getFiles() {
        return files;
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
        if(STOP_RECURSIVE) return; //nez jebe i dalje ako se sve ne ucita, a to je bas problem ako je neka nodejs aplikacija
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
                subFolder.getFiles().forEach(files -> updateFiles(files, dir));
            });
            if(!Objects.equals(dir, lastNode)) lastNode.add(dir);
        }else {
            lastNode.add(new DefaultMutableTreeNode(file.getName()));
            files.put(file.getPath(), (File) file);
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
        String finalS = sb.substring(0, sb.length() - 1);
        File file = files.get(finalS);
        JFrame fileEdit = new JFrame("PteroGUI | " + file.getName() );
        FileEditPanel fep = new FileEditPanel();
        fep.getTextArea1().setText(file.retrieveContent().execute());
        fileEdit.setContentPane(fep);
        fileEdit.setResizable(false);
        fileEdit.pack();
        fileEdit.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        fileEdit.setLocationRelativeTo(tree);
        fileEdit.setVisible(true);
    }
    /*TODO: brisanje fajla*/
}
