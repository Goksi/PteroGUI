package tech.goksi.pterogui.entities;


import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.File;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LazyNode extends DefaultMutableTreeNode {
    private boolean childrenLoaded = false;
    private final Logger logger;



    public LazyNode(GenericFile file){
        add(new DefaultMutableTreeNode("Loading...", false));
        setAllowsChildren(!file.isFile());
        setUserObject(file);
        this.logger = LoggerFactory.getLogger(this.getClass().getName());

    }

    private void setChildren(List<LazyNode> children){
        removeAllChildren();
        //setAllowsChildren(children.size() > 0);
        for(LazyNode node : children){
            add(node);
        }
        childrenLoaded = true;
    }
    public void loadChildren(GenericFile file, DefaultTreeModel model){
        if (childrenLoaded) return;
        new SwingWorker<List<LazyNode>, Void>() {
            @Override
            protected List<LazyNode> doInBackground() {
                ArrayList<LazyNode> node = new ArrayList<>();
                if(!file.isFile()){
                    Directory dir = (Directory) file;
                    dir.getFiles().forEach(file -> node.add(new LazyNode(file)));
                }
                return node;
            }

            @Override
            protected void done() {
                try {
                    setChildren(get());
                    model.nodeStructureChanged(LazyNode.this);
                } catch (Exception e) {
                    logger.error("Error while updating node structure !", e);
                }
                super.done();
            }
        }.execute();

    }

    public static class TreeRender extends DefaultTreeCellRenderer{
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if(value instanceof LazyNode){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                /*TODO: different type of icons for different file types :thinking:*/
                if(node.getUserObject() instanceof File){
                    File file = (File) node.getUserObject();
                    setText(file.getName());
                }else if(node.getUserObject() instanceof Directory){
                    Directory dir = (Directory) node.getUserObject();
                    setText(dir.getName());
                    setIcon(dir.getName().equals("Root Directory") ? UIManager.getIcon("FileChooser.homeFolderIcon") : UIManager.getIcon("FileView.directoryIcon"));
                }
            }
            return this;
        }
    }
}
