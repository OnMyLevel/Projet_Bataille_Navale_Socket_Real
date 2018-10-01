package testSocket;

import game.Joueur;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientConnexion implements Runnable{

    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    private Joueur joueur;

    //Notre liste de commandes. Le serveur nous répondra différemment selon la commande utilisée.
    private String[] listCommands = {"FULL", "DATE", "HOUR", "NONE"};
    private static int count = 0;
    private String name = "Player-";
    private String coorAttaque;

    public int getToucher() {
        return toucher;
    }

    public void setToucher(int toucher) {
        this.toucher = toucher;
    }

    private int toucher;

    public String getRetourAttaque() {
        return retourAttaque;
    }

    public void setRetourAttaque(String retourAttaque) {
        this.retourAttaque = retourAttaque;
    }

    private String retourAttaque;

    public Socket getConnexion() {
        return connexion;
    }

    public void setConnexion(Socket connexion) {
        this.connexion = connexion;
    }

    public ClientConnexion(String host, int port, Joueur clientjoueur){
        name += ++count;
        try {
            connexion = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setJoueur(clientjoueur);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public String getCoorAttaque() {
        return coorAttaque;
    }

    public void setCoorAttaque(String coorAttaque) {
        this.coorAttaque = coorAttaque;
    }

    public void run(){

        //nous n'allons faire que 10 demandes par thread...
       // for(int i =0; i < 10; i++){
        while(this.getCoorAttaque()!="Q" ){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());
                //On envoie la commande au serveur
              if(this.getCoorAttaque()!=null){
                    String commande = getCommand(this.getCoorAttaque());
                    writer.write(commande);
                    //TOUJOURS UTILISER flush() POUR ENVOYER RÉELLEMENT DES INFOS AU SERVEUR
                    writer.flush();
                    System.out.println("L'attaque sur la position " + commande + ": ");
                    //On attend la réponse
                    String response = read();
                    this.setRetourAttaque(response);
                    String[] tab = response.split(",");
                    System.out.println(" ICI");
                    System.out.println(tab[0]);
                    if(tab[0].compareTo("1")==0) {
                        this.setToucher(Integer.valueOf(tab[0]));
                        System.out.println("\t * : " + " Réponse reçue " + "\n " + response);
                    }else{
                        this.setToucher(Integer.valueOf(tab[0]));
                        System.out.println("\t * : " + " Réponse reçue " + "\n " + response);
                    }
               }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writer.write("CLOSE");
        writer.flush();
        writer.close();
    }

    //Méthode qui permet d'envoyer une attaque

   private String getCommand(String coord) {
        System.out.println(coord);
        return coord;
    }

 /* private String getCommand(String coord){
        System.out.println(" Tapez les coordonées de la case \n ");
        Scanner sc = new Scanner(System.in);
        String x = sc.next();
        this.setCoorAttaque(x);
        return  x;
   }*/

    //Méthode pour lire les réponses du serveur
    private String read() throws IOException{
        System.out.println("read()");
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        System.out.println(response);
        return response;
    }

    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 2345;
        System.out.println("Serveur initialisé.");
        //for(int i = 0; i < 5; i++){
        Thread t = new Thread(new ClientConnexion(host, port, new Joueur()));
        t.start();
        //}
    }
}
