package model;

import java.util.ArrayList;

public class Flotte {

    private static final int NOMBRE_BATEAU = 5;

    private static  final int CROISEUR_TAILLE=3;
    private static  final int CUIRASSE_TAILLE=4;
    private static  final int TORPILLEUR_TAILLE=2;
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

    public boolean freePosition(int x, int y){
        boolean verif = false;
        if(this.maps.caseValide(new Addresse(x,y)) &&  this.maps.caseVide(new Addresse( x,y))){
            verif = true;
        }
        return verif;
    }

    public boolean freePositioncCroiseurAlignement(Addresse debut,boolean vertical,int taille){
        boolean a = true;
        if(!vertical) {
            for (int i = 0; i < taille; i++) {
                if (! this.freePosition(debut.getAdrLigne() , debut.getAdrColone()+i)){
                    a = false;
                }
            }
        }else{
            for (int i = 0; i < taille; i++) {
                if (!this.freePosition(debut.getAdrLigne()+i , debut.getAdrColone())){
                    a = false;
                }
            }
        }
        return a;
    }

    public void placeCroiseur(Addresse debut,boolean vertical){
        System.out.println(" Placement d'un bateau de type Croiseur");
       if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
               && this.freePositioncCroiseurAlignement(debut,vertical,CROISEUR_TAILLE)){
           if( (vertical != true && CROISEUR_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                   (vertical == true && CROISEUR_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){
               if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CROISEUR_TAILLE - debut.getAdrColone()))) {
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
               System.out.println("Impossible de placer un Croiseur à cette position 2");
           }
       }else{
           System.out.println("Impossible de placer un Croiseur à cette position ");
       }
    }

    public void placeCuirasse(Addresse debut,boolean vertical){
        System.out.println("Placement d'un bateau de type Cuirasse");
        if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut,vertical,CUIRASSE_TAILLE)){
            if( (vertical != true && CUIRASSE_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && CUIRASSE_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){

                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CUIRASSE_TAILLE - debut.getAdrColone()))) {
                    Cuirasse a = new Cuirasse(debut.getAdrLigne(), debut.getAdrColone(),CUIRASSE_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                }
            }
            else{
                System.out.println("Impossible de placer un Cuirasse à cette position  ");
            }
        }else{
            System.out.println("Impossible de placer un Cuirasse à cette position ");
        }
    }

    public void placePorteAvion(Addresse debut,boolean vertical){
        System.out.println("Placement d'un bateau de type PorteAvion");
        if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut,vertical,PORTEAVIONS_TAILLE)){
            if( (vertical != true && PORTEAVIONS_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && PORTEAVIONS_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){

                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (PORTEAVIONS_TAILLE - debut.getAdrColone()))) {
                    PorteAvion a = new PorteAvion(debut.getAdrLigne(), debut.getAdrColone(),PORTEAVIONS_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                }
            }
            else{
                System.out.println("Impossible de placer un PorteAvion à cette position  ");
            }
        }else{
            System.out.println("Impossible de placer un PorteAvion à cette position ");
        }
    }

    public void placeTorpilleur(Addresse debut,boolean vertical){
        System.out.println("Placement d'un bateau de type Torpilleur");
        if(!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut,vertical,TORPILLEUR_TAILLE)){
            if( (vertical != true && TORPILLEUR_TAILLE<= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && TORPILLEUR_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())  ){

                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (TORPILLEUR_TAILLE - debut.getAdrColone()))) {
                    PorteAvion a = new PorteAvion(debut.getAdrLigne(), debut.getAdrColone(),TORPILLEUR_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                }
            }
            else{
                System.out.println("Impossible de placer un Torpilleur à cette position  ");
            }
        }else{
            System.out.println("Impossible de placer un Torpilleur à cette position ");
        }
    }


    public void placeSousmarin(Addresse debut,boolean vertical){
           System.out.println("Placement d'un bateau de type SousMarin");
            if(freePosition(debut.getAdrLigne(),debut.getAdrColone() + ( SOUSMARIN_TAILLE - debut.getAdrColone() ))){
                SousMarin a = new SousMarin(debut.getAdrLigne(),debut.getAdrColone(), SOUSMARIN_TAILLE,vertical);
                for(int i=0; i< a.getElement().length; i++){
                    if(a.getElement()[i]!=null) {
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
        a.placeCuirasse(new Addresse(5,0), false);
        a.maps.AfficheC3();
        a.placePorteAvion(new Addresse(4,7), true);
        a.maps.AfficheC3();
        a.placeTorpilleur(new Addresse(1,5), false);
        a.maps.AfficheC3();

        for(int i=0;i< a.compBateau.size();i++) {
            System.out.println(a.compBateau.get(i).toString());
        }
    }
}
