package model;

public class Croiseur extends Bateau {
    /** Creates a new instance of Croiseur */
    public Croiseur(int x, int y, int taille,boolean vertical) {
        super(x,y, taille, vertical);
    }
    public String toString(){
        return "croiseur"+super.toString();
    }
}
