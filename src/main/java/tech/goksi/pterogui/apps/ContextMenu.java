package tech.goksi.pterogui.apps;

import javax.swing.*;
import java.awt.*;

public class ContextMenu extends JPopupMenu {
    public ContextMenu(){
        super("Edit");
        add(new JMenuItem("Open"));
        add(new JMenuItem("Delete"));
    }
    @Override
    public void show(final Component comp, final int x, final int y){
        if (comp instanceof JTree) { //check if it's actually a tree (just in case)
            final JTree tree = (JTree) comp;
            tree.setSelectionPath(tree.getPathForLocation(x,y));
            Object lastPath;
            if((lastPath = tree.getLastSelectedPathComponent()) != null && tree.getModel().getChildCount(lastPath) == 0){
                comp.requestFocus();
                super.show(comp, x, y);
            }
         }
    }
}
