package tech.goksi.pterogui.events;

import tech.goksi.pterogui.apps.ContextMenu;
import tech.goksi.pterogui.apps.FileManager;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickEvent implements MouseListener {
    private final JTree tree;
    private final FileManager fm;
    public ClickEvent(JTree tree, FileManager fm){
        this.tree = tree;
        this.fm = fm;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getComponent() instanceof ContextMenu){

        } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && !e.isConsumed() && tree.getModel().getChildCount(tree.getLastSelectedPathComponent()) == 0) {
            e.consume();
            fm.openFile();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
