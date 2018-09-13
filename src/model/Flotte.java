package model;

import java.util.ArrayList;
import java.util.Vector;

public class Flotte {

    private static final int NOMBRE_BATEAU = 5;
    private ArrayList<Bateau> compBateau;
    private boolean flotteDet;

    public Flotte() {
        this.flotteDet = false;
        this.compBateau = new ArrayList<>();
    }

}
