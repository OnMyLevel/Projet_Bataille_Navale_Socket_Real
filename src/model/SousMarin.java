package model;

/**
 *
 */
public class SousMarin extends Bateau {

    /** Creates a new instance of SousMarin */
    public SousMarin(int x, int y,int taille, boolean vertical) {
        super(x,y,taille,vertical);
        System.out.println("2 \n");
    }

    public void avancer(int x,int y){
        super.avancer(x, y);
    }
    public void touche(int x, int y){
            super.touche(x,y);
    }
    public String toString(){
        return "sousmarin"+super.toString();
    }
}
