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

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        name = new JLabel();

        //======== this ========

        //---- name ----
        name.setText("Loading...");
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setIcon(new ImageIcon(getClass().getResource("/cool.png")));
        name.setMaximumSize(new Dimension(125, 32));
        name.setPreferredSize(new Dimension(125, 32));
        name.setMinimumSize(new Dimension(125, 32));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(127, 127, 127)
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(161, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(355, Short.MAX_VALUE))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel name;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public JLabel getNameLbl() {
        return name;
    }
}
