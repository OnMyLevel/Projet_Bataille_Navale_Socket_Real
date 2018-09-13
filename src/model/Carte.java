package model;
import sun.jvm.hotspot.debugger.posix.elf.ELFException;

import java.util.Random;
public class Carte {

    private String nomc;
    private Object  tab[][];
    public static final int colonec =10;
    public static final int lignec =10;
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

        if( (a.getAdrLigne()> 0 &&  a.getAdrLigne()<(lignec)) && (a.getAdrColone()>0 && a.getAdrColone()<(colonec)))
            b=this.tab[a.getAdrLigne()][a.getAdrColone()];
        return b;

    }


    public Carte(){

        this.tab = new Object[lignec][colonec];
        int i;
        int j;

        for (i=0;i<Carte.lignec;i++){
            for(j=0;j<Carte.colonec;j++){
                this.tab[i][j]=s;

            }
        }
    }

    public void IniCarte1()
    {
        this.tab = new Object[lignec][];
        int i;
        int j;

        for ( i =0; i<Carte.lignec;i++){

            this.tab[i]=new Object[Carte.colonec];
        }

        for( i =0; i<Carte.lignec;i++ ){
            for( j = 0;j<this.tab[i].length;j++){
                this.tab[i][j]=s;


            }
        }
        this.PlacerAle();
        this.PlacerAle();
    }

    public void PlacerAle(){

        int a;
        int b;
        int h;
        a=dice(lignec);
        b=dice(colonec);
        h=dice(3);


        Element m=  new Element();
        Element v =new Element();
        Element s= new Element();
        Element c=new Element();
        if( h==2){

        }
        this.PlacerE2(v, new Addresse(a,b));


        if( h==0){

        }
        this.PlacerE2(m, new Addresse(a,b));

        if( h==1){

            this.PlacerE2(s, new Addresse(a,b));

        }
    }

    public void PlacerM(){
        int a;
        int b;

        Element m=  new Element();
        Element v=  new Element();

        a=dice(lignec);
        b=dice(colonec);

        this.PlacerE2(m, new Addresse(a,b));
        this.PlacerE2(v, new Addresse(a,b));
        a=dice(lignec);
        b=dice(colonec);

    }



    public static char intTochar(int i){
        String s =""+i;
        return s.charAt(0);
    }


    public void AfficheC3()
    {
        int i;
        int j;
        System.out.print(" ");
        for( i =0; i<this.tab.length;i++ ){
            if(i== this.tab.length -1 ){
                System.out.print(" "+intTochar(i)+" \n");
            }
            else{
                System.out.print(" " + intTochar(i) + " ");
            }
        }
        for( i =0; i<this.tab.length;i++ ){
            for( j = 0;j<this.tab[i].length;j++){
                if (j==0)
                {
                    System.out.print(i+""+tab[i][j].toString());
                }
                else {
                    if (this.tab[i][j]==null){
                        System.out.print(tab[i][j].toString());
                    }
                    System.out.print(tab[i][j].toString());
                }
            }
            System.out.println("|");
        }
        System.out.print(" ");
        for( i =0; i<this.tab.length;i++ ){
            System.out.print(" _ ");
        }
        System.out.print("\n");
        System.out.print("\n");
    }


    public void PlacerE1(Element a){
        if ((a.getAdresse().getAdrLigne()>=0 && a.getAdresse().getAdrLigne()< (lignec)) &&
                (a.getAdresse().getAdrColone()>=0 && a.getAdresse().getAdrColone()< (colonec) )){

            if ( this.tab[a.getAdresse().getAdrLigne()][a.getAdresse().getAdrColone()].toString()!="?"){
                System.out.println("Occcuper! \n");
            }
            else{
                this.tab[a.getAdresse().getAdrLigne()][a.getAdresse().getAdrColone()]=a;
            }
        }
        else {
            System.out.println("Eror d'Adresse!\n ");
        }
    }

    public  void PlacerE2(Object v, Addresse b){
        if ( (b.getAdrLigne()>=0 && b.getAdrLigne()< lignec) &&
                (b.getAdrColone()>=0 && b.getAdrColone()< colonec ) &&
                (this.tab[b.getAdrLigne()][b.getAdrColone()].toString()=="#")){

            System.out.println("E\n ");
        }

        else{
            this.tab[b.getAdrLigne()][b.getAdrColone()]=v;
            if (v instanceof  Element  ){
                ((Element) v).setAdresse(b);
            }
        }
    }


    public void SuprimerE(Addresse a){
        if ((a.getAdrLigne()>=0 && a.getAdrLigne()< (lignec)) && (a.getAdrColone()>=0 && a.getAdrColone()< (colonec) )){

            this.tab[a.getAdrLigne()][a.getAdrColone()]=s;
        }
    }

    public boolean caseValide(Addresse b){
        if(b.getAdrLigne() >= 0 && b.getAdrLigne() < lignec && b.getAdrColone() >= 0 && b.getAdrColone() < colonec){
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
		Element b  = new Element();
		Element v  = new Element();
		Addresse n= new Addresse(5,3);
		Addresse h= new Addresse(7,9);

		a = new  Carte();
		a.setNonC("MERIL");
		 int g=1;
		 a.PlacerE2(v, h);
		 a.PlacerE2(b,n);

		 a.AfficheC3();


	}

}
