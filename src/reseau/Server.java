package reseau;


import IHM.Launcher;
import game.Game;
import model.*;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
    // ID pour la connection
    private int uniqueId;
    // ArrayList des clients thread
    private ArrayList<ClientThread> al;
    // afficher temps
    private SimpleDateFormat sdf;
    // portnumber du server
    private int port;
    // verifier si le serveur en marche
    private boolean keepGoing;
    // notification
    private String notif = " *** ";
    public Game mainGame;
    public Flotte flotte;
    public Carte carte;
    private Launcher launcher;

    //constructor
    public Server(int port) {
        // the port
        this.port = port;
        // afficher hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // ArrayList des clients
        al = new ArrayList<ClientThread>();
        mainGame = new Game();
        flotte = new Flotte();
        carte = new Carte();

        //String bindAddress = "192.162.20.1";
    }

    private void start() {
        keepGoing = true;
        //creer un serveurSocket et attend les connexion requests
        try {
            // socket utiliser par le server
            ServerSocket serverSocket = new ServerSocket(port);

            // boucle infini pour attente de connexions
            while (keepGoing) {
                if (al.size() < 3) {
                    display("Le Serveur attend les clients au port " + port + ".");
                    //accepte connexion des clients
                    Socket socket = serverSocket.accept();
                    // break si le serveur est stopper
                    if (!keepGoing)
                        break;
                    //si le client est connecter, cela créer un thread
                    ClientThread t = new ClientThread(socket);
                    //ajoute le client à arraylist
                    al.add(t);
                    if(al.get(0).username.equals("admin")) {
                        t.start();
                    }
                }
                if (al.size() == 3) { // il y a 3 client
                    broadcast("Nous sommes au complet, le jeu va pouvoir commencer");
                    broadcast("Le jeu commencera dans 3...");
                    Attente();
                    broadcast(" 2...");
                    Attente();
                    broadcast(" 1...");
                    Attente();
                    broadcast(" --- START ---");

                    Socket socket = serverSocket.accept();
                    if (!keepGoing)
                        break;
                    //si le client est connecter, cela créer un thread
                    ClientThread t = new ClientThread(socket);
                    t.start();
                }

            }
            // essaie de stopper le serveur
            try {
                serverSocket.close();
                for (int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        //ferme les data streams et socket
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch (IOException ignored) {
                    }
                }
            } catch (Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }

    private void Attente() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // stop le server
    private void stop() {
        keepGoing = false;
        try {
            new Socket("127.0.0.1", port);
        } catch (Exception ignored) {
        }
    }

    //equivalent d'un System.out.println
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    //permet d'envoyer un message a tous les clients
    private synchronized boolean broadcast(String message) {

        String time = sdf.format(new Date());
        String[] w = message.split(" ",3);
        String messageLf = time + " " + message + "\n";

        // affiche le message
        System.out.print(messageLf);

        for (int i = al.size(); --i >= 0; ) {
            ClientThread ct = al.get(i);
            // try to write to the Client if it fails remove it from the list
            if (!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Deconnecte le client " + ct.username + " et l'efface de la liste.");
            }
        }
        return true;


    }

    // SI le client envoie LOGOUT message pour sortir
    private synchronized void remove(int id) {

        String disconnectedClient = "";
        // scan l'arraylist jusquuntil we found the Id
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            // if found remove it
            if (ct.id == id) {
                disconnectedClient = ct.getUsername();
                al.remove(i);
                break;
            }
        }
        broadcast(notif + disconnectedClient + " has left the chat room." + notif);
    }

    /*
     *  To run as a console application
     * > java Server
     * > java Server portNumber
     * If the port number is not specified 1500 is used
     */
    public static void main(String[] args) {
        // start server on port 1500 unless a PortNumber is specified
        int portNumber = 1500;
        switch (args.length) {
            case 1:
                try {
                    portNumber = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
                return;

        }
        // create a server object and start it
        Server server = new Server(portNumber);
        server.start();
    }

    // One instance of this thread will run for each client
    public class ClientThread extends Thread {

        // the socket to get messages from client
        private Socket socket;
        private ObjectInputStream sInput;
        private ObjectOutputStream sOutput;
        //PrintWriter pw = new PrintWriter(sOutput,true);
        // my unique id (easier for deconnection)
        int id;
        // the Username of the Client
        private String username;
        // message object to recieve message and its type
        ChatMessage cm;
        // timestamp
        String date;

        public static final int NOMBRE_BATEAU = 5;

        public static final int SOUSMARIN_TAILLE = 1;
        public static final int TORPILLEUR_TAILLE = 2;
        public static final int CROISEUR_TAILLE = 3;
        public static final int CUIRASSE_TAILLE = 4;
        public static final int PORTEAVIONS_TAILLE = 5;


        public int score,score1, score2;
        public Game mainGame =  new Game();
        public Flotte flotte = new Flotte();
        public Carte carte = new Carte();
        public ArrayList<Bateau> compBateau = new ArrayList<Bateau>();
        public boolean flotteDet = false;
        public Carte maps = new Carte();

        // Constructor
        ClientThread(Socket socket) {
            // a unique id
            id = ++uniqueId;
            this.socket = socket;
            //Creating both Data Stream
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                // read the username
                username = (String) sInput.readObject();
                broadcast(notif + username + " has joined the chat room." + notif);
            } catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            } catch (ClassNotFoundException ignored) {
            }
            date = new Date().toString() + "\n";
        }

        private String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void getMessageClient(){
            try {
                cm = (ChatMessage) sInput.readObject();
            }
            catch (IOException e){
                display(username+" Exception reading Streams: "+e);
            }
            catch (ClassNotFoundException ignored){

            }
        }

        // infinite loop to read and forward message
        public void run() {
            // to loop until LOGOUT

            boolean keepGoing = true;
            while (keepGoing) {
                // read a String (which is an object)
                getMessageClient();
                // get the message from the ChatMessage object received
                String message = cm.getMessage();
                // different actions based on type message

                String admin = al.get(0).username;
                String client1 = al.get(1).username;
                String client2 = al.get(2).username;

                //admin
                if (username.equals(admin)) {
                    //writeMsg("Vous etes l'admin, tapez 'Instruction' pour voir les instructions");
                    Admin(message);
                }

                //client1
                if (username.equals(client1)) {
                    Client1Jeu(message);
                }

                if (username.equals(client2)) {
                    Client2Jeu(message);
                }


            }
            // if out of the loop then disconnected and remove from client list
            remove(id);
            close();
        }

        // close everything
        private void close() {
            try {
                if (sOutput != null) sOutput.close();
            } catch (Exception ignored) {
            }
            try {
                if (sInput != null) sInput.close();
            } catch (Exception ignored) {
            }
            try {
                if (socket != null) socket.close();
            } catch (Exception ignored) {
            }
        }

        // write a String to the Client output stream
        private boolean writeMsg(String msg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.writeObject(msg);
            }
            // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display(notif + "Error sending message to " + username + notif);
                display(e.toString());
            }
            return true;
        }

        private void Admin(String message) {
            writeMsg("Vous etes l'admin, tapez 'Instruction' pour avoir voir les instructions");
            switch (cm.getType()) {
                case ChatMessage.INSTRUCTION:
                    writeMsg("-- Un simple message pour l'admin --\n" +
                            "1. Tapez 'WHO IS IN' pour voir la listes des clients\n" +
                            "2. Tapez 'LOGOUT' pour vous déconnectez du serveur\n" +
                            "3. Tapez 'LOCATION' pour placer les bateaux\n" +
                            "4. Tapez 'AFFICHER CARTE' pour afficher la carte");
                    break;
                case ChatMessage.MESSAGE:
                    boolean confirmation = broadcast(username + ": " + message);
                    if (confirmation == false) {
                        String msg = notif + "Désolé. Cette utilisateur n'existe pas." + notif;
                        writeMsg(msg);
                    }
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " s'est déconnecté avec le message LOGOUT.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("La liste des utilisateur à " + sdf.format(new Date()) + "\n");
                    // send list of active clients
                    for (int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i + 1) + ") " + ct.username + " depuis " + ct.date);
                    }
                    break;

                case ChatMessage.AFFICHECARTE:
                    writeMsg("\n" + al.get(0).afficheCarte());
                    writeMsg(String.valueOf(al.get(0).compBateau));
                    break;

                case ChatMessage.LOCATION:
                    writeMsg(al.get(0).afficheCarte());
                    writeMsg("Voulez vous placer les bateaux manuellement(manuel) ou automatiquement(automatique) ?");
                    getMessageClient();
                    switch (cm.getType()) {
                        case ChatMessage.MANUEL:
                            writeMsg("Vous devez placez 5 bateaux");
                            for (int i = 5; i > 0; ) {
                                al.get(0).placeMan(message);
                                if (Objects.equals(al.get(0).placeMan(message), false)) {
                                    i = 0;
                                    break;
                                } else if (Objects.equals(al.get(0).placeMan(message), true)) {
                                    i--;
                                    writeMsg("il vous reste encore " + i + " bateaux a placer");
                                }
                            }
                            writeMsg("Voici le placement final des bateaux \n" + al.get(0).afficheCarte());
                            break;
                        case ChatMessage.AUTOMATIQUE:
                            writeMsg(" Placement automatique lancer ");
                            al.get(0).placeBateauAle();
                            writeMsg(al.get(0).afficheFlotte());
                            writeMsg(al.get(0).afficheCarte());
                            writeMsg("Il y a :"+al.get(0).compBateau.size()+" bateaux");
                            break;
                    }
                    break;

                case ChatMessage.START:
                    broadcast("Les bateaux sont placés, vous pouvez commencez a jouer !! Que le meilleur gagne");
                    boolean count=true;
                    while(count){//compBateau.size()==5) {
                        ClientThread ct1 = al.get(1);
                        ClientThread ct2 = al.get(2);
                        broadcast("C'est au tour du joueur 1 (" + ct1.username + ") de commencez (vous avez dix secondes pour jouer)");
                        ct1.writeMsg(ct1.username + "a" + ct1.getScoreClient() + "points");
                        ct1.writeMsg("C'est a votre tour\n Tapez 'JOUER' pour jouer");
                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        broadcast("C'est au tour du joueur 2 (" + ct2.username + ") de commencez ( vous avez dix secondes pour jouer)");
                        ct2.writeMsg(ct1.username + "a" + ct2.getScoreClient() + "points");
                        ct2.writeMsg("C'est a votre tour\n Tapez 'JOUER' pour jouer");
                        try {
                            Thread.sleep(15000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.QUITTER:
                                count = false;
                                break;
                        }
                    }
                    break;
            }
        }

        private void Client1(String message) {
            writeMsg("Vous etes le joueur 1, tapez 'Instruction' pour avoir voir les instructions");
            switch (cm.getType()) {
                case ChatMessage.INSTRUCTION:
                    writeMsg("-- Un simple message pour le joueur 1 --\n" +
                            "1. Tapez 'WHOISIN' pour voir la listes des clients\n" +
                            "2. Tapez 'LOGOUT' pour vous déconnectez du serveur\n");
                    break;
                case ChatMessage.MESSAGE:
                    boolean confirmation = broadcast(username + ": " + message);
                    if (confirmation == false) {
                        String msg = notif + "Desolé. l'utilisateur n'existe pas." + notif;
                        writeMsg(msg);
                    }
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " s'est déconnecté avec le message LOGOUT.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("La liste des utilisateurs à " + sdf.format(new Date()) + "\n");
                    // send list of active clients
                    for (int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i + 1) + ") " + ct.username + " depuis " + ct.date);
                    }
                    break;
                case ChatMessage.JOUER:
                    broadcast(username + " veut jouer");
                    writeMsg("Voulez vous commencer a jouer ?");
                    getMessageClient();
                    switch (cm.getType()) {
                        case ChatMessage.YES:
                            writeMsg("Ok d'accord");
                            break;
                    }
                    break;
            }
        }
        private void Client1Jeu(String message){
            writeMsg("Vous etes le joueur 1, tapez 'Instruction' pour avoir voir les instructions");
            switch (cm.getType()) {
                case ChatMessage.INSTRUCTION:
                    writeMsg("-- Un simple message pour le joueur 1 --\n" +
                            "1. Tapez 'JOUER' pour voir la listes des clients\n" +
                            "2. Tapez 'LOGOUT' pour vous déconnectez du serveur\n");
                case ChatMessage.MESSAGE:
                    boolean confirmation = broadcast(username + ": " + message);
                    if (confirmation == false) {
                        String msg = notif + "Desolé. l'utilisateur n'existe pas." + notif;
                        writeMsg(msg);
                    }
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " s'est déconnecté avec le message LOGOUT.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("La liste des utilisateurs à " + sdf.format(new Date()) + "\n");
                    // send list of active clients
                    for (int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i + 1) + ") " + ct.username + " depuis " + ct.date);
                    }
                case ChatMessage.AFFICHECARTE:
                    writeMsg("\n"+al.get(0).afficheFlotte());
                    writeMsg("\n"+al.get(0).afficheCarte());
                    break;
                case ChatMessage.JOUER:
                    int aide=0;
                    int test=0;
                    test = al.get(0).lanceAttaque1();
                    if(test==1){
                        aide=0;
                        ClientThread ct1 = al.get(1);
                        writeMsg("Actuellement "+ct1.username);
                        al.get(1).setScoreClient(al.get(1).getScoreClient()+1);
                        writeMsg("Toucher !!");
                    }
                    else {
                        writeMsg("A l'eau");
                        writeMsg("Il reste : " + al.get(0).getNombreBateau()+" bateaux");
                        ClientThread ct2 = al.get(2);
                        writeMsg("C'est au tour du joueur 2 : "+ct2.username);
                    }
            }
        }

        private void Client2(String message) {
            writeMsg("Vous etes le joueur 2, tapez 'Instruction' pour avoir voir les instructions");
            switch (cm.getType()) {
                case ChatMessage.INSTRUCTION:
                    writeMsg("-- Un simple message pour le joueur 2 --\n" +
                            "1. Tapez 'WHOISIN' pour voir la listes des clients\n" +
                            "2. Tapez 'LOGOUT' pour vous déconnectez du serveur\n");
                    break;
                case ChatMessage.MESSAGE:
                    boolean confirmation = broadcast(username + ": " + message);
                    if (confirmation == false) {
                        String msg = notif + "Desolé. l'utilisateur n'existe pas." + notif;
                        writeMsg(msg);
                    }
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " s'est déconnecté avec le message LOGOUT.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("La liste des utilisateurs à " + sdf.format(new Date()) + "\n");
                    // send list of active clients
                    for (int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i + 1) + ") " + ct.username + " depuis " + ct.date);
                    }
                    break;
                        /*case ChatMessage.JOUER:
                            broadcast(username + " veut jouer");
                            writeMsg("Voulez vous commencer a jouer ?");
                            try {
                                cm = (ChatMessage) sInput.readObject();
                            } catch (IOException e) {
                                display(username + " Exception reading Streams: " + e);
                                break;
                            } catch (ClassNotFoundException e2) {
                                break;
                            }
                            switch (cm.getType()) {
                                case ChatMessage.YES:
                                    writeMsg("Ok d'accord");
                                    break;
                            }
                            break;*/
            }
        }
        private void Client2Jeu(String message){
            writeMsg("Vous etes le joueur 2, tapez 'Instruction' pour avoir voir les instructions");
            switch (cm.getType()) {
                case ChatMessage.INSTRUCTION:
                    writeMsg("-- Un simple message pour le joueur 2 --\n" +
                            "1. Tapez 'WHO IS IN' pour voir la listes des clients\n" +
                            "2. Tapez 'LOGOUT' pour vous déconnectez du serveur\n" +
                            "3. Tapez 'JOUER' pour vous déconnectez du serveur\n");
                    break;
                case ChatMessage.MESSAGE:
                    boolean confirmation = broadcast(username + ": " + message);
                    if (confirmation == false) {
                        String msg = notif + "Desolé. l'utilisateur n'existe pas." + notif;
                        writeMsg(msg);
                    }
                    break;
                case ChatMessage.LOGOUT:
                    display(username + " s'est déconnecté avec le message LOGOUT.");
                    keepGoing = false;
                    break;
                case ChatMessage.WHOISIN:
                    writeMsg("La liste des utilisateurs à " + sdf.format(new Date()) + "\n");
                    // send list of active clients
                    for (int i = 0; i < al.size(); ++i) {
                        ClientThread ct = al.get(i);
                        writeMsg((i + 1) + ") " + ct.username + " depuis " + ct.date);
                    }
                    break;
                case ChatMessage.AFFICHECARTE:
                    writeMsg("\n"+al.get(0).afficheFlotte());
                    writeMsg("\n"+al.get(0).afficheCarte());
                    break;
                case ChatMessage.JOUER:
                    int aide=0;
                    int test=0;
                    test = al.get(0).lanceAttaque2();
                    if(test==1){
                        aide=0;
                        ClientThread ct2 = al.get(2);
                        writeMsg("Actuellement "+ct2.username);
                        al.get(2).setScoreClient(al.get(2).getScoreClient()+1);
                        writeMsg("Toucher !!");
                    }
                    else {
                        writeMsg("A l'eau");
                        writeMsg("Il reste : " + al.get(0).getNombreBateau()+" bateaux");
                        ClientThread ct1 = al.get(1);
                        writeMsg("C'est au tour du joueur 2 : "+ct1.username);
                    }
                    break;
            }
        }

        private boolean placeMan(String message) {
            boolean bienFait = false;
            writeMsg(" Quel bateau souhaiter placer ? \n "
                    + " - 'Sous Marin' - (1 case)\n "
                    + " - 'Torpilleur' -(2 cases)\n "
                    + " - 'Croiseur' - (3 cases)\n "
                    + " - 'Cuirasse' - (4 cases)\n "
                    + " - 'Porte Avions' -(5 cases)\n "
                    + " - 'Quitter' -\n ");
            getMessageClient();
            boolean test = false;
            int a = 0;
            switch (cm.getType()) {
                case ChatMessage.SOUSMARIN:
                    test = false;
                    writeMsg(" Placement de Sous Marin ? ");
                    writeMsg(" Placement vertical ou horizontal ? \n ");
                    getMessageClient();
                    switch (cm.getType()){
                        case ChatMessage.VERTICAL:
                            test = al.get(0).placeSousmarin(recupAdr(), true);
                            break;
                        case ChatMessage.HORIZONTAL:
                            test = al.get(0).placeSousmarin(recupAdr(), false);
                            break;
                    }
                    while (!test) {
                        writeMsg(" Réssayer le placement! \n ");
                        writeMsg(" Placement vertical ou horizontal ? \n ");
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.VERTICAL:
                                test = al.get(0).placeSousmarin(recupAdr(), true);
                                break;
                            case ChatMessage.HORIZONTAL:
                                test = al.get(0).placeSousmarin(recupAdr(), false);
                                break;
                        }
                    }
                    writeMsg("\n"+al.get(0).afficheCarte());
                    return true;

                case ChatMessage.TORPILLEUR:
                    test = false;
                    writeMsg(" Placement de Torpilleur !  \n ");
                    writeMsg(" Placement vertical ou horizontal ? \n ");
                    getMessageClient();
                    switch (cm.getType()){
                        case ChatMessage.VERTICAL:
                            test = al.get(0).placeTorpilleur(recupAdr(), true);
                            break;
                        case ChatMessage.HORIZONTAL:
                            test = al.get(0).placeTorpilleur(recupAdr(), false);
                            break;
                    }
                    while (!test) {
                        writeMsg(" Réssayer le placement! \n ");
                        writeMsg(" Placement vertical ou horizontal ? \n ");
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.VERTICAL:
                                test = al.get(0).placeTorpilleur(recupAdr(), true);
                                break;
                            case ChatMessage.HORIZONTAL:
                                test = al.get(0).placeTorpilleur(recupAdr(), false);
                                break;
                        }
                    }
                    writeMsg("\n"+al.get(0).afficheCarte());
                    return true;

                case ChatMessage.CROISEUR:
                    test = false;
                    writeMsg(" Placement de Croiseur ! \n ");
                    writeMsg(" Placement vertical ou horizontal ? \n ");
                    getMessageClient();
                    switch (cm.getType()){
                        case ChatMessage.VERTICAL:
                            test = al.get(0).placeCroiseur(recupAdr(), true);
                            break;
                        case ChatMessage.HORIZONTAL:
                            test = al.get(0).placeCroiseur(recupAdr(), false);
                            break;
                    }
                    while (!test) {
                        writeMsg(" Réssayer le placement! \n ");
                        writeMsg(" Placement vertical ou horizontal ? \n ");
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.VERTICAL:
                                test = al.get(0).placeCroiseur(recupAdr(), true);
                                break;
                            case ChatMessage.HORIZONTAL:
                                test = al.get(0).placeCroiseur(recupAdr(), false);
                                break;
                        }
                    }
                    writeMsg("\n"+al.get(0).afficheCarte());
                    return true;

                case ChatMessage.CUIRASE:
                    test = false;
                    writeMsg(" Placement de Cuirasse ! \n ");
                    writeMsg(" Placement vertical ou horizontal ? \n ");
                    getMessageClient();
                    switch (cm.getType()){
                        case ChatMessage.VERTICAL:
                            test = al.get(0).placeCuirasse(recupAdr(), true);
                            break;
                        case ChatMessage.HORIZONTAL:
                            test = al.get(0).placeCuirasse(recupAdr(), false);
                            break;
                    }
                    while (!test) {
                        writeMsg(" Réssayer le placement! \n ");
                        writeMsg(" Placement vertical ou horizontal ? \n ");
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.VERTICAL:
                                test = al.get(0).placeCuirasse(recupAdr(), true);
                                break;
                            case ChatMessage.HORIZONTAL:
                                test = al.get(0).placeCuirasse(recupAdr(), false);
                                break;
                        }
                    }
                    writeMsg("\n"+al.get(0).afficheCarte());
                    return true;

                case ChatMessage.PORTEAVIONS:
                    test = false;
                    writeMsg(" Placement de Porte Avions ? \n ");
                    writeMsg(" Placement vertical ou horizontal ? \n ");
                    getMessageClient();
                    switch (cm.getType()){
                        case ChatMessage.VERTICAL:
                            test = al.get(0).placePorteAvion(recupAdr(), true);
                            break;
                        case ChatMessage.HORIZONTAL:
                            test = al.get(0).placePorteAvion(recupAdr(), false);
                            break;
                    }
                    while (!test) {
                        writeMsg(" Réssayer le placement! \n ");
                        writeMsg(" Placement vertical ou horizontal ? \n ");
                        getMessageClient();
                        switch (cm.getType()){
                            case ChatMessage.VERTICAL:
                                test = al.get(0).placePorteAvion(recupAdr(), true);
                                break;
                            case ChatMessage.HORIZONTAL:
                                test = al.get(0).placePorteAvion(recupAdr(), false);
                                break;
                        }
                    }
                    writeMsg("\n"+al.get(0).afficheCarte());
                    return true;

                case ChatMessage.QUITTER:
                    writeMsg(" Fin du Manageur\n ");
                    return false;
            }
            return false;
        }
        private Addresse recupAdr(){
            int x=0;
            int y=0;
            writeMsg(" Rentrez l'abscisse ↕ :");
            getMessageClient();
            switch (cm.getType()){
                case ChatMessage.UN:
                    x=1;
                    break;
                case ChatMessage.DEUX:
                    x=2;
                    break;
                case ChatMessage.TROIS:
                    x=3;
                    break;
                case ChatMessage.QUATRE:
                    x=4;
                    break;
                case ChatMessage.CINQ:
                    x=5;
                    break;
                case ChatMessage.SIX:
                    x=6;
                    break;
                case ChatMessage.SEPT:
                    x=7;
                    break;
                case ChatMessage.HUIT:
                    x=8;
                    break;
                case ChatMessage.NEUF:
                    x=9;
                    break;
            }

            writeMsg(" Rentrez l'ordonnées ↔ :");
            getMessageClient();
            switch (cm.getType()){
                case ChatMessage.UN:
                    y=1;
                    break;
                case ChatMessage.DEUX:
                    y=2;
                    break;
                case ChatMessage.TROIS:
                    y=3;
                    break;
                case ChatMessage.QUATRE:
                    y=4;
                    break;
                case ChatMessage.CINQ:
                    y=5;
                    break;
                case ChatMessage.SIX:
                    y=6;
                    break;
                case ChatMessage.SEPT:
                    y=7;
                    break;
                case ChatMessage.HUIT:
                    y=8;
                    break;
                case ChatMessage.NEUF:
                    y=9;
                    break;
            }
            return new Addresse(x,y);
        }
        private Addresse recupAdrClient(){
            int x=0;
            int y=0;
            ClientThread ctadmin = al.get(0);
            writeMsg(" Rentrez l'abscisse ↕ :");
            getMessageClient();
            switch (cm.getType()){
                case ChatMessage.UN:
                    x=1;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.DEUX:
                    x=2;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.TROIS:
                    x=3;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.QUATRE:
                    x=4;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.CINQ:
                    x=5;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.SIX:
                    x=6;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.SEPT:
                    x=7;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.HUIT:
                    x=8;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.NEUF:
                    x=9;
                    ctadmin.writeMsg("");
                    break;
            }

            writeMsg(" Rentrez l'ordonnées ↔ :");
            getMessageClient();
            switch (cm.getType()){
                case ChatMessage.UN:
                    y=1;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.DEUX:
                    y=2;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.TROIS:
                    y=3;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.QUATRE:
                    y=4;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.CINQ:
                    y=5;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.SIX:
                    y=6;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.SEPT:
                    y=7;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.HUIT:
                    y=8;
                    ctadmin.writeMsg("");
                    break;
                case ChatMessage.NEUF:
                    y=9;
                    ctadmin.writeMsg("");
                    break;
            }
            return new Addresse(x,y);
        }
        private int adrToucher(Addresse t){
            int i = 0;
            while (i <al.get(0).compBateau.size()){
                for (int j = 0; j < al.get(0).compBateau.get(i).getElement().length;j++) {
                    if (al.get(0).compBateau.get(i).getElement()[j].toucheR(t)) {
                        writeMsg("Vous avez toucher un bateau à l'adresse  " + t.toString() + "");
                        al.get(0).maps.afficheCarte();
                        al.get(0).bateauDetruit();
                        return 1;
                    }
                }
                i++;
            }
            al.get(0).bateauDetruit();
            return 0;
        }
        private void bateauDetruit(){
            for (int i = 0; i <al.get(0).compBateau.size(); i++) {
                if (al.get(0).compBateau.get(i).estdetruit()){
                    writeMsg("Le bateau "+al.get(0).compBateau.get(i).getClass()+" a été détruit ");
                }
            }
        }
        private int lanceAttaque1(){
            return al.get(0).adrToucher(al.get(1).recupAdr());
        }
        private int lanceAttaque2(){
            return al.get(0).adrToucher(al.get(2).recupAdr());
        }
        private int getScoreClient(){
            return score;
        }
        private void setScoreClient(int score){
            score = score;
        }


        public Carte getMaps() {
            return al.get(0).maps;
        }
        public boolean freePosition(int x, int y) {
            boolean verif = false;
            if (al.get(0).maps.caseValide(new Addresse(x, y)) && al.get(0).maps.caseVide(new Addresse(x, y))) {
                verif = true;
            }
            return verif;
        }
        public boolean freePositioncCroiseurAlignement(Addresse debut, boolean vertical, int taille) {
            boolean a = true;
            if (!vertical) {
                for (int i = 0; i < taille; i++) {
                    if (!al.get(0).freePosition(debut.getAdrLigne(), debut.getAdrColone() + i)) {
                        a = false;
                    }
                }
            } else {
                for (int i = 0; i < taille; i++) {
                    if (!al.get(0).freePosition(debut.getAdrLigne() + i, debut.getAdrColone())) {
                        a = false;
                    }
                }
            }
            return a;
        }
        public boolean placeCroiseur(Addresse debut, boolean vertical) {
            boolean bienFait = false;
            if (!debut.equal(al.get(0).maps.EXAVERTICAL) && !debut.equal(al.get(0).maps.EXBHORYSENTAL)
                    && al.get(0).freePositioncCroiseurAlignement(debut, vertical, CROISEUR_TAILLE)) {
                if ((vertical != true && CROISEUR_TAILLE <= al.get(0).maps.TAILLECOLONNE - debut.getAdrColone()) ||
                        (vertical == true && CROISEUR_TAILLE <= al.get(0).maps.TAILLELIGNE - debut.getAdrLigne())) {
                    if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CROISEUR_TAILLE - debut.getAdrColone()))) {
                        Croiseur a = new Croiseur(debut.getAdrLigne(), debut.getAdrColone(), CROISEUR_TAILLE, vertical);
                        for (int i = 0; i < a.getElement().length; i++) {
                            if (a.getElement()[i] != null) {
                                al.get(0).maps.PlacerElement(a.getElement()[i]);
                            }
                        }
                        al.get(0).compBateau.add(a);
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
            if (!debut.equal(al.get(0).maps.EXAVERTICAL) && !debut.equal(al.get(0).maps.EXBHORYSENTAL)
                    && al.get(0).freePositioncCroiseurAlignement(debut, vertical, CUIRASSE_TAILLE)) {
                if ((vertical != true && CUIRASSE_TAILLE <= al.get(0).maps.TAILLECOLONNE - debut.getAdrColone()) ||
                        (vertical == true && CUIRASSE_TAILLE <= al.get(0).maps.TAILLELIGNE - debut.getAdrLigne())) {
                    if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (CUIRASSE_TAILLE - debut.getAdrColone()))) {
                        Cuirasse a = new Cuirasse(debut.getAdrLigne(), debut.getAdrColone(), CUIRASSE_TAILLE, vertical);
                        for (int i = 0; i < a.getElement().length; i++) {
                            if (a.getElement()[i] != null) {
                                al.get(0).maps.PlacerElement(a.getElement()[i]);
                            }
                        }
                        al.get(0).compBateau.add(a);
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
            if (!debut.equal(al.get(0).maps.EXAVERTICAL) && !debut.equal(al.get(0).maps.EXBHORYSENTAL)
                    && al.get(0).freePositioncCroiseurAlignement(debut, vertical, PORTEAVIONS_TAILLE)) {
                if ((vertical != true && PORTEAVIONS_TAILLE <= al.get(0).maps.TAILLECOLONNE - debut.getAdrColone()) ||
                        (vertical == true && PORTEAVIONS_TAILLE <= al.get(0).maps.TAILLELIGNE - debut.getAdrLigne())) {
                    if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (PORTEAVIONS_TAILLE - debut.getAdrColone()))) {
                        PorteAvion a = new PorteAvion(debut.getAdrLigne(), debut.getAdrColone(), PORTEAVIONS_TAILLE, vertical);
                        for (int i = 0; i < a.getElement().length; i++) {
                            if (a.getElement()[i] != null) {
                                al.get(0).maps.PlacerElement(a.getElement()[i]);
                            }
                        }
                        al.get(0).compBateau.add(a);
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
            if (!debut.equal(al.get(0).maps.EXAVERTICAL) && !debut.equal(al.get(0).maps.EXBHORYSENTAL)
                    && al.get(0).freePositioncCroiseurAlignement(debut, vertical, TORPILLEUR_TAILLE)) {
                if ((vertical != true && TORPILLEUR_TAILLE <= al.get(0).maps.TAILLECOLONNE - debut.getAdrColone()) ||
                        (vertical == true && TORPILLEUR_TAILLE <= al.get(0).maps.TAILLELIGNE - debut.getAdrLigne())) {

                    if (freePosition(debut.getAdrLigne(), debut.getAdrColone() + (TORPILLEUR_TAILLE - debut.getAdrColone()))) {
                        Torpilleur a = new Torpilleur(debut.getAdrLigne(), debut.getAdrColone(), TORPILLEUR_TAILLE, vertical);
                        for (int i = 0; i < a.getElement().length; i++) {
                            if (a.getElement()[i] != null) {
                                al.get(0).maps.PlacerElement(a.getElement()[i]);
                            }
                        }
                        al.get(0).compBateau.add(a);
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
                        al.get(0).maps.PlacerElement(a.getElement()[i]);
                    }
                }
                al.get(0).compBateau.add(a);
                bienFait = true;
            } else {
                System.out.println("Impossible de placer un SousMarin à cette position ");
            }
            return bienFait;
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
                        a = al.get(0).placeSousmarin(new Addresse(x, y), true);
                    } else {
                        a = al.get(0).placeSousmarin(new Addresse(x, y), false);
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
                    a = al.get(0).placePorteAvion(new Addresse(x,y),true);
                }else{
                    a = al.get(0).placePorteAvion(new Addresse(x,y),false);
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
                    a = al.get(0).placeCroiseur(new Addresse(x,y),true);
                }else{
                    a = al.get(0).placeCroiseur(new Addresse(x,y),false);
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
                    a = al.get(0).placeTorpilleur(new Addresse(x,y),true);
                }else{
                    a = al.get(0).placeTorpilleur(new Addresse(x,y),false);
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
                    a = al.get(0).placeCuirasse(new Addresse(x,y),true);
                }else{
                    a = al.get(0).placeCuirasse(new Addresse(x,y),false);
                }
            }
            return a;
        }
        public boolean placeBateauAle(){
            boolean a=false;
            a = al.get(0).placeAleCroiseur();
            a = al.get(0).placeAlePorteAVion();
            a = al.get(0).placeAleSousMarin();
            a = al.get(0).placeAleCuirasse();
            a = al.get(0).placeAleTorpilleur();
            return  a;
        }
        public boolean finDeLaFlotte(){

            return al.get(0).compBateau.isEmpty();
        }
        public String afficheFlotte(){
            String s = new String();
            for(int i=0;i< al.get(0).compBateau.size();i++) {
                s+= al.get(0).compBateau.get(i).toString();
                //sSystem.out.println(al.get(0).compBateau.get(i).toString());
            }
            return s;
        }
        public  int getNombreBateau(){
            return  al.get(0).compBateau.size();
        }
        public String afficheCarte(){

            return al.get(0).maps.afficheCarte();
        }
    }
}