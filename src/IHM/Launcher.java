package IHM;
import interfaces.AdminGUI;
import interfaces.JoueurGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class Launcher implements ImageObserver {

    private AdminGUI lancement2;
    private JoueurGUI lancement3;
    private static Launcher nv;
    private JButton quitterFenetre;
    private JButton jouer;
    private JTextField id;
    private JPasswordField pw;
    private ImagePanel panelNorth;
    private Graphics g;

    private JFrame fenetreLancement;
    private String username;
    private String password;

    public JFrame getFenetreLancement() {
        return fenetreLancement;
    }

    public void setFenetreLancement(JFrame fenetreLancement) {
        this.fenetreLancement = fenetreLancement;
    }

    public JFrame getLancement2() {
        return lancement2;
    }
    public JFrame getLancement3() {
        return lancement3;
    }
    public void setLancement2(
            AdminGUI lancement2) {
        this.lancement2 = lancement2;
    }
    public void setLancement3(JoueurGUI lancement3) {
        this.lancement3 = lancement3;
    }
    public static Launcher getNv() {
        return nv;
    }
    public static void setNv(Launcher nv) {
        Launcher.nv = nv;
    }
    public JButton getQuitterFenetre() {
        return quitterFenetre;
    }
    public void setQuitterFenetre(JButton quitterFenetre) {
        this.quitterFenetre = quitterFenetre;
    }
    public JButton getJouer() {
        return jouer;
    }
    public void setJouer(JButton jouer) {
        this.jouer = jouer;
    }
    public JTextField getId() {
        return id;
    }
    public void setId(JTextField id) {
        this.id = id;
    }
    public JPasswordField getPw() {
        return pw;
    }
    public void setPw(JPasswordField pw) {
        this.pw = pw;
    }


    public void setPanelNorth(ImagePanel panelNorth) {
        this.panelNorth = panelNorth;
    }

    public static JPanel setBackgroundImage(JFrame frame, final File img) throws IOException {
        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1;
            private BufferedImage buf = ImageIO.read(img);
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g); g.drawImage(buf, 0,0, null);
            }
        };
        frame.setContentPane(panel);
        return panel;
    }

    public Launcher() throws IOException {

        this.setFenetreLancement(new JFrame());
        //Taille de la fenetre
        fenetreLancement.setSize(840, 296);

        //Enlever contour de la fen�tre
        fenetreLancement.setUndecorated(true);


        fenetreLancement.setLocationRelativeTo(null);

        //Vider le layout de la fen�tre
        fenetreLancement.setLayout(null);

        //Changement du fond de couleur de la fen�tre
        //fenetreLancement.getContentPane().setBackground(new Color(253,245,230));

        // this.getFenetreLancement().add(this.getPanelNorth());
        this.setBackgroundImage(this.fenetreLancement, new File("C://Users/rahma/Documents/ESIEAA/4A/Projet_Bataille_Navale_Socket_Real/img/bataille.jpg"));



        ///s�paration :
        //this.getFenetreLancement().setBackground(bgColor)d
        id = new JTextField("          ID         ");
        //id.setBounds(400,200,100,40);
        fenetreLancement.add(id);

        pw = new JPasswordField("Mot de passe");
        //pw.setBounds(400,250,100,40);
        fenetreLancement.add(pw);

        quitterFenetre = new JButton("Quitter");
        quitterFenetre.setBounds(400,400, 100, 40);
        quitterFenetre.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(1);
            }
        });
        jouer = new JButton("Jouer !");
        jouer.setBounds(400,300, 100, 40);
        jouer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                username = id.getText();
                char[] mdpTab = pw.getPassword();
                password = new String(mdpTab);
                boolean test= false;

                test = checkId(username, password);
                System.out.println(test);
                if(test==true) {

                    lancement2 = new AdminGUI();
                    //System.exit(1);
                }
                else {
                    lancement3 = new JoueurGUI();
                    //System.exit(1);
                }

            }
        });
        fenetreLancement.add(quitterFenetre);
        fenetreLancement.add(jouer);

        //TRES IMPORTANT A NE PAS OUBLIER A LA FIN DU TRAITEMENT
        //AFFICHER LA FENETRE
        fenetreLancement.setVisible(true);
    }

    private boolean checkId(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        if	(username.equals("admin") && password.equals("admin")) {

            return true;//lancer fenetre admin
        }
        else
        {
            return false; //lance fenetre joueur
        }

    }

    public static void main(String[] args) throws IOException {


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException e) {

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        nv = new Launcher();
    }

    @Override
    public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        return false;
    }

    public Graphics getG() {
        return g;
    }

    public void setG(Graphics g) {
        this.g = g;
    }
}
