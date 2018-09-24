package game;

import model.Addresse;
import model.Flotte;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Joueur> joueurs;
    private Flotte flotte;
    private int score;

    @Override
    public String toString() {
        return "Game{" +
                "joueurs=" + joueurs +
                ", flotte=" + flotte +
                ", score=" + score +
                '}';
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Flotte getFlotte() {
        return flotte;
    }

    public void setFlotte(Flotte flotte) {
        this.flotte = flotte;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Game() {
        this.flotte = new Flotte();
        this.joueurs = new ArrayList<Joueur>();
        this.score = 0;
    }

    public Game(ArrayList<Joueur> joueurs, Flotte flotte, int score) {
        this.joueurs = joueurs;
        this.flotte = flotte;
        this.score = score;
    }

    public boolean placeAle(){

        return this.getFlotte().placeBateauAle();
    }

    public Addresse recupAdr(){
        int x=0;
        int y=0;
        Scanner sc = new Scanner(System.in);
        System.out.println(" Rentrez l'abcisse. \n ");
        x = sc.nextInt();
        System.out.println(" Rentrez l'ordonée.\n ");
        y = sc.nextInt();
        return  new Addresse(x,y);
    }

    public boolean lanceAttaque(Addresse a) {
        return this.flotte.adrToucher(a);
    }

    public boolean placeManuel(){
        int x=0;
        boolean bienFait= false;
        System.out.println(" Quel bateau souhaiter placer ? \n ");
        System.out.println(" - Tapez 1 pour SousMarin \n ");
        System.out.println(" - Tapez 2 pour Torppilleur\n ");
        System.out.println(" - Tapez 3 pour Croiseur \n ");
        System.out.println(" - Tapez 4 pour Cuirasse \n ");
        System.out.println(" - Tapez 5 pour PorteAvillons \n ");
        System.out.println(" - Tapez 0 pour quitter ! \n ");
        Scanner sc = new Scanner(System.in);
        x = sc.nextInt();
        boolean test = false;
        int a=0;
        switch(x)
        {
            case 1:
                test =false;
                System.out.println(" Placement de SousMarin ? \n ");
                System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                x = sc.nextInt();
                if(x==1){
                    test=this.getFlotte().placeSousmarin(recupAdr(),true);
                }else{
                    test=this.getFlotte().placeSousmarin(recupAdr(),false);
                }
                while (!test){
                    System.out.println(" Réssayer le placement! \n ");
                    System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                    x = sc.nextInt();
                    if(x==1){
                        test=this.getFlotte().placeSousmarin(recupAdr(),true);
                    }else{
                        test=this.getFlotte().placeSousmarin(recupAdr(),false);
                    }
                }
                this.flotte.afficheCarte();
                return  true;
            case 2:
                test=false;
                System.out.println(" Placement de Torpilleur !  \n ");
                System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                x = sc.nextInt();
                if(x==1){
                    test=this.getFlotte().placeTorpilleur(recupAdr(),true);
                }else{
                    test=this.getFlotte().placeTorpilleur(recupAdr(),false);
                }
                while (!test){
                    System.out.println(" Réssayer le placement! \n ");
                    System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                    x = sc.nextInt();
                    if(x==1){
                        test=this.getFlotte().placeTorpilleur(recupAdr(),true);
                    }else{
                        test=this.getFlotte().placeTorpilleur(recupAdr(),false);
                    }
                }
                this.flotte.afficheCarte();
                return  true;
            case 3:
                test =false;
                System.out.println(" Placement de Croiseur ! \n ");
                System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                x = sc.nextInt();
                if(x==1){
                    test=this.getFlotte().placeCroiseur(recupAdr(),true);
                }else{
                    test=this.getFlotte().placeCroiseur(recupAdr(),false);
                }
                while (!test){
                    System.out.println(" Réssayer le placement! \n ");
                    System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                    x = sc.nextInt();
                    if(x>1){
                        test=this.getFlotte().placeCroiseur(recupAdr(),true);
                    }else{
                        test=this.getFlotte().placeCroiseur(recupAdr(),false);
                    }
                }
                this.flotte.afficheCarte();
                return  true;
            case 4:
                test = false;
                System.out.println(" Placement de Cuirasse ! \n ");
                System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                x = sc.nextInt();
                if(x==1){
                    test=this.getFlotte().placeCuirasse(recupAdr(),true);
                }else{
                    test=this.getFlotte().placeCuirasse(recupAdr(),false);
                }
                while (!test){
                    System.out.println(" Réssayer le placement! \n ");
                    System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                    x = sc.nextInt();
                    if(x==1){
                        test=this.getFlotte().placeCuirasse(recupAdr(),true);
                    }else{
                        test=this.getFlotte().placeCuirasse(recupAdr(),false);
                    }
                }
                this.flotte.afficheCarte();
                return  true;
            case 5:
                test = false;
                System.out.println(" Placement de PorteAvion ? \n ");
                System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                x = sc.nextInt();
                if(x==1){
                    test=this.getFlotte().placePorteAvion(recupAdr(),true);
                }else{
                    test=this.getFlotte().placePorteAvion(recupAdr(),false);
                }
                while (!test){
                    System.out.println(" Réssayer le placement! \n ");
                    System.out.println(" Placement vertical - 1 ou horisental - 2 ? \n ");
                    x = sc.nextInt();
                    if(x==1){
                        test=this.getFlotte().placePorteAvion(recupAdr(),true);
                    }else{
                        test=this.getFlotte().placePorteAvion(recupAdr(),false);
                    }
                }
                this.flotte.afficheCarte();
                return  true;
            default:
                System.out.println(" Fin du Manageur\n ");
                return  false;
        }
    }

    public boolean PlacementBateau(){
        int x=0;
        boolean bientFait=false;
        System.out.println(" Souhaiter vous placer les Bateaux aumomatique ou manuellement ? \n ");
        System.out.println(" - Tapez 1 pour automatique \n ");
        System.out.println(" - Tapez 2 pour manuellement \n ");
        Scanner sc = new Scanner(System.in);
        x = sc.nextInt();
        if(x==1){
            System.out.println(" Placement automatique lancer. \n ");
            bientFait=this.placeAle();
        }
        else {
            System.out.println(" Placement Manuel \n ");
            bientFait=this.placeManuel();
        }
        return  bientFait;
    }



    public int  partie(){
        System.out.println(" Bienvenue sur la partie \n ");
        System.out.println(this.getJoueurs().toString());
        if(this.PlacementBateau()) {
            System.out.println(" Lancement de la Partie \n ");
            while (this.flotte.finDeLaFlotte() == false) {
                boolean point;
                for(int i=0;i< this.joueurs.size();i++){
                    point = this.lanceAttaque(this.joueurs.get(i).lanceAttaque());
                    if(point){
                        this.getJoueurs().get(i).setScore( this.getJoueurs().get(i).getScore()+1);
                    }
                }
                System.out.print('\u000C');
            }
        }
        return 0;
    }

    public void infoGame(){
        System.out.println("Actuellement \n ");
        System.out.println(this.getJoueurs().toString());
        System.out.println("Il reste : " + this.getFlotte().getNombreBateau() + " Bateaux");
        System.out.println(this.getFlotte().afficheFlotte().toString());
        this.flotte.afficheCarte();
    }


}
