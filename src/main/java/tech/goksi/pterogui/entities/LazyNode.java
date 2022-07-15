package tech.goksi.pterogui.entities;


import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
import com.mattmalec.pterodactyl4j.client.entities.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class LazyNode extends DefaultMutableTreeNode {
    private boolean childrenLoaded = false;
    private final Logger logger;



    public LazyNode(GenericFile file){
        add(new DefaultMutableTreeNode("Loading...", false));
        setAllowsChildren(!file.isFile());
        setUserObject(file.getName());
        this.logger = LoggerFactory.getLogger(this.getClass().getName());

    }

    private void setChildren(List<LazyNode> children){
        removeAllChildren();
        setAllowsChildren(children.size() > 0);
        for(LazyNode node : children){
            add(node);
        }
        childrenLoaded = true;
    }
    /*TODO: childrenLoaded doesn't actually work, update it does but its calling api anyway because of file in args, probably will change that*/
    public void loadChildren(GenericFile file, ClientServer server, DefaultTreeModel model){
        if (childrenLoaded) return;
        new SwingWorker<List<LazyNode>, Void>() {
            @Override
            protected List<LazyNode> doInBackground() throws Exception {
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
}
