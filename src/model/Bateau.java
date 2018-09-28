package model;

import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;

public class Bateau {

    private Element[] element;
    private boolean vertical;

    public Element[] getElement() {
        return element;
    }

    public void setElement(Element[] element) {
        this.element = element;
    }

    public Bateau(){

    }

    /** Creates a new instance of Bateau */
    public Bateau(int x, int y, int taille,boolean vertical) {
        element=new Element[taille];
        this.vertical = vertical;
        if(!vertical) {
            for (int i = 0; i < taille; i++) {
                element[i] = new Element(x, y + i);
            }
        }
        else{
            for(int i=0;i<taille;i++){
                element[i]=new Element (x+i,y);
            }
        }
    }

    public void avancer(int x,int y){
        for(int i=0;i<element.length;i++){
            element[i].avancer(x,y);
        }
    }

    public boolean touche(Addresse b){
        boolean a=false;
        for(int i=0;i<element.length;i++){
            a=element[i].toucheR(b);
        }
        return a;
    }

    public boolean estdetruit(){
        boolean res=true;
        int i=0;
        while((i<element.length)&&res){
            res=(res)&&(element[i].toString() == " * ");
            i++;
        }
        return res;
    }

    public String toString(){
        String s="nb block:" + this.getElement().length + "\n";
        for(int i=0;i<element.length;i++) {
            s += ""+element[i].toString()+"";
        }
        s +="\n";
        for(int i=0;i<element.length;i++) {
            s += "|"+element[i].getabcisse()+","+element[i].getordonnee()+"|";
        }
        s +="\n";
        return s;
    }



}
