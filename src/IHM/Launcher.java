package IHM;

import reseau.Server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

public class Launcher {

    private JFrame fenetreLancement = new JFrame();
    private JFrame lancement2 = new JFrame();
    private static Launcher nv;
    private JButton quitterFenetre;
    private JButton jouer;
    private JTextField id;
    private JPasswordField pw;
    private Server server;


    public Launcher() {

        //Taille de la fenetre
        fenetreLancement.setSize(900, 650);

        //Enlever contour de la fen�tre
        fenetreLancement.setUndecorated(true);


        fenetreLancement.setLocationRelativeTo(null);

        //Vider le layout de la fen�tre
        fenetreLancement.setLayout(null);

        //Changement du fond de couleur de la fen�tre
        fenetreLancement.getContentPane().setBackground(new Color(253,245,230));

        id = new JTextField("ID");
        id.setBounds(400,200,100,40);
        fenetreLancement.add(id);

        pw = new JPasswordField("Mot de passe");
        pw.setBounds(400,250,100,40);
        fenetreLancement.add(pw);

        quitterFenetre = new JButton("Quitter");
        quitterFenetre.setBounds(400,400, 100, 40);
        quitterFenetre.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
        });
        jouer = new JButton("Jouer");
        jouer.setBounds(400,300, 100, 40);
        jouer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //System.exit(1);

                lancement2.setVisible(true);
                fenetreLancement.setVisible(false);
            }
        });
        fenetreLancement.add(quitterFenetre);
        fenetreLancement.add(jouer);

        //TRES IMPORTANT A NE PAS OUBLIER A LA FIN DU TRAITEMENT
        //AFFICHER LA FENETRE
        fenetreLancement.setVisible(true);
        int port = 1500;
        this.server = new Server(port);

    }

    public void ActionPerformed(ActionEvent e) {

        if(e.getSource() == quitterFenetre) {
            System.exit(1);
        }

        else if (e.getSource() == jouer) {
//afficher une autre fenetre apres avoir fait jouer et ferme la premiere fenetre
            final JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setVisible(true);
           // server.start();
        }

    }

    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }catch(Exception em) {
            em.printStackTrace();
        }

        nv = new Launcher();

        new SwingOpenImage("C:\\Users\\rahma\\Documents\\ESIEAA\\4A\\bataille.jpg");

    }
}
