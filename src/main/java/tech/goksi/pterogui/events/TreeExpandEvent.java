package tech.goksi.pterogui.events;

import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.Directory;
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
    public TreeExpandEvent(FileManager fileManager ){
        this.model = fileManager.getModel();
    }
    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        TreePath path = event.getPath();
        if(path.getLastPathComponent() instanceof LazyNode){
            LazyNode node = (LazyNode) path.getLastPathComponent();
            Directory dir = (Directory) node.getUserObject();
            node.loadChildren(dir.into(dir).execute(), model);
        }
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
}
