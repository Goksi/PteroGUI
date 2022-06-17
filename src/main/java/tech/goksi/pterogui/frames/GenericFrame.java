package tech.goksi.pterogui.frames;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GenericFrame extends JFrame {
    public GenericFrame(String name, JPanel contentPane, Component location){
        super(name);
        setContentPane(contentPane);
        setResizable(false);
        pack();
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/cool.png"))).getImage());
        setLocationRelativeTo(location);
    }
}
