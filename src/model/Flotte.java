package model;

import java.util.ArrayList;

public class Flotte {

    private static final int NOMBRE_BATEAU = 5;

    private static  final int CROISEUR_TAILLE=3;
    private static  final int CUIRASSE_TAILLE=4;
    private static  final int TORPILEURS_TAILLE=2;
    private static  final int SOUSMARIN_TAILLE=1;
    private static  final int PORTEAVIONS_TAILLE=5;

    private ArrayList<Bateau> compBateau;
    private boolean flotteDet;
    private Carte maps;

    public Flotte() {
        this.flotteDet = false;
        this.compBateau = new ArrayList<>();
        this.maps = new Carte();
    }

    public boolean freePositioncCroiseur(int x,int y){
        boolean verif = false;
        if(this.maps.caseValide(new Addresse(x,y)) &&  this.maps.caseVide(new Addresse( x,y))){
            verif = true;
        }
        return verif;
    }

    public void placeCroiseur(Addresse debut,boolean vertical){
        System.out.println("placeCroiseur");

       if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)){
           if( (vertical != true && CROISEUR_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                   (vertical == true && CROISEUR_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){
               if (freePositioncCroiseur(debut.getAdrLigne(), debut.getAdrColone() + (CROISEUR_TAILLE - debut.getAdrColone()))) {
                   Croiseur a = new Croiseur(debut.getAdrLigne(), debut.getAdrColone(), CROISEUR_TAILLE, vertical);
                   for (int i = 0; i < a.getElement().length; i++) {
                       if (a.getElement()[i] != null) {

                           this.maps.PlacerElement(a.getElement()[i]);
                       }
                   }
                   this.compBateau.add(a);
               }
           }
           else{
               System.out.println("Impossible de placer un croiseur à cette position 2 ");
           }
       }else{
           System.out.println("Impossible de placer un croiseur à cette position ");
       }
    }

    public void placeCuirasse(Addresse debut,boolean vertical){
        System.out.println("placeCroiseur");

        if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)){
            if( (vertical != true && CUIRASSE_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && CUIRASSE_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){

                if (freePositioncCroiseur(debut.getAdrLigne(), debut.getAdrColone() + (CUIRASSE_TAILLE - debut.getAdrColone()))) {
                    Croiseur a = new Croiseur(debut.getAdrLigne(), debut.getAdrColone(),CUIRASSE_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {

                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                }
            }
            else{
                System.out.println("Impossible de placer  de beateau à cette position 2 ");
            }
        }else{
            System.out.println("Impossible de placer se type de beateau à cette position ");
        }
    }


    public void placeSousmarin(Addresse debut,boolean vertical){
        System.out.println("4 \n");
            if(freePositioncCroiseur(debut.getAdrLigne(),debut.getAdrColone() + ( SOUSMARIN_TAILLE - debut.getAdrColone() ))){
                SousMarin a = new SousMarin(debut.getAdrLigne(),debut.getAdrColone(), SOUSMARIN_TAILLE,vertical);
                for(int i=0; i< a.getElement().length; i++){
                    if(a.getElement()[i]!=null) {
                        System.out.println("4 \n");
                        this.maps.PlacerElement(a.getElement()[i]);
                    }
                }
                this.compBateau.add(a);
            }
    }

    public boolean AjouterBateau(Bateau ajouter){
        boolean a= false ;
        if(this.compBateau.size() < NOMBRE_BATEAU){
            this.compBateau.add(ajouter);
            a = true;
        }
        return  a;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Flotte a= new Flotte();
        a.maps.AfficheC3();
        a.placeSousmarin( new Addresse(5,5),false);
        a.maps.AfficheC3();
        a.placeCroiseur(new Addresse(5,8), true);
        a.maps.AfficheC3();
        System.out.println(a.compBateau.get(0).toString());
    }
}
