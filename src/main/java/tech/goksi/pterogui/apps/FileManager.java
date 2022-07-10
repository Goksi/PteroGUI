package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import tech.goksi.pterogui.frames.FileEditPanel;
import tech.goksi.pterogui.frames.GenericFrame;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;


public class FileManager {
    public static boolean STOP_RECURSIVE = false;  //really can't think of another way rn
    private static final Map<ClientServer, DefaultTreeModel> nodesCache = new HashMap<>();
    private boolean edited = false;
    private boolean cut = false;
    private DefaultMutableTreeNode cuttedNode;
    private File currentFile;
    private File clipboard;
    private FileEditPanel fep;
    private static final List<String> NON_READABLE = Arrays.asList("sqlite", "jar", "exe", "db", "mp3");
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
        }else {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(server.getName());
            server.retrieveDirectory().executeAsync(f -> updateFiles(f, root));
            model = new DefaultTreeModel(root);
            nodesCache.put(server, model);
        }
        tree.setModel(model);

    }

   /*TODO: fix thread blocking, maybe load up to 2 levels and rest after tree expand event*/
    private void updateFiles(GenericFile file, DefaultMutableTreeNode lastNode){
        if(STOP_RECURSIVE) return; //nez jebe i dalje ako se sve ne ucita, a to je bas problem ako je neka nodejs aplikacija
        if(!file.isFile()){
            DefaultMutableTreeNode dir;
            if(!file.getName().equals("Root Directory")){
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
        }
    }

    public void openFile(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        File file = getFileFromPath(rawPath);
        if(!file.isFile() || NON_READABLE.contains(file.getName().split("\\.")[1])) return;
        currentFile = file;
        fep = new FileEditPanel();
        GenericFrame fileEdit = new GenericFrame("PteroGUI | " + file.getName(), fep, tree);
        fep.getButton2().addActionListener(e -> fileEdit.dispose());
        fep.getButton1().addActionListener(e -> save(fileEdit));
        String content = file.retrieveContent().execute();
        fep.getTextArea1().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void check(){
                if(!(content.equals(fep.getTextArea1().getText())) ){
                    fileEdit.setTitle("PteroGUI | " +"*" +file.getName());
                    edited = true;
                }else {
                    fileEdit.setTitle("PteroGUI | "  +file.getName());
                    edited = false;
                }
            }
        });
        fep.getTextArea1().setText(content);
        fileEdit.setVisible(true);
    }

    private void save(JFrame jframe){
        if(edited){
            server.getFileManager().write(currentFile, fep.getTextArea1().getText()).execute();
            jframe.dispose();
        }
    }

    public void delete(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        ((DefaultTreeModel) tree.getModel()).removeNodeFromParent((DefaultMutableTreeNode)tree.getLastSelectedPathComponent());
        File file = getFileFromPath(rawPath);
        file.delete().execute();
    }

    private File getFileFromPath(String rawPath){
        rawPath = rawPath.substring(1, rawPath.length() - 1);
        String[] path = rawPath.replaceAll(" ", "").split(",");
        String finalS = String.join("/", Arrays.copyOfRange(path, 1, path.length-1));
        Directory dir = server.retrieveDirectory(finalS).execute();
        return (File) dir.getFiles().stream().filter(file -> file.getName().equals(path[path.length-1])).findFirst().orElse(null);
    }

    public void copy(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        clipboard = getFileFromPath(rawPath);
    }
    public void cut(){
        copy();
        cut = true;
        cuttedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    }

    public void paste(){
        if(clipboard != null){
            File selectedOne = getFileFromPath(Objects.requireNonNull(tree.getSelectionPath()).toString());
            ((DefaultMutableTreeNode) tree.getSelectionPath().getParentPath().getLastPathComponent()).add(new DefaultMutableTreeNode(clipboard.getName())); // ne reloada tree nakon dodavanja node-a
            ((DefaultTreeModel) tree.getModel()).reload();;
            String dirToPaste = selectedOne.getPath().replaceAll(selectedOne.getName(), "");
            String clipboardDir = clipboard.getPath().replaceAll(clipboard.getName(), "");
            clipboard.copy().execute();
            clipboard.rename(dirToPaste + clipboard.getName()).execute();
            server.retrieveDirectory(clipboardDir).executeAsync(dir -> {
                for(GenericFile file : dir.getFiles()){
                    if(file.getName().contains(clipboard.getName().split("\\.")[0]) && !dirToPaste.equals(clipboardDir)){
                        if(cut){
                            file.delete().execute();
                            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(cuttedNode);
                            cuttedNode = null;
                            cut = false;
                        }else {
                            file.rename(clipboard.getPath()).execute();
                        }
                        break;
                    }
                }
            });

        }
    }
}
