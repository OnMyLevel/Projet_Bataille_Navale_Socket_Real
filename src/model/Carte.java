package model;

import java.util.Random;
public class Carte {

    private String nomc;
    private Object  tab[][];
    public static final int TAILLECOLONNE =10;
    public static final int TAILLELIGNE =10;

    public static final Addresse EXBHORYSENTAL =new Addresse(0,9);
    public static final Addresse EXAVERTICAL = new Addresse(9,1);

    private String s=" . ";
    private Random random = new Random();

    public void setNonC(String c){
        this.nomc=c;
    }

    public String getNomc(){
        return this.nomc;
    }

    public void setTabE(Object tab2[][]){
        this.tab=tab2;

    }
    public  Object[][] geTab(){
        return this.tab;
    }

    public  String getS(){
        return   this.s;
    }

    public void  setElementT(Object a,Addresse b)
    {
        this.tab[b.getAdrLigne()][b.getAdrColone()]=a ;
        if (a.toString()=="#"){
            ((Element) a).setAdresse(b);
        }
    }

    public Object getElementT(Addresse a ){
        Object b= new Object();

        if( (a.getAdrLigne()> 0 &&  a.getAdrLigne()<(TAILLELIGNE)) && (a.getAdrColone()>0 && a.getAdrColone()<(TAILLECOLONNE)))
            b=this.tab[a.getAdrLigne()][a.getAdrColone()];
        return b;

    }

    public Carte(){

        this.tab = new Object[TAILLELIGNE][TAILLECOLONNE];
        int i;
        int j;

        for (i=0; i<Carte.TAILLELIGNE; i++){
            for(j=0; j<Carte.TAILLECOLONNE; j++){
                this.tab[i][j]=s;

            }
        }
    }



    public void placerAle(){

        int a;
        int b;
        int h;
        a=dice(TAILLELIGNE);
        b=dice(TAILLECOLONNE);
        h=dice(3);


        Element m=  new Element();
        Element v =new Element();
        Element s= new Element();
        Element c=new Element();
        if( h==2){

        }
        this.PlacerElement2(v, new Addresse(a,b));
        if( h==0){
            this.PlacerElement2(m, new Addresse(a,b));
        }
        if( h==1){
            this.PlacerElement2(s, new Addresse(a,b));

        }
    }

    public void PlacerM(){
        int a;
        int b;

        Element m=  new Element();
        Element v=  new Element();

        a=dice(TAILLELIGNE);
        b=dice(TAILLECOLONNE);

        this.PlacerElement2(m, new Addresse(a,b));
        this.PlacerElement2(v, new Addresse(a,b));
        a=dice(TAILLELIGNE);
        b=dice(TAILLECOLONNE);

    }

    public static char intTochar(int i){
        String s =""+i;
        return s.charAt(0);
    }

    public String afficheCarte(){
        String s = new String();
        int i;
        int j;
        System.out.print(" ");
        s+=" ";
        for( i =0; i<this.tab.length;i++ ){
            if(i== this.tab.length -1 ){
                System.out.print(" "+intTochar(i)+" \n");
                s+=" "+intTochar(i)+" \n";
            }
            else{
                System.out.print(" " + intTochar(i) + " ");
                s+=" " + intTochar(i) + " ";
            }
        }
        for( i =0; i<this.tab.length;i++ ){
            for( j = 0;j<this.tab[i].length;j++){
                if (j==0)
                {
                    System.out.print(i+""+tab[i][j].toString());
                    s+=i+""+tab[i][j].toString();
                }
                else {
                    if (this.tab[i][j]==null){
                        System.out.print(tab[i][j].toString());
                        s+=tab[i][j].toString();
                    }
                    System.out.print(tab[i][j].toString());
                    s+=tab[i][j].toString();
                }
            }
            System.out.println("|");
            s+="|"+"\n";
        }
        System.out.print(" ");
        s+=" ";
        for( i =0; i<this.tab.length;i++ ){
            System.out.print(" _ ");
            s+=" _ ";
        }
        System.out.print("\n");
        s+="\n";
        System.out.print("\n");
        s+="\n";

        return s;
    }


    public void PlacerElement(Element a){
        if ((a.getAdresse().getAdrLigne()>=0 && a.getAdresse().getAdrLigne()< (TAILLELIGNE)) &&
                (a.getAdresse().getAdrColone()>=0 && a.getAdresse().getAdrColone()< (TAILLECOLONNE) )){

            if ( this.tab[a.getAdresse().getAdrLigne()][a.getAdresse().getAdrColone()].toString() !=" . "){
                System.out.println("Occcuper  \n");
            }
            else{
                this.tab[a.getAdresse().getAdrLigne()][a.getAdresse().getAdrColone()]=a;
            }
        }
        else {
            System.out.println("Erreur d'Adresse!\n ");
            System.out.println("8 \n");
        }
    }

    public  void PlacerElement2(Object v, Addresse b){
        if ( (b.getAdrLigne()>=0 && b.getAdrLigne()< TAILLELIGNE) &&
                (b.getAdrColone()>=0 && b.getAdrColone()< TAILLECOLONNE) &&
                (this.tab[b.getAdrLigne()][b.getAdrColone()].toString() == "#")){
            System.out.println("ELEMENT PLACER \n ");
        }
        else{
            this.tab[b.getAdrLigne()][b.getAdrColone()]=v;
            if (v instanceof  Element  ){
                ((Element) v).setAdresse(b);
            }
        }
    }


    public void SuprimerE(Addresse a){
        if ((a.getAdrLigne()>=0 && a.getAdrLigne()< (TAILLELIGNE)) && (a.getAdrColone()>=0 && a.getAdrColone()< (TAILLECOLONNE) )){
            this.tab[a.getAdrLigne()][a.getAdrColone()]=s;
        }
    }

    public boolean caseValide(Addresse b){
        if(b.getAdrLigne() >= 0 && b.getAdrLigne() < TAILLELIGNE && b.getAdrColone() >= 0 && b.getAdrColone() < TAILLECOLONNE){
            return true;
        }
        else
            return false;
    }

    public boolean caseVide(Addresse a){
        if(caseValide(a) && this.tab[a.getAdrLigne()][a.getAdrColone()] == s){
            return true;
        }
        else
            return false;
    }

    public int dice( int sides)throws NullPointerException{
        int total = 0;
        this.random= new Random();
        total = this.random.nextInt(sides) ;
        return total;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Carte a;
        Element b  = new Element(" X ");
        Element v  = new Element();
        Addresse n= new Addresse(5,3);
        Addresse h= new Addresse(7,9);

        a = new  Carte();
        a.setNonC("GRAND LINE");
        int g=1;
        a.PlacerElement2(v, h);
        a.PlacerElement2(b,n);
        a.afficheCarte();

    }

}
