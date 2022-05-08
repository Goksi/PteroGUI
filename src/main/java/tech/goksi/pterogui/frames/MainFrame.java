/*
 * Created by JFormDesigner on Wed May 04 23:22:51 CEST 2022
 */

package tech.goksi.pterogui.frames;

import java.awt.*;
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
        editBtn = new JButton();
        massBtn = new JButton();

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

        //---- editBtn ----
        editBtn.setText("<html><center>Edit selected<br>server</center></html>");
        editBtn.setActionCommand("<html>Edit selected<br /><center>server</center></html>");

        //---- massBtn ----
        massBtn.setText("<html>Mass<br />action</html>");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(label1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(servers, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(49, 49, 49)
                            .addComponent(editBtn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                            .addGap(51, 51, 51)
                            .addComponent(massBtn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(68, 68, 68)
                            .addComponent(name, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(servers)
                        .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(massBtn, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                        .addComponent(editBtn, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addGap(41, 41, 41))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel name;
    private JComboBox<String> servers;
    private JLabel label1;
    private JButton editBtn;
    private JButton massBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JLabel getNameLbl() {
        return name;
    }

    public JComboBox<String> getServersComboBox(){
        return servers;
    }

    public JButton getEditBtn() {
        return editBtn;
    }

    public JButton getMassBtn() {
        return massBtn;
    }
}
