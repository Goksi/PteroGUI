/*
 * Created by JFormDesigner on Wed May 04 23:22:51 CEST 2022
 */

package tech.goksi.pterogui.frames;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Goksi
 */
public class MainFrame extends JPanel {
    public MainFrame() {
        initComponents();
    }

    public JComboBox<String> getServers() {
        return servers;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        name = new JLabel();
        servers = new JComboBox<>(new String[]{"You dont own any servers!"});
        label1 = new JLabel();

        //======== this ========

        //---- name ----
        name.setText("Loading...");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setIcon(new ImageIcon(getClass().getResource("/rsz_pterodactyl.png")));
        name.setMaximumSize(new Dimension(125, 32));
        name.setPreferredSize(new Dimension(125, 32));
        name.setMinimumSize(new Dimension(125, 32));

        //---- label1 ----
        label1.setText("Select your server: ");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addComponent(name, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(label1)
                            .addGap(18, 18, 18)
                            .addComponent(servers, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(servers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(184, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel name;
    private JComboBox<String> servers;
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JLabel getNameLbl() {
        return name;
    }

    public JComboBox<String> getServersComboBox(){
        return servers;
    }
}
