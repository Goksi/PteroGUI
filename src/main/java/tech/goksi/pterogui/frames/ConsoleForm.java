/*
 * Created by JFormDesigner on Sun May 08 21:48:01 CEST 2022
 */

package tech.goksi.pterogui.frames;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author unknown
 */
public class ConsoleForm extends JPanel {
    public ConsoleForm() {
        initComponents();
    }

    public JTextArea getConsoleTxt() {
        return consoleTxt;
    }

    public JButton getCommandBtn() {
        return commandBtn;
    }

    public JTextField getCommandTxt() {
        return commandTxt;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        consoleTxt = new JTextArea();
        commandTxt = new JTextField();
        commandBtn = new JButton();

        //======== this ========

        //======== scrollPane1 ========
        {

            //---- consoleTxt ----
            consoleTxt.setEditable(false);
            scrollPane1.setViewportView(consoleTxt);
        }

        //---- commandBtn ----
        commandBtn.setText("Send command");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(commandTxt, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(commandBtn, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(commandBtn)
                        .addComponent(commandTxt, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JScrollPane scrollPane1;
    private JTextArea consoleTxt;
    private JTextField commandTxt;
    private JButton commandBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
