package interfaces;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;


import game.Game;
import game.Joueur;
import model.Addresse;
import model.Carte;
import model.Element;
import testSocket.ClientConnexion;
import testSocket.TimeServer;

import  java.awt.event.*;

public class JoueurGUI extends JFrame implements  ActionListener{

    /**
     * Gestion du jeux
     */
    private ClientConnexion client;

    /**
     * Gestion de l'IHM
     */
    private JLabel[][] tab; // tableau de JLabels
    private JPanel panelGrille = new JPanel(); // panel du bas ( grille )
    GridLayout gridLayout1 = new GridLayout();
    private JPanel panelblanc = new JPanel();
    private JTextArea infoGame;
    private JTextArea nbAttaque;
    private  JTextArea scoreJoeur;
    private  JTextArea getCoord;
    private JButton   valider;
    private JOptionPane Info;
    private JTextArea infoGenerale;
    private  int exX;
    private  int exY;


    public JoueurGUI(String titre, int x, int y, int w, int h, Joueur clientJoueur) {

        super(titre);

        this.setLayout(new BorderLayout());
        this.initialise();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(x, y, w, h);
        this.setResizable(false);
        this.setVisible(true);
        //this.initiJoueur(clientJoueur);
    }


    public void initiSimple(){
        String host = "127.0.0.1";
        int port = 2345;
        Joueur j = new Joueur();
        this.client = new ClientConnexion(host, port,j);
        this.client.setJoueur(new Joueur());
      // his.client.setJoueur(clientJoueur);
    }

    public JoueurGUI() {

        super("BATAILLE-NAVALE-INTERFACES-JOUEUR");
        this.initiSimple();
        this.setLayout(new BorderLayout());
        this.initialise();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(800, 700, 1200, 800);
        this.setResizable(false);
        this.setVisible(true);
        this.client.run();
    }

    public void initialise() {
        this.add(this.getPanelCenter(), BorderLayout.CENTER);
        this.add(this.getPanelWeast(), BorderLayout.WEST);
        this.add(this.getPanelNorth(), BorderLayout.NORTH);
        this.add(this.getPanelSouth(), BorderLayout.SOUTH);
        this.add(this.getPanelEst(), BorderLayout.EAST);
    }


    public JPanel getPanelCenter() {

        JPanel pan = new JPanel();

        pan.setBorder(BorderFactory.createTitledBorder("Grille"));
        pan.setLayout(new GridLayout(0, 1));
        tab = new JLabel[10][10];
        panelGrille.setBounds(new Rectangle(5, 65, 550, 465));
        panelGrille.setBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.RAISED));
        panelGrille.setLayout(gridLayout1);
        gridLayout1.setColumns(this.client.getJoueur().getMapsJoueur().TAILLELIGNE);
        gridLayout1.setRows(this.client.getJoueur().getMapsJoueur().TAILLECOLONNE);

        this.getContentPane().add(panelblanc, null);
        this.getContentPane().add(panelGrille, null);

        //J'attribue la couleur aux JLabels
        for (int i = 0; i < this.client.getJoueur().getMapsJoueur().TAILLELIGNE; i++) {
            for (int j = 0; j < this.client.getJoueur().getMapsJoueur().TAILLECOLONNE; j++) {
                this.tab[i][j] = new JLabel(); // cr��ation du JLabel
                this.tab[i][j].setOpaque(true);
                this.panelGrille.add(tab[i][j]); // ajouter au Panel
                this.tab[i][j].setOpaque(true);
                this.tab[i][j].setHorizontalAlignment(SwingConstants.CENTER);  // tab[colonne][ligne].addMouseListener((MouseListener) ); // ajouter l'��couteur aux
                if(j==0 && i==0){
                    this.tab[i][j].setBackground(Color.white);
                }
                else if(i==0){
                    this.tab[i][j].setText(j+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else if(j==0){
                    this.tab[i][j].setText(i+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else{
                    if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" # ") {
                        this.tab[i][j].setText(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.red);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" * ") {
                        this.tab[i][j].setText(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.yellow);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" ! ") {
                        this.tab[i][j].setText(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.green);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else {
                        this.tab[i][j].setText(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.blue);
                        this.tab[i][j].setText(i+","+j);
                    }
                }
                // create a line border with the specified color and width
                Border border = BorderFactory.createLineBorder(Color.black, 1);
                this.tab[i][j].setBorder(border);
                //  this.tab[i][j].addMouseListener(new LabelAdapter());
            }
        }
        pan.add(panelGrille);
        return pan;
    }


    public JPanel getPanelWeast() {
        JPanel pan = new JPanel();
        pan.setBorder(BorderFactory.createTitledBorder("Infos Generale"));
        pan.setLayout(new GridLayout(0,1));
        infoGenerale = new JTextArea ( 16, 25 );
        infoGenerale.setEditable ( false ); // set textArea non-editable
        pan.add(infoGenerale);
        return pan;
    }

    public JPanel getPanelNorth() {

        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout());

        JPanel pan_score = new JPanel();
        JPanel pan_nbdepla = new JPanel();

        pan_score.setBorder(BorderFactory.createTitledBorder("Nombre d'Attaques: "));
        pan_nbdepla.setBorder(BorderFactory.createTitledBorder("Nombre Score: "));
        String s = new String("                 ");
        s += this.client.getJoueur().getScore();
        nbAttaque = new JTextArea(s);
        nbAttaque.setEditable(false);
        pan_score.add(nbAttaque);

        s = "                    ";
        s += this.client.getJoueur().getNombreAttaques();
        this.scoreJoeur = new JTextArea(s);
        this.scoreJoeur.setEditable(false);
        pan_nbdepla.add(this.scoreJoeur);

        pan.add(pan_score);
        pan.add(pan_nbdepla);

        return pan;
    }

    public JPanel getPanelSouth() {

        JPanel pan = new JPanel();
        JPanel pan_autre = new JPanel();

        pan.setBorder(BorderFactory.createTitledBorder("Attaquer "));
        pan_autre.setLayout(new FlowLayout());

        JButton bout2 = new JButton ( new ActionQuitter("Quitter"));
        JButton bout3 = new JButton ( new ActionRefresh("Refresh"));

        pan_autre.add(bout2);
        pan_autre.add(bout3);
        pan.add(pan_autre);

        return pan;
    }

    public JPanel getPanelEst() {

        JPanel pan = new JPanel();
        JPanel pan_Mscore = new JPanel();
        this.infoGame = new JTextArea();
        this.getCoord= new JTextArea();
        this.valider = new JButton(new ActionValiderPla("Valider"));

        this.getCoord.setBorder(BorderFactory.createTitledBorder("cood (X,Y)"));
        this.infoGame.setTabSize(100);
        this.infoGame.setBorder(BorderFactory.createTitledBorder("Au Tours du Joueur"));
        pan_Mscore.setLayout(new GridLayout(0,1));
        pan_Mscore.setBorder(BorderFactory.createTitledBorder("Commande Joueur"));

        this.infoGame.setVisible(true);
        this.getCoord.setVisible(true);
        this.valider.setVisible(true);

        pan_Mscore.add(this.getCoord);
        pan_Mscore.add(valider);
        pan_Mscore.add(this.infoGame);

        pan.add(pan_Mscore);

        return pan;
    }


    //Classe interne implémentant l'interface ItemListener
    class ItemState implements ItemListener{
        public void itemStateChanged(ItemEvent e) {
            System.out.println("événement déclenché sur : " + e.getItem());
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
    }

    public class ActionValiderPla extends AbstractAction {
        public ActionValiderPla(String texte) {
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {

            if (getCoord.getText() != null && getCoord.getText() != " ") {
                System.out.println("ATTAQUE COTE CLIENT");
                System.out.println(client.getCoorAttaque());
                client.setCoorAttaque(getCoord.getText());
                String[] tab = getCoord.getText().split(",");
                int x = Integer.valueOf(tab[0]);
                int y = Integer.valueOf(tab[1]);
                exX = x;
                exY = y;
                gameRerefresh();
            }
            /*if (client.getJoueur().getIdGame() == 1) {
                Info = new JOptionPane();
                Info.showMessageDialog(null,
                        " Avoue de jouer", "Information", JOptionPane.INFORMATION_MESSAGE);
                gameRerefresh();
            }
            getCoord.setText("0,0");
        }*/

            else {
                System.out.println("ICI");
            }

        }

    }

    public class ActionQuitter extends AbstractAction {
        public ActionQuitter(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Quitter");
            client.setJoueur(null);
            close();
        }
    }

    private class LabelAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel a = (JLabel ) e.getComponent();
            System.out.println(a.getText());
            gameRerefresh();
        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class ActionRefresh extends AbstractAction {
        public ActionRefresh(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Refresh");
            gameRerefresh();
            refreshJframe();
        }
    }

    public int close(){
        System.exit(0);
        return 0;
    }


    public  void refreshJframe(){
        this.gameRerefresh();
        this.refreshPanelCenter();
        this.refreshPanelNorth();
        this.refreshPanelWest();
        this.refreshPanelEst();
        this.repaint();
    }

    public void gameRerefresh(){
          System.out.println(client.getToucher()+ " lalala ");
        if(client.getToucher()==1){
            client.getJoueur().setScore(client.getJoueur().getScore()+1);
            client.getJoueur().getMapsJoueur().SuprimerE(new Addresse(exX,exY));
            client.getJoueur().getMapsJoueur().PlacerElement2(new Element(" X "),new Addresse(exX,exY));
        }if(client.getToucher()==3){
            client.getJoueur().setScore(client.getJoueur().getScore()+3);
           // switch (client.)
            client.getJoueur().getMapsJoueur().SuprimerE(new Addresse(exX,exY));
            client.getJoueur().getMapsJoueur().PlacerElement2(new Element(" ! "),new Addresse(exX,exY));
        }
    }

    private void refreshPanelNorth() {
        this.scoreJoeur.setText("Nombre d'attaque: "+this.client.getJoueur().getNombreAttaques());
        this.nbAttaque.setText("Votre Score: "+this.client.getJoueur().getScore());
        this.infoGenerale.setText(this.client.getJoueur().toString());
    }


    private void refreshPanelWest() {
        this.infoGenerale.setText(this.client.getJoueur().toString());
        this.infoGenerale.setText(this.client.getRetourAttaque());
    }

    private void refreshPanelEst() {
        this.infoGame.setText(this.client.getJoueur().toString());
    }

    public void refreshPanelCenter() {

        for (int i = 0; i < this.client.getJoueur().getMapsJoueur().TAILLELIGNE; i++) {
            for (int j = 0; j < this.client.getJoueur().getMapsJoueur().TAILLECOLONNE; j++) {
                //this.tab[i][j] = new JLabel(); // cr��ation du JLabel
                this.tab[i][j].setOpaque(true);
                this.panelGrille.add(tab[i][j]); // ajouter au Panel
                this.tab[i][j].setOpaque(true);
                this.tab[i][j].setHorizontalAlignment(SwingConstants.CENTER); // pour
                // tab[colonne][ligne].addMouseListener((MouseListener) ); // ajouter l'��couteur aux
                if(j==0 && i==0){
                    this.tab[i][j].setBackground(Color.white);
                }
                else if(i==0){
                    this.tab[i][j].setText(j+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else if(j==0){
                    this.tab[i][j].setText(i+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else{
                    if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" # ") {
                        //this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.red);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" * ") {
                        // this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.red);
                        this.tab[i][j].setText(i+","+j);

                    }  else if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" ! ") {
                        // this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.green);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString()==" X ") {
                        // this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.yellow);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else {
                        this.tab[i][j].setText(this.client.getJoueur().getMapsJoueur().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.blue);
                        this.tab[i][j].setText(i+","+j);
                    }
                }
                // create a line border with the specified color and width
                Border border = BorderFactory.createLineBorder(Color.black, 1);
                this.tab[i][j].setBorder(border);
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JoueurGUI a = new JoueurGUI();
    }
}



