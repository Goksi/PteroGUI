package tech.goksi.pterogui.apps;

import tech.goksi.pterogui.events.ContextMenuEvent;

import javax.swing.*;
import java.awt.*;

public class ContextMenu extends JPopupMenu {
    public ContextMenu(FileManager fileManager){
        super("Edit");
        ContextMenuEvent e = new ContextMenuEvent(fileManager);
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(e);
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(e);
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(e);
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(e);
        add(open);
        add(delete);
        add(copy);
        add(paste);
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
