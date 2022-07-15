package tech.goksi.pterogui.events;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.goksi.pterogui.apps.FileManager;
import tech.goksi.pterogui.entities.LazyNode;

import javax.swing.event.TreeExpansionEvent;

import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;


public class TreeExpandEvent implements TreeWillExpandListener {
    private final DefaultTreeModel model;
    private final ClientServer server;
    private final FileManager fileManager;
    public TreeExpandEvent(FileManager fileManager, ClientServer server ){
        this.server = server;
        this.fileManager = fileManager;
        this.model = fileManager.getModel();
    }
    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        TreePath path = event.getPath();
        if(path.getLastPathComponent() instanceof LazyNode){
            LazyNode node = (LazyNode) path.getLastPathComponent();
            node.loadChildren(fileManager.getDirectoryFromPath(path.toString()),server, model);
        }
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
}
