package io.github.kuugasky.kuugatool.core.jdk17;

import javax.swing.*;
import java.awt.*;

/**
 * JEP392
 *
 * @author kuuga
 * @since 2022/8/9 09:01
 */
public class JEP392 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello World Java Swing");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel lblText = new JLabel("Hello World!", SwingConstants.CENTER);
        frame.getContentPane().add(lblText);
        frame.pack();
        frame.setVisible(true);

    }

}
