package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.goksi.pterogui.entities.LazyNode;
import tech.goksi.pterogui.frames.FileEditPanelFrame;
import tech.goksi.pterogui.frames.GenericFrame;
import tech.goksi.pterogui.utils.StringUtils;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.*;


public class FileManager {
    private boolean edited = false;
    private boolean cut = false;
    private DefaultMutableTreeNode cutNode;
    private File currentFile;
    private File clipboard;
    private FileEditPanelFrame fep;
    private DefaultTreeModel model;
    private static final List<String> NON_READABLE = Arrays.asList("sqlite", "jar", "exe", "db", "mp3", "rar");
    private final JTree tree;
    private final ClientServer server ;
    private final Logger logger;
    public FileManager(JTree tree, ClientServer server){
        this.tree = tree;
        this.server = server;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void updateUI(){
        Directory rootDir = server.retrieveDirectory().execute();
        LazyNode root = new LazyNode(rootDir);
        model = new DefaultTreeModel(root);
        tree.setModel(model);
        root.loadChildren(rootDir, server, model);
        tree.collapseRow(0);
    }

    /*TODO: ArrayIndexOutOfBoundsException for files without extension*/
    public void openFile(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        File file = getFileFromPath(rawPath);
        if(!file.isFile() || NON_READABLE.contains(file.getName().split("\\.")[1])) return;
        currentFile = file;
        fep = new FileEditPanelFrame();
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
                    fileEdit.setTitle("PteroGUI | "  + file.getName());
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
    /*TODO: probably fixed space in name, need to test*/
    private File getFileFromPath(String rawPath){
        rawPath = rawPath.substring(1, rawPath.length() - 1);
        String[] path = Arrays.stream(rawPath.split(",")).map(String::trim).toArray(String[]::new);
        String finalS = String.join("/", Arrays.copyOfRange(path, 1, path.length-1));
        Directory dir = server.retrieveDirectory(finalS.length() == 0 ? "/" : finalS).execute();
        return (File) dir.getFiles().stream().filter(file -> file.getName().equals(path[path.length-1])).findFirst().orElse(null);
    }
    public Directory getDirectoryFromPath(String rawPath){
        rawPath = rawPath.substring(1, rawPath.length() - 1);
        String[] path = rawPath.replaceAll(" ", "").split(",");
        String finalS = String.join("/", Arrays.copyOfRange(path, 1, path.length));
        return server.retrieveDirectory(finalS).execute();
    }
    public void copy(){
        String rawPath = Objects.requireNonNull(tree.getSelectionPath()).toString();
        clipboard = getFileFromPath(rawPath);
    }
    /*TODO: cut problem*/
    public void cut(){
        copy();
        cut = true;
        cutNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
    }
    /*TODO: not working if moving dir up (have to use ../ pathing, gonna kms)*/

    public void paste(){
        if(clipboard == null) return;
        TreePath selectionPath = tree.getSelectionPath();
        if(selectionPath == null) return;
        File selectedFile = getFileFromPath(selectionPath.toString());
        String tmp;
        String dirToPaste = (tmp = selectedFile.getPath().replaceAll(selectedFile.getName(), "")).equals("/") ? tmp : "/" + tmp;
        String currentDir = (tmp = clipboard.getPath().replaceAll(clipboard.getName(), "")).equals("/") ? tmp : "/" + tmp;
        int pasteSlash, currentSlash; //initialization in case pasting to upper dir is needed, it won't go out of int range.... ig ¯\_(ツ)_/¯
        if((pasteSlash = (int) dirToPaste.chars().filter(ch -> ch == '/').count()) < (currentSlash = (int) currentDir.chars().filter(ch -> ch == '/').count())){
            int numberOfDots = currentSlash - pasteSlash;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < numberOfDots; i++) sb.append("../");
            dirToPaste = sb.toString();
        }
        logger.debug("Dir to paste is {}", dirToPaste + clipboard.getName());
    }

    public DefaultTreeModel getModel() {
        return model;
    }

    /*public void paste(){
        if(clipboard != null){
            File selectedOne = getFileFromPath(Objects.requireNonNull(tree.getSelectionPath()).toString());
            ((DefaultMutableTreeNode) tree.getSelectionPath().getParentPath().getLastPathComponent()).add(new DefaultMutableTreeNode(clipboard.getName())); // ne reloada tree nakon dodavanja node-a
            ((DefaultTreeModel) tree.getModel()).reload();;
            String dirToPaste = selectedOne.getPath().replaceAll(selectedOne.getName(), "");
            String clipboardDir = clipboard.getPath().replaceAll(clipboard.getName(), "");
            clipboard.copy().execute();
            logger.debug("Final string to paste is {}", dirToPaste + clipboard.getName());
            clipboard.rename(dirToPaste + clipboard.getName()).execute();
            server.retrieveDirectory(clipboardDir).executeAsync(dir -> {
                for(GenericFile file : dir.getFiles()){
                    if(file.getName().contains(clipboard.getName().split("\\.")[0]) && !dirToPaste.equals(clipboardDir)){
                        if(cut){
                            file.delete().execute();
                            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(cutNode);
                            cutNode = null;
                            cut = false;
                        }else {
                            file.rename(clipboard.getPath()).execute();
                        }
                        break;
                    }
                }
            });
        }
    }*/

    /*private File getFileFromPath(String rawPath){
        rawPath = rawPath.substring(1, rawPath.length() - 1);
        String[] path = rawPath.replaceAll(" ", "").split(",");
        String finalS = String.join("/", Arrays.copyOfRange(path, 1, path.length-1));
        Directory dir = server.retrieveDirectory(finalS.length() == 0 ? "/" : finalS).execute();
        return (File) dir.getFiles().stream().filter(file -> file.getName().equals(path[path.length-1])).findFirst().orElse(null);
    }*/
}
