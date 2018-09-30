package testSocket;

import game.Game;
import game.Joueur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class TimeServer {

    //On initialise des valeurs par défaut
    private int port = 2345;
    private String host = "127.0.0.1";
    private ServerSocket server = null;
    private boolean isRunning = true;
    private Game realGame;
    private ArrayList<Thread> listThread;

    public TimeServer(){
        try {
            server = new ServerSocket(port, 100, InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listThread = new ArrayList<>();
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
   /* public void open(){

        //Toujours dans un thread à part vu qu'il est dans une boucle infinie
        Thread t = new Thread(new Runnable(){
            public void run(){
                int i =0;
                while(isRunning == true){

                    try {
                        //On attend une connexion d'un client
                        Socket client = server.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");

                        System.out.println("Connexion cliente reçue.");
                        Joueur tmp = new Joueur("joueur#"+listThread.size(), "0000", 0,listThread.size());
                        realGame.ajouerJoueur(tmp);

                        Thread t;
                        if(listThread.size()>=1) {
                             t = new Thread(new ClientProcessor(client, realGame, realGame.getJoueurs().get(listThread.size())));
                        }else{
                             t = new Thread(new ClientProcessor(client, realGame, realGame.getJoueurs().get(0)));
                        }
                        listThread.add(t);
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
    }*/




    //On lance notre serveur
    public void open() {

        //Toujours dans un thread à part vu qu'il est dans une boucle infinie
        Thread t = new Thread(new Runnable() {
            public void run() {
                int i = 0;
                while (isRunning == true) {

                    try {
                        //On attend une connexion d'un client
                        Socket client = server.accept();

                        //Une fois reçue, on la traite dans un thread séparé
                        System.out.println("Connexion cliente reçue.");

                        System.out.println("Connexion cliente reçue.");
                        Joueur tmp = new Joueur("joueur#" + listThread.size(), "0000", 0, listThread.size());
                        realGame.ajouerJoueur(tmp);

                        Thread t;
                        if (listThread.size() >= 1) {
                            t = new Thread(new ClientProcessor(client, realGame, realGame.getJoueurs().get(listThread.size())));
                        } else {
                            t = new Thread(new ClientProcessor(client, realGame, realGame.getJoueurs().get(0)));
                        }
                        listThread.add(t);
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
