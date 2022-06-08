/*
 * Created by JFormDesigner on Sun Jun 05 21:56:42 CEST 2022
 */

package tech.goksi.pterogui.frames;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class FileEditPanel extends JPanel {
    public FileEditPanel() {
        initComponents();
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JButton getButton2() {
        return button2;
    }

    public JButton getButton1() {
        return button1;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        button1 = new JButton();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        button2 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(500, 340));

        //---- button1 ----
        button1.setText("Save");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }

        //---- button2 ----
        button2.setText("Close");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(button1)
                        .addComponent(button2))
                    .addContainerGap())
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton button1;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
