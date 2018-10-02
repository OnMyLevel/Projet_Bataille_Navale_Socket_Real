package model;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;

public class Flotte {

    public static final int NOMBRE_BATEAU = 5;

    public static final int SOUSMARIN_TAILLE = 1;
    public static final int TORPILLEUR_TAILLE = 2;
    public static final int CROISEUR_TAILLE = 3;
    public static final int CUIRASSE_TAILLE = 4;
    public static final int PORTEAVIONS_TAILLE = 5;

    public ArrayList<Bateau> compBateau;
    public Bateau bateauDetruis;
    public boolean flotteDet;
    public Carte maps;

    public Flotte() {
        this.flotteDet = false;
        this.compBateau = new ArrayList<>();
        this.maps = new Carte();
    }

    public Carte getMaps() {
        return maps;
    }

    public boolean freePosition(int x, int y) {
        boolean verif = false;
        if (this.maps.caseValide(new Addresse(x, y)) && this.maps.caseVide(new Addresse(x, y))) {
            verif = true;
        }
        return verif;
    }

    public boolean freePositioncCroiseurAlignement(Addresse debut, boolean vertical, int taille) {
        boolean a = true;
        if (!vertical) {
            for (int i = 0; i < taille; i++) {
                if (!this.freePosition(debut.getAdrLigne(), debut.getAdrColone() + i)) {
                    a = false;
                }
            }
        } else {
            for (int i = 0; i < taille; i++) {
                if (!this.freePosition(debut.getAdrLigne() + i, debut.getAdrColone())) {
                    a = false;
                }
            }
        }
        return a;
    }

    public boolean placeCroiseur(Addresse debut, boolean vertical) {
        boolean bienFait = false;
        if (!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut, vertical, CROISEUR_TAILLE)) {
            if ((vertical != true && CROISEUR_TAILLE <= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && CROISEUR_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())) {
                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CROISEUR_TAILLE - debut.getAdrColone()))) {
                    Croiseur a = new Croiseur(debut.getAdrLigne(), debut.getAdrColone(), CROISEUR_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                    bienFait = true;
                }
            } else {
                System.out.println("Impossible de placer un Croiseur à cette position ");
            }
        } else {
            System.out.println("Impossible de placer un Croiseur à cette position ");
        }
        return bienFait;
    }

    public boolean placeCuirasse(Addresse debut, boolean vertical) {
        boolean bienFait = false;
        if (!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut, vertical, CUIRASSE_TAILLE)) {
            if ((vertical != true && CUIRASSE_TAILLE <= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && CUIRASSE_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())) {
                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CUIRASSE_TAILLE - debut.getAdrColone()))) {
                    Cuirasse a = new Cuirasse(debut.getAdrLigne(), debut.getAdrColone(), CUIRASSE_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                    bienFait = true;
                }
            } else {
                System.out.println("Impossible de placer un Cuirasse à cette position  ");
            }
        } else {
            System.out.println("Impossible de placer un Cuirasse à cette position ");
        }
        return bienFait;
    }

    public boolean placePorteAvion(Addresse debut, boolean vertical) {
        boolean bienFait = false;
        if (!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut, vertical, PORTEAVIONS_TAILLE)) {
            if ((vertical != true && PORTEAVIONS_TAILLE <= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && PORTEAVIONS_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())) {
                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (PORTEAVIONS_TAILLE - debut.getAdrColone()))) {
                    PorteAvion a = new PorteAvion(debut.getAdrLigne(), debut.getAdrColone(), PORTEAVIONS_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                    bienFait = true;
                }
            } else {
                System.out.println("Impossible de placer un PorteAvion à cette position ");
            }
        } else {
            System.out.println("Impossible de placer un PorteAvion à cette position ");
        }
        return bienFait;
    }

    public boolean placeTorpilleur(Addresse debut, boolean vertical) {
        boolean bienFait = false;
        if (!debut.equal(this.maps.EXAVERTICAL) && !debut.equal(this.maps.EXBHORYSENTAL)
                && this.freePositioncCroiseurAlignement(debut, vertical, TORPILLEUR_TAILLE)) {
            if ((vertical != true && TORPILLEUR_TAILLE <= this.maps.TAILLECOLONNE - debut.getAdrColone()) ||
                    (vertical == true && TORPILLEUR_TAILLE <= this.maps.TAILLELIGNE - debut.getAdrLigne())) {

                if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (TORPILLEUR_TAILLE - debut.getAdrColone()))) {
                    Torpilleur a = new Torpilleur(debut.getAdrLigne(), debut.getAdrColone(), TORPILLEUR_TAILLE, vertical);
                    for (int i = 0; i < a.getElement().length; i++) {
                        if (a.getElement()[i] != null) {
                            this.maps.PlacerElement(a.getElement()[i]);
                        }
                    }
                    this.compBateau.add(a);
                    bienFait = true;
                }
            } else {
                System.out.println("Impossible de placer un Torpilleur à cette position ");
            }
        } else {
            System.out.println("Impossible de placer un Torpilleur à cette position ");
        }
        return bienFait;
    }

    public boolean placeSousmarin(Addresse debut, boolean vertical) {
        boolean bienFait = false;
        if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (SOUSMARIN_TAILLE - debut.getAdrColone()))) {
            SousMarin a = new SousMarin(debut.getAdrLigne(), debut.getAdrColone(), SOUSMARIN_TAILLE, vertical);
            for (int i = 0; i < a.getElement().length; i++) {
                if (a.getElement()[i] != null) {
                    this.maps.PlacerElement(a.getElement()[i]);
                }
            }
            this.compBateau.add(a);
            bienFait = true;
        } else {
            System.out.println("Impossible de placer un SousMarin à cette position ");
        }
        return bienFait;
    }

    public boolean AjouterBateau(Bateau ajouter) {
        boolean a = false;
        if (this.compBateau.size() < NOMBRE_BATEAU) {
            this.compBateau.add(ajouter);
            a = true;
        }
        return a;
    }

    public int adrToucher(Addresse t) {
        int i = 0;
        while (i <this.compBateau.size()){
            for (int j = 0; j < this.compBateau.get(i).getElement().length;j++) {
                if (this.compBateau.get(i).getElement()[j].toucheR(t)) {
                    System.out.println("Vous avez toucher un bateau à l'adresse  " + t.toString() + "");
                    //return 1;
                }
            }
            i++;
        }
         if(this.bateauDetruit()==2){
             System.out.println(" his.bateauDetruit()==2 ");
             return 2;
        }else {
             return 0;
         }
    }

    public Bateau getBateauDetruis() {
        return bateauDetruis;
    }

    public void setBateauDetruis(Bateau bateauDetruis) {
        this.bateauDetruis = bateauDetruis;
    }

    public int  bateauDetruit() {
        System.out.println(" bateauDetruit()  ");
        for (int i = 0; i < this.compBateau.size(); i++) {
            if (this.compBateau.get(i).estdetruit()){
                System.out.println("Le bateaut "+this.compBateau.get(i).getClass()+" a été détruit 1");
                for(int j =0; j< this.compBateau.get(i).getElement().length;j++){
                    System.out.println("Le bateaut "+this.compBateau.get(i).getClass()+" a été détruit 1");
                    this.compBateau.get(i).getElement()[j].detruit();
                    this.setBateauDetruis(this.compBateau.get(i));
                    this.compBateau.remove(i);
                    return  2;
                }
            }
        }
        return 1;
    }

    public boolean placeAleSousMarin(){
        boolean a=false;
        while(!a){
            int x=(int) (Math.random() * 9);
            int y=(int) (Math.random() * 9);
            int ale= (int) (Math.random() * 2);
            boolean vertical = false;
            if(ale>0 && x> 0 && y>0){
                if (ale > 0) {
                    a = this.placeSousmarin(new Addresse(x, y), true);
                } else {
                    a = this.placeSousmarin(new Addresse(x, y), false);
                }
            }
        }
        return a;
    }

    public boolean placeAlePorteAVion(){
        boolean a=false;
        while(!a){
            int x=(int) (Math.random() * 9);
            int y=(int) (Math.random() * 9);
            int ale= (int) (Math.random() * 2);
            boolean vertical = false;
            if(ale>0 && x>0 && y>0 ){
                a = this.placePorteAvion(new Addresse(x,y),true);
            }else{
                a = this.placePorteAvion(new Addresse(x,y),false);
            }
        }
        return a;
    }

    public boolean placeAleCroiseur(){
        boolean a=false;

        while(!a){
            int x=(int) (Math.random() * 9);
            int y=(int) (Math.random() * 9);
            int ale= (int) (Math.random() * 2);
            boolean vertical = false;
            if(ale>0 && x> 0 && y>0){
                a = this.placeCroiseur(new Addresse(x,y),true);
            }else{
                a = this.placeCroiseur(new Addresse(x,y),false);
            }
        }
        return a;
    }

    public boolean placeAleTorpilleur(){
        boolean a=false;
        int i= 0;

        while(!a){
            int x=(int) (Math.random() * 9);
            int y=(int) (Math.random() * 9);
            int ale= (int) (Math.random() * 2);
            boolean vertical = false;
            if(ale>0 && x> 0 && y>0){
                a = this.placeTorpilleur(new Addresse(x,y),true);
            }else{
                a = this.placeTorpilleur(new Addresse(x,y),false);
            }
        }
        return a;
    }

    public boolean placeAleCuirasse(){

        boolean a=false;
        int i= 0;

        while(!a){
            int x=(int) (Math.random() * 9);
            int y=(int) (Math.random() * 9);
            int ale= (int) (Math.random() * 2);
            boolean vertical = false;
            if(ale>0 && x> 0 && y>0){
                a = this.placeCuirasse(new Addresse(x,y),true);
            }else{
                a = this.placeCuirasse(new Addresse(x,y),false);
            }
        }
        return a;
    }

    public boolean placeBateauAle(){
        boolean a=false;
        a = this.placeAleCroiseur();
        a = this.placeAlePorteAVion();
        a = this.placeAleSousMarin();
        a = this.placeAleCuirasse();
        a = this.placeAleTorpilleur();
        return  a;
    }

    public boolean finDeLaFlotte(){

        return this.compBateau.isEmpty();
    }

    public String afficheFlotte(){
        String s = new String();
        for(int i=0;i< this.compBateau.size();i++) {
            s+= this.compBateau.get(i).toString();
            System.out.println(this.compBateau.get(i).toString());
        }
        return s;
    }

    public  int getNombreBateau(){
        return  this.compBateau.size();
    }
    public String afficheCarte(){

        return this.maps.afficheCarte();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Flotte a= new Flotte();

        System.out.println(a.placeBateauAle());
        a.afficheCarte();
        a.afficheFlotte();
        a.afficheCarte();
        a.afficheFlotte();
        System.out.println("Tapez vos coordonées");
        System.out.println(" Tapez les coordonées de la case \n ");
        Scanner sc = new Scanner(System.in);
        String b = sc.next();
        String[] tab = b.split(",");
        System.out.println("LANCE ATTTAQUE");
        System.out.println(tab[0]);
        System.out.println(tab[1]);
        int x = Integer.valueOf(tab[0]);
        int y = Integer.valueOf(tab[1]);
        a.adrToucher( new Addresse(x,y));
        a.afficheCarte();
        a.afficheFlotte();
        System.out.println(a.getBateauDetruis().getClass());
    }
}
