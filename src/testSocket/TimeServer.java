package testSocket;

import game.Game;
import game.Joueur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class TimeServer {

    //On initialise des valeurs par défaut
    private int port = 2345;
    private String host = "127.0.0.1";
    private ServerSocket server = null;
    private boolean isRunning = true;
    private Game realGame;
    private HashMap<Thread, Joueur> joueurHashMap;

    public TimeServer(){
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        joueurHashMap = new HashMap<>();
        realGame = new Game();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Game getRealGame() {
        return realGame;
    }

    public void setRealGame(Game realGame) {
        this.realGame = realGame;
    }

    public HashMap<Thread, Joueur> getJoueurHashMap() {
        return joueurHashMap;
    }

    public void setJoueurHashMap(HashMap<Thread, Joueur> joueurHashMap) {
        this.joueurHashMap = joueurHashMap;
    }

    public TimeServer(String pHost, int pPort){
        host = pHost;
        port = pPort;
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //On lance notre serveur
    public void open(){

        //Toujours dans un thread à part vu qu'il est dans une boucle infinie
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(isRunning == true){

                    try {
                        //On attend une connexion d'un client
                        Socket client = server.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");
                        Thread t = new Thread(new ClientProcessor(client));
                        if(joueurHashMap.isEmpty()) {
                            System.out.println("Connexion cliente reçue.");
                            Joueur tmp = new Joueur(t.getName(), "0000", 0,0);
                            joueurHashMap.put(t, tmp);
                            realGame.ajouerJoueur(tmp);
                        }
                        else{
                            System.out.println("Connexion cliente reçue.");
                            Joueur tmp = new Joueur(t.getName(), "0000", 0,joueurHashMap.size());
                            joueurHashMap.put(t, tmp);
                            realGame.ajouerJoueur(tmp);
                        }
                        t.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });

        t.start();
    }

    public void close(){
        isRunning = false;
    }
}
