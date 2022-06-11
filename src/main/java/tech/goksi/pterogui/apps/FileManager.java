package tech.goksi.pterogui.apps;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import tech.goksi.pterogui.frames.FileEditPanel;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class FileManager {
    public static boolean STOP_RECURSIVE = false;  //really can't think of another way rn
    private static final Map<ClientServer, DefaultTreeModel> nodesCache = new HashMap<>();
    private boolean edited = false;
    private File currentFile;
    private FileEditPanel fep;
    private Map<String, File> files = new HashMap<>(); //npe
    private static List<String> NON_READABLE = Arrays.asList("sqlite", "jar", "exe", "db");
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
            files.put(file.getPath(), (File) file); //problem, null pointer
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
        if(NON_READABLE.contains(file.getName().split("\\.")[1])) return;
        currentFile = file;
        JFrame fileEdit = new JFrame("PteroGUI | " + file.getName() );
        fep = new FileEditPanel();
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
        fileEdit.setContentPane(fep);
        fileEdit.setResizable(false);
        fileEdit.pack();
        fileEdit.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        fileEdit.setLocationRelativeTo(tree);
        fileEdit.setVisible(true);
    }
    private void save(JFrame jframe){
        if(edited){
            server.getFileManager().write(currentFile, fep.getTextArea1().getText()).execute();
            jframe.dispose();
        }
    }

    /*TODO: brisanje fajla*/
}
