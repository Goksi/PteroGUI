/*
 * Created by JFormDesigner on Sun May 08 20:22:39 CEST 2022
 */

package tech.goksi.pterogui.frames;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Goksi
 */
public class ServerSettingsFrame extends JPanel {
    public ServerSettingsFrame() {
        initComponents();
    }

    public JLabel getServerInfoLabel() {
        return label2;
    }

    public JButton getConsoleBtn() {
        return consoleBtn;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        consoleBtn = new JButton();

        //======== this ========

        //---- label1 ----
        label1.setText("Info:");

        //---- label2 ----
        label2.setText("<html><body>Name: %name<br>Identifier: %id<br>IP: %ip<br>Status: %status<br>Egg: %egg<br>Node: %node</body></html>");

        //---- consoleBtn ----
        consoleBtn.setText("Open console");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(consoleBtn)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1))
                    .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(label1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(consoleBtn))
                    .addContainerGap(110, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel label2;
    private JButton consoleBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
