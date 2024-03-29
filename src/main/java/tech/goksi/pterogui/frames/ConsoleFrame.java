/*
 * Created by JFormDesigner on Sun May 08 21:48:01 CEST 2022
 */

package tech.goksi.pterogui.frames;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Goksi
 */
public class ConsoleFrame extends JPanel {
    public ConsoleFrame() {
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

    public JScrollPane getScrollPane() {
        return scrollPane1;
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
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(commandTxt, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(commandBtn, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(commandBtn, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addComponent(commandTxt, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
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
