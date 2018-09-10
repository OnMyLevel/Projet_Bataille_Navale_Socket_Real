package model;

/**
 *
 */
public class SousMarin extends Bateau {
    private boolean enplongee;
    /** Creates a new instance of SousMarin */
    public SousMarin(int x, int y, boolean p) {
        super(x,y,true,1);
        this.enplongee=p;
    }

    public void avancer(int x,int y){
        if(enplongee==false)
            super.avancer(x, y);
    }
    public void touche(int x, int y){
        if(enplongee==false)
            super.touche(x,y);
    }
    public String toString(){
        return "sousmarin"+super.toString();
    }
}
