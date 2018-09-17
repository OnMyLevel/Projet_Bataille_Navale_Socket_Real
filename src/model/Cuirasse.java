package model;

public class Cuirasse extends Bateau{
    /** Creates a new instance of Cuirasse */
    public Cuirasse(int x, int y, int taille, boolean vertical) {

        super(x,y,taille,vertical);
    }
    public String toString(){
        return "escorteur"+super.toString();
    }

}
