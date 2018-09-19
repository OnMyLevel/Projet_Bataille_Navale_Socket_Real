package game;

import model.Addresse;
import model.Carte;
import model.Element;
import model.Flotte;

import java.util.ArrayDeque;
import java.util.Scanner;

public class GameLocal {
    private  Joueur simplJoueur;
    private  Flotte flotte;
    private int score;

    public Joueur getSimplJoueur() {
        return simplJoueur;
    }

    public void setSimplJoueur(Joueur simplJoueur) {
        this.simplJoueur = simplJoueur;
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

    public GameLocal(Joueur simplJoueur, Flotte flotte) {
        this.simplJoueur = simplJoueur;
        this.flotte = flotte;
    }

    public GameLocal(Joueur simplJoueur, Flotte flotte, int score) {
        this.simplJoueur = simplJoueur;
        this.flotte = flotte;
        this.score = score;
    }

    public GameLocal() {
        this.simplJoueur = new Joueur("Meril","meril");
        this.flotte =new Flotte();
        this.score = 0;
    }

    public Addresse recupAdr(){
        int x=0;
        int y=0;
        Scanner sc = new Scanner(System.in);
        System.out.println(" Rentrez l'abcisse ");
        x = sc.nextInt();
        System.out.println(" Rentrez l'ordonées ");
        y = sc.nextInt();
        System.out.println(" Votre cyble est la case : {"+x+","+y+"}");
        return  new Addresse(x,y);
    }

    public boolean lanceAttaque(){
        return this.flotte.adrToucher(this.recupAdr());
    }




    public int  partie(){
        System.out.println(" Bienvenue sur la partie ");
        System.out.println(this.simplJoueur);
        this.lanceAttaque();
        while(this.flotte.finDeLaFlotte() != false5){
            boolean test=false;
            test=lanceAttaque();
            if(test){
                System.out.println("Vous avez toucher un  bateau ");
                this.flotte.afficheCarte();
            }else{
                System.out.println("Vous avez pas toucher de bateau");
            }
            this.lanceAttaque();
        }
        return 0;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        GameLocal a = new GameLocal();
        a.partie();

    }


}
