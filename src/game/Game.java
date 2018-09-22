package game;

import model.Flotte;

import java.util.ArrayList;

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

    public void partie(){
        System.out.println("Je suis dans partie");
    }


}
