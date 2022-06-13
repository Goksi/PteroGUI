package tech.goksi.pterogui.events;

import tech.goksi.pterogui.apps.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextMenuEvent implements ActionListener {
    private final FileManager fm;
    public ContextMenuEvent(FileManager fm){
        this.fm = fm;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JMenuItem){
            if(e.getActionCommand().equals("Open")){
                fm.openFile();
            }else if(e.getActionCommand().equals("Delete")){
                fm.delete();
            }
        }
    }
}
