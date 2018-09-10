package model;

public class Escorteur extends Bateau{
    /** Creates a new instance of Escorteur */
    public Escorteur(int x, int y, boolean h) {
        super(x,y,h,2);
    }
    public String toString(){
        return "escorteur"+super.toString();
    }

}
