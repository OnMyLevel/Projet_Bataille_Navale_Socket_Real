package model;

public class Element {
    private Addresse adr;
    private String etat;

    public Element(int i, int j) {
        System.out.println("2-1 \n");
        this.adr = new Addresse(i,j);
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
    public boolean touche(int i , int j) {
        boolean a= false;
        if(this.adr.getAdrLigne()==i && this.adr.getAdrColone()==j) {
            if(etat=="intact"){
                etat="abime";
                a=true;
            }else if(etat=="abime")
                etat="detruit";
                a=true;
        }
        return a;
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

    public void setEtat(String a){
        etat=a;
    }

    public String toString() {
        String s;
        if(etat.compareTo("intact")==0){
            s=" # ";
        }else{
            if(etat=="abime"){
                s=" * ";
            }else{
                s=" . ";
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
