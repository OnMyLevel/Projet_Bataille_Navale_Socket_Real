package model;

public class Addresse {

    private int ligne;
    private int colone;

    public int getAdrColone() {
        return this.colone;
    }

    public void setAdrColone(int a) {
        this.colone = a;
    }

    public int getAdrLigne() {
        return this.ligne;
    }

    public void setAdrLigne(int b) {
        this.ligne = b;
    }

    public Addresse(int a, int b) {
        this.ligne = a;
        this.colone = b;
    }

    public Addresse() {
        this.ligne = 0;
        this.colone = 0;
    }

    public void CopieA(Addresse a) {
        this.ligne = a.ligne;
        this.colone = a.colone;
    }

    public boolean equal(Addresse a) {

        if (this.ligne == a.getAdrLigne() && this.colone == a.getAdrColone()) {
            return true;
        }

        return false;
    }

    public String toString() {
        return ("ligne :" + this.ligne + "| colone :" + this.colone);

    }

    public void Addresse(int i, int j) {
        // TODO Auto-generated method stub
        this.ligne = i;
        this.colone = j;

    }
}
