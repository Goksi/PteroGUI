package tech.goksi.pterogui.events;

import tech.goksi.pterogui.apps.FileManager;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ClickEvent implements MouseListener {
    private final JTree tree;
    private final FileManager fm;
    public ClickEvent(JTree tree, FileManager fm){
        this.tree = tree;
        this.fm = fm;
    }
    /*TODO: npe if clicked fast 2 , probably tree.getLastSelectedPathComponent() null */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && !e.isConsumed() && tree.getModel().getChildCount(tree.getLastSelectedPathComponent()) == 0
            &&   !(((DefaultMutableTreeNode) Objects.requireNonNull(tree.getSelectionPath()).getLastPathComponent()).getAllowsChildren()) ) {
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
