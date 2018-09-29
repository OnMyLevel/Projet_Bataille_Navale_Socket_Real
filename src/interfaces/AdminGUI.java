package interfaces;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;


import game.Game;
import model.Addresse;
import testSocket.TimeServer;

import  java.awt.event.*;

public class AdminGUI extends JFrame implements  ActionListener{

    /**
     * Gestion du jeux
     */
    private TimeServer server;

    /**
     * Gestion de l'IHM
     */
    private JTextArea ta  = new JTextArea();
    private JTextArea tb;
    private JLabel[][] tab; // tableau de JLabels
    private JPanel panelGrille = new JPanel(); // panel du bas ( grille )
    GridLayout gridLayout1 = new GridLayout();
    private JPanel panelblanc = new JPanel();
    private  JButton placementAl;
    private  JButton  placementMan;
    private  JComboBox comboBox;
    private JTextArea infoGame;
    private JTextArea NbBateaux;
    private  JTextArea NbJoeur;
    private JRadioButton vertical;
    private  JTextArea getCoord;
    private JButton   valider;
    private JOptionPane Info;
    private JButton   lancerPartie;
    private JTextArea infoGenerale;


    public AdminGUI(String titre, int x, int y, int w, int h, Game moteurJeu) {

        super(titre);

        this.setLayout(new BorderLayout());
        this.initialise();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(x, y, w, h);
        this.setResizable(false);
        this.setVisible(true);
        //this.getPanelCenter().getAccessibleContext().getAccessibleChild(1).
    }

    public void initGame(){
        this.server = new TimeServer();
        this.server.open();
        this.server.setRealGame( new  Game());
    }

    public AdminGUI() {

        super("BATAILLE NAVALE");
        this.initGame();
        this.setLayout(new BorderLayout());
        this.initialise();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(800, 700, 1200, 800);
        this.setResizable(false);
        this.setVisible(true);
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
        gridLayout1.setColumns(this.server.getRealGame().getFlotte().getMaps().TAILLELIGNE);
        gridLayout1.setRows(this.server.getRealGame().getFlotte().getMaps().TAILLECOLONNE);

        this.getContentPane().add(panelblanc, null);
        this.getContentPane().add(panelGrille, null);

        //J'attribue la couleur aux JLabels
        for (int i = 0; i < this.server.getRealGame().getFlotte().getMaps().TAILLELIGNE; i++) {
            for (int j = 0; j < this.server.getRealGame().getFlotte().getMaps().TAILLECOLONNE; j++) {
                this.tab[i][j] = new JLabel(); // cr��ation du JLabel
                this.tab[i][j].setOpaque(true);
                this.panelGrille.add(tab[i][j]); // ajouter au Panel
                this.tab[i][j].setOpaque(true);
                this.tab[i][j].setHorizontalAlignment(SwingConstants.CENTER); // pour
                // tab[colonne][ligne].addMouseListener((MouseListener) ); // ajouter l'��couteur aux
                if(j==0 && i==0){
                    this.tab[i][j].setBackground(Color.white);
                }
                /*
                else if(i==0){
                    this.tab[i][j].setText(j+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else if(j==0){
                    this.tab[i][j].setText(i+"");
                    this.tab[i][j].setBackground(Color.white);
                }*/
                else{
                    if(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString()==" # ") {
                        this.tab[i][j].setText(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.red);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString()==" * ") {
                        this.tab[i][j].setText(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setBackground(Color.magenta);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else {
                        this.tab[i][j].setText(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
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
        JScrollPane scroll = new JScrollPane ( infoGenerale );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
        pan.add(scroll);

        return pan;
    }


    public JPanel getPanelNorth() {

        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout());

        JPanel pan_score = new JPanel();
        JPanel pan_nbdepla = new JPanel();

        pan_score.setBorder(BorderFactory.createTitledBorder("Nombre Bateaux: "));
        pan_nbdepla.setBorder(BorderFactory.createTitledBorder("Nombre Joueurs: "));
        String s = new String("                 ");
        s += this.server.getRealGame().getScore();
        NbBateaux = new JTextArea(s);
        NbBateaux.setEditable(false);
        pan_score.add( NbBateaux);

        s = "                      ";
        s += this.server.getRealGame().infoGame();
        this.NbJoeur= new JTextArea(s);
        this.NbJoeur.setEditable(false);
        pan_nbdepla.add(this.NbJoeur);

        pan.add(pan_score);
        pan.add(pan_nbdepla);

        return pan;
    }

    public JPanel getPanelSouth() {

        JPanel pan = new JPanel();
        JPanel pan_autre = new JPanel();

        pan.setBorder(BorderFactory.createTitledBorder("Autre commande "));
        pan_autre.setLayout(new FlowLayout());

        JButton bout1 = new JButton ( new ActionRestart("Restart"));
        JButton bout2 = new JButton ( new ActionQuitter("Quitter"));
        JButton bout3 = new JButton ( new ActionRefresh("Refresh"));
        lancerPartie = new JButton ( new ActionLancer("LancerPartie"));

        lancerPartie.setEnabled(false);
        pan_autre.add(bout1);
        pan_autre.add(bout2);
        pan_autre.add(bout3);
        pan_autre.add(lancerPartie);
        pan.add(pan_autre);

        return pan;
    }

    public JPanel getPanelEst() {

        JPanel pan = new JPanel();
        JPanel pan_Mscore = new JPanel();
        this.infoGame = new JTextArea();
        this.getCoord= new JTextArea();
        this.valider = new JButton(new ActionValiderPla("Valider"));
        this.vertical = new JRadioButton("Vertical");
        this.placementAl = new JButton( new ActionPlaceAle("Placement Ale"));
        this.placementMan = new JButton( new ActionPlaceMan("Placement Man"));

        this.placementAl.setSize(new Dimension(100,5));
        this.placementMan.setSize(new Dimension(100,5));
        this.getCoord.setBorder(BorderFactory.createTitledBorder("cood (X,Y)"));
        this.infoGame.setTabSize(100);
        this.infoGame.setBorder(BorderFactory.createTitledBorder("Au Tours du Joueur"));
        pan_Mscore.setLayout(new GridLayout(0,1));
        pan_Mscore.setBorder(BorderFactory.createTitledBorder("Commande Admin "));

        this.placementAl.setVisible(true);
        this.placementMan.setVisible(true);
        initComboBox();
        this.infoGame.setVisible(true);
        this.getCoord.setVisible(false);
        this.vertical.setVisible(false);
        this.valider.setVisible(false);

        pan_Mscore.add(placementAl);
        pan_Mscore.add(placementMan);
        pan_Mscore.add(this.getCoord);
        pan_Mscore.add(this.comboBox);
        pan_Mscore.add(this.vertical);
        pan_Mscore.add(valider);
        pan_Mscore.add(this.infoGame);

        pan.add(pan_Mscore);

        return pan;
    }

    private void initComboBox() {
        this.comboBox = new JComboBox();
       // this.comboBox.setPreferredSize(new Dimension(100, 20));
        this.comboBox.addItem("SousMarin");
        this.comboBox.addItem("Torpilleur");
        this.comboBox.addItem("Croiseur");
        this.comboBox.addItem("Cuirrasse");
        this.comboBox.addItem("PorteAvion");

        //Ajout du listener
        this.comboBox.addItemListener(new ItemState());
        this.comboBox.setPreferredSize(new Dimension(100, 20));
        this.comboBox.setForeground(Color.BLACK);
        this.comboBox.setVisible(false);
        this.setVisible(false);
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

    public class ActionPlaceAle extends AbstractAction {
        public ActionPlaceAle(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Placement Ale");
            server.getRealGame().placeAle();
            placementAl.setEnabled(false);
            placementMan.setEnabled(false);

            if (server.getRealGame().getFlotte().getNombreBateau() > 4) {
                //Boîte du message d'information
                Info = new JOptionPane();
                Info.showMessageDialog(null,
                        "Vous avez assez de bateau pour lancer la partie", "Information", JOptionPane.INFORMATION_MESSAGE);
                refreshJframe();
            }
            refreshJframe();
        }
    }
    public class ActionValiderPla extends AbstractAction {
        public ActionValiderPla(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Valider Placement");
            if (getCoord.toString() != null) {

                String[] a = getCoord.getText().split(",");
                System.out.println(a[0].toString());
                int x = Integer.valueOf(a[0]);
                int y = Integer.valueOf(a[1]);

                System.out.println(x + "," + y);

                if (vertical.isValid()) {
                    switch (comboBox.getSelectedIndex()) {
                        case 0:
                            System.out.println("Case1: Value is: 1 ");
                            server.getRealGame().getFlotte().placeSousmarin(new Addresse(x, y), true);
                        case 1:
                            System.out.println("Case2: Value is: 2 ");
                            server.getRealGame().getFlotte().placeTorpilleur(new Addresse(x, y), true);
                        case 2:
                            System.out.println("Case3: Value is: 3");
                            server.getRealGame().getFlotte().placeCroiseur(new Addresse(x, y), true);
                        case 3:
                            System.out.println("Case3: Value is: 4");
                            server.getRealGame().getFlotte().placeCuirasse(new Addresse(x, y), true);
                        case 4:
                            System.out.println("Case3: Value is: 5");
                            server.getRealGame().getFlotte().placePorteAvion(new Addresse(x, y), true);
                        default:
                            System.out.println("False ");
                    }
                } else {
                    switch (comboBox.getSelectedIndex()) {
                        case 0:
                            System.out.println("Case1: Value is: 1 ");
                            server.getRealGame().getFlotte().placeSousmarin(new Addresse(x, y), false);
                        case 1:
                            System.out.println("Case2: Value is: 2 ");
                            server.getRealGame().getFlotte().placeTorpilleur(new Addresse(x, y), false);
                        case 2:
                            System.out.println("Case3: Value is: 3");
                            server.getRealGame().getFlotte().placeCroiseur(new Addresse(x, y), false);
                        case 3:
                            System.out.println("Case3: Value is: 4");
                            server.getRealGame().getFlotte().placeCuirasse(new Addresse(x, y), false);
                        case 4:
                            System.out.println("Case3: Value is: 5");
                            server.getRealGame().getFlotte().placePorteAvion(new Addresse(x, y), false);
                        default:
                            System.out.println("False ");
                    }

                }
               gameRerefresh();
            }

            if (server.getRealGame().getFlotte().getNombreBateau() > 5) {
                valider.setEnabled(false);
                //Boîte du message d'information
                Info = new JOptionPane();
                Info.showMessageDialog(null,
                        "Le nombre Maximum de bateau est atteint ", "Information", JOptionPane.INFORMATION_MESSAGE);
                gameRerefresh();
            }
        }
    }

    public class ActionPlaceMan extends AbstractAction {
        public ActionPlaceMan(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Placement Ale");
            placementMan.setEnabled(false);
            placementAl.setEnabled(false);
            getCoord.setVisible(true);
            vertical.setVisible(true);
            valider.setVisible(true);
            comboBox.setVisible(true);
            gameRerefresh();
            refreshJframe();
        }
    }

    public class ActionQuitter extends AbstractAction {
        public ActionQuitter(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Quitter");
            server.setRealGame(null);
            close();
        }
    }

    public class ActionLancer extends AbstractAction {
        public ActionLancer(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Lancer");
            gameRerefresh();
            infoGenerale.setText(infoGenerale.getText()+"\n ********Lancement de la partie********");
        }
    }

    private void lancerPartie() {
        System.out.println("Lancement de la partie");
        gameRerefresh();
    }

    private class LabelAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel a = (JLabel ) e.getComponent();
            System.out.println(a.getText());
            gameRerefresh();
            nombreDejoueursChange();
        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class ActionRestart extends AbstractAction {
        public ActionRestart(String texte){
            super(texte);
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Restart");
            server.setRealGame(new Game());
            placementAl.setEnabled(true);
            placementMan.setEnabled(true);
            comboBox.setVisible(false);
            gameRerefresh();
            refreshJframe();
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
       this.refreshPanelCenter();
       this.refreshPanelNorth();
       this.refreshPanelSouth();
       this.refreshPanelWest();
       this.refreshPanelEst();
       this.gameRerefresh();;
       this.repaint();
    }

    public void gameRerefresh(){
        nombreDejoueursChange();
        LancerPartie();
    }

    private void refreshPanelNorth() {
        this.NbJoeur.setText("Nombre de joueurs: "+this.server.getRealGame().getJoueurs().size());
        this.NbBateaux.setText("Nombre de Bateaux: "+this.server.getRealGame().getFlotte().getNombreBateau());
        this.infoGenerale.setText(this.server.getRealGame().infoGame());
    }

    private void refreshPanelSouth() {
    }

    public void LancerPartie(){
        if(this.server.getRealGame().getFlotte().getNombreBateau() >4 && this.server.getRealGame().getJoueurs().size() >1){
            lancerPartie.setEnabled(true);
        }
    }

    private void refreshPanelWest() {
    }

    private void refreshPanelEst() {
        this.infoGame.setText(this.server.getRealGame().infoGame());
    }

    public  void  nombreDejoueursChange(){
        if(this.server.getRealGame().getJoueurs().size() > this.server.getRealGame().getAncienNombreJoueur()){
            Info = new JOptionPane();
            Info.showMessageDialog(null,
                    this.server.getRealGame().getJoueurs().get(this.server.getRealGame().getAncienNombreJoueur()+1)+"A rejoint la partie", "Information", JOptionPane.INFORMATION_MESSAGE);
         this.server.getRealGame().setProchainJoueur(this.server.getRealGame().getJoueurs().size());
        }
    }

    public void refreshPanelCenter() {

        for (int i = 0; i < this.server.getRealGame().getFlotte().getMaps().TAILLELIGNE; i++) {
            for (int j = 0; j < this.server.getRealGame().getFlotte().getMaps().TAILLECOLONNE; j++) {
                //this.tab[i][j] = new JLabel(); // cr��ation du JLabel
                this.tab[i][j].setOpaque(true);
                this.panelGrille.add(tab[i][j]); // ajouter au Panel
                this.tab[i][j].setOpaque(true);
                this.tab[i][j].setHorizontalAlignment(SwingConstants.CENTER); // pour
                // tab[colonne][ligne].addMouseListener((MouseListener) ); // ajouter l'��couteur aux
                if(j==0 && i==0){
                    this.tab[i][j].setText(i+"");
                    this.tab[i][j].setBackground(Color.white);
                }
                else{
                    if(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString()==" # ") {
                        //this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.red);
                        this.tab[i][j].setText(i+","+j);
                    }
                    else if(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString()==" * ") {
                       // this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.magenta);
                        this.tab[i][j].setText(i+","+j);

                    }  else if(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString()==" ! ") {
                        // this.tab[i][j].setText(moteurJeu.getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
                        this.tab[i][j].setText(" ");
                        this.tab[i][j].setBackground(Color.black);
                        //this.tab[i][j].setText(i+","+j);
                    }
                    else {
                        this.tab[i][j].setText(this.server.getRealGame().getFlotte().getMaps().getElementT(new Addresse(i, j)).toString());
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
        AdminGUI a = new AdminGUI();
    }
}



