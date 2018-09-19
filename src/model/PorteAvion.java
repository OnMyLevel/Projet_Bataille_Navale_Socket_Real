package model;

public class PorteAvion extends Bateau{
    /** Creates a new instance of Croiseur */
    public PorteAvion(int x, int y, int taille,boolean vertical) {

        super(x,y, taille, vertical);
    }
    public String toString(){
        return "Porte Avion :"+super.toString();
    }
}
