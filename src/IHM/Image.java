package IHM;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

public class Image extends JPanel{

    public void SwingOpenImage(String filePath) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                JFrame frame = new JFrame(filePath);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JLabel lbl = new JLabel();
                lbl.setIcon(new ImageIcon(img));
                frame.getContentPane().add(lbl, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }
}