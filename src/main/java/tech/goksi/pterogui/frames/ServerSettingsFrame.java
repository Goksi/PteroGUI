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

    public JButton getChangeStateBtn() {
        return changeStateBtn;
    }

    public JComboBox<String> getStateComboBox() {
        return stateComboBox;
    }

    public JLabel getMemoryUsageLbl() {
        return memoryUsageLbl;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        consoleBtn = new JButton();
        changeStateBtn = new JButton();
        stateComboBox = new JComboBox<>(new String[]{"Start", "Stop", "Kill", "Restart"});
        memoryUsageLbl = new JLabel();

        //======== this ========

        //---- label1 ----
        label1.setText("Info:");

        //---- label2 ----
        label2.setText("<html><body>Name: %name<br>Identifier: %id<br>IP: %ip<br>Status: %status<br>Egg: %egg<br>Node: %node</body></html>");

        //---- consoleBtn ----
        consoleBtn.setText("Open console");

        //---- changeStateBtn ----
        changeStateBtn.setText("Change state");

        //---- memoryUsageLbl ----
        memoryUsageLbl.setText("Memory usage: %u/%a MB");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(consoleBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(changeStateBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(stateComboBox, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
                        .addComponent(memoryUsageLbl))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1))
                    .addGap(65, 65, 65))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(label1))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(memoryUsageLbl)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(consoleBtn)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(changeStateBtn)
                                .addComponent(stateComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(110, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JLabel label2;
    private JButton consoleBtn;
    private JButton changeStateBtn;
    private JComboBox<String> stateComboBox;
    private JLabel memoryUsageLbl;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
