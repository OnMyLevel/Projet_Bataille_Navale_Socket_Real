package model;

public class Element {
    private Addresse adr;
    private String etat;

    public Element(int i, int j) {
        this.adr.setAdrLigne(i);
        this.adr.setAdrColone(j);
        etat="intact";
    }

    public Element() {
        etat="intact";
    }

    public int getabcisse(){
        return this.adr.getAdrLigne();
    }
    public int getordonnee(){
        return this.adr.getAdrColone();
    }
    public void touche(int i , int j) {
        if(this.adr.getAdrLigne()==i && this.adr.getAdrColone()==j) {
            if(etat=="intact"){
                etat="abime";
            }else if(etat=="abime")
                etat="detruit";
        }
    }
    public void avancer(int i,int j){
        if((i>=-1) && (i<=1) && (j>=-1) && (j<=1)){
            this.adr.setAdrLigne(this.adr.getAdrLigne()+i);
            this.adr.setAdrColone(this.adr.getAdrColone()+j);
        }
    }
    public String getEtat(){
        return etat;
    }
    public String toString() {
        String s;
        if(etat.compareTo("intact")==0){
            s="#";
        }else{
            if(etat=="abime"){
                s="*";
            }else{
                s=".";
            }
        }
        return s;
    }


    public void setAdresse(Addresse b) {
        this.adr = b;
    }

    public Addresse getAdresse() {
        return  this.adr;
    }

}
