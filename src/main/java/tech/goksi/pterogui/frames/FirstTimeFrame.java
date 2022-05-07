/*
 * Created by JFormDesigner on Wed May 04 00:26:15 CEST 2022
 */

package tech.goksi.pterogui.frames;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Goksi
 */
public class FirstTimeFrame extends JPanel {
    public FirstTimeFrame() {
        initComponents();
    }

    private void button1(ActionEvent e) {
        if(passwordField1.getPassword().length != 48){
            JOptionPane.showMessageDialog(this, "Your api key must be 48 characters long!", "Not enough characters", JOptionPane.WARNING_MESSAGE);
            passwordField1.setText("");
            return;
        }
        if((!textPane1.getText().contains("http") || !textPane1.getText().contains("https")) || textPane1.getText().length() < 10){
            textPane1.setText("");
            JOptionPane.showMessageDialog(this, "Not valid pterodactyl app url!", "Not valid url", JOptionPane.WARNING_MESSAGE);
            return;
        }

        synchronized (button1){
            button1.notify();
        }
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        apiKeyLbl = new JLabel();
        appUrlLbl = new JLabel();
        button1 = new JButton();
        passwordField1 = new JPasswordField();
        textPane1 = new JTextPane();

        //======== this ========

        //---- apiKeyLbl ----
        apiKeyLbl.setText("Enter your API key here:");

        //---- appUrlLbl ----
        appUrlLbl.setText("Enter your app url here:");

        //---- button1 ----
        button1.setText("Save settings");
        button1.addActionListener(e -> button1(e));

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(appUrlLbl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(textPane1, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(apiKeyLbl)
                            .addGap(4, 4, 4)
                            .addComponent(passwordField1, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)))
                    .addGap(63, 63, 63))
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(apiKeyLbl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(appUrlLbl, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(32, 32, 32)
                            .addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(47, 47, 47)
                    .addComponent(button1)
                    .addGap(33, 33, 33))
        );
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public JButton getButton1() {
        return button1;
    }

    public JTextPane getAppUrlBox() {
        return textPane1;
    }

    public JPasswordField getApiKeyBox() {
        return passwordField1;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel apiKeyLbl;
    private JLabel appUrlLbl;
    private JButton button1;
    private JPasswordField passwordField1;
    private JTextPane textPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
