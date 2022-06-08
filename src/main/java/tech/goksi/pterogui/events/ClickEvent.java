package tech.goksi.pterogui.events;

import tech.goksi.pterogui.apps.FileManager;

import javax.swing.*;
import javax.swing.tree.TreePath;
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
        if(SwingUtilities.isRightMouseButton(e)){
            /*otvara context menu na desni klil*/
            TreePath clickedPath = tree.getPathForLocation(e.getX(), e.getY());
            if(clickedPath == null) return;
            tree.setSelectionPath(clickedPath);
            System.out.println("B");
        }else if(e.getClickCount() == 2 && !e.isConsumed() && tree.getModel().getChildCount(tree.getLastSelectedPathComponent()) == 0){
            e.consume();
            fm.open();
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
