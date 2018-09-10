package model;

public class Croiseur extends Bateau {

    /** Creates a new instance of Croiseur */
    public Croiseur(int x, int y, boolean h) {
        super(x,y,h,3);
    }
    public String toString(){
        return "croiseur"+super.toString();
    }
}
