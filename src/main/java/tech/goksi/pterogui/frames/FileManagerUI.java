/*
 * Created by JFormDesigner on Thu Jun 02 15:19:31 CEST 2022
 */

package tech.goksi.pterogui.frames;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.tree.*;

/**
 * @author Goksi
 */
public class FileManagerUI extends JPanel {
    public FileManagerUI() {
        initComponents();
    }

    public JTree getTree1() {
        return tree1;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        tree1 = new JTree();

        //======== this ========

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tree1);
        }

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTree tree1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
