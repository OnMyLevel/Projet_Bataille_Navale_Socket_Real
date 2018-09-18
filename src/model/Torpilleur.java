package model;

public class Torpilleur extends  Bateau{
    /** Creates a new instance of Cuirasse */
    public Torpilleur(int x, int y, int taille, boolean vertical) {

        super(x,y,taille,vertical);
    }
    public String toString(){

        return "Torpilleur : "+super.toString();
    }

}
