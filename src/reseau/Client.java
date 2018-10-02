package reseau;


import java.net.*;
import java.io.*;
import java.util.*;



public class Client  {

    public String notif = " *** ";

    public ObjectInputStream sInput;
    public ObjectOutputStream sOutput;
    public Socket socket;
    public String server, username;
    public int port;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Client(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }


    public boolean start() {

        try {
            socket = new Socket(server, port);
        }
        catch(Exception ec) {
            display("Erreur de connexion au serveur:" + ec);
            return false;
        }

        String msg = "Connexion accepter " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        new ListenFromServer().start();

        try
        {
            sOutput.writeObject(username);
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        return true;
    }

    //message console
    private void display(String msg) {
        System.out.println(msg);
    }

    //pour envoyer un msg au serveur
    public void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        }
        catch(IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {}
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {}
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {}

    }

    void getMessage (){
        try {
            sInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }
    public static void main(String[] args) {

        int portNumber = 1500;
        String serverAddress = "localhost"; //192.168.20.31
        String userName = "Anonymous";
        Scanner scan = new Scanner(System.in);

        System.out.println("Entrez votre login: ");
        userName = scan.nextLine();
        // different case according to the length of the arguments.
        switch(args.length) {
            case 3:
                // for > javac Client username portNumber serverAddr
                serverAddress = args[2];
            case 2:
                // for > javac Client username portNumber
                try {
                    portNumber = Integer.parseInt(args[1]);
                }
                catch(Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                    return;
                }
            case 1:
                // for > javac Client username
                userName = args[0];
            case 0:
                // for > java Client
                break;
            // if number of arguments are invalid
            default:
                System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
                return;
        }
        // creation Client object
        Client client = new Client(serverAddress, portNumber, userName);
        // essai de connecter au server et return si ce n'est pas connecter
        if(!client.start())
            return;

        System.out.println("\nBonjour ! Bienvenue dans Bataille Navale 2000.");
        //client.getMessage();

        //Boucle infini pour lire ce qu'écrit le client
        while(true) {
            System.out.print("> ");
            //read les messages des clients
            String msg = scan.nextLine();

            if(msg.equalsIgnoreCase("LOGOUT")) {
                client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
                break;
            }
            else if (msg.equalsIgnoreCase("START")){
                client.sendMessage(new ChatMessage(ChatMessage.START,""));
            }
            else if(msg.equalsIgnoreCase("CROISEUR")){
                client.sendMessage(new ChatMessage(ChatMessage.CROISEUR,""));
            }
            else if(msg.equalsIgnoreCase("SOUS MARIN")){
                client.sendMessage(new ChatMessage(ChatMessage.SOUSMARIN,""));
            }
            else if(msg.equalsIgnoreCase("TORPILLEUR")){
                client.sendMessage(new ChatMessage(ChatMessage.TORPILLEUR,""));
            }
            else if(msg.equalsIgnoreCase("CUIRASSE")){
                client.sendMessage(new ChatMessage(ChatMessage.CUIRASE,""));
            }
            else if(msg.equalsIgnoreCase("PORTE AVIONS")){
                client.sendMessage(new ChatMessage(ChatMessage.PORTEAVIONS,""));
            }
            else if(msg.equalsIgnoreCase("QUITTER")){
                client.sendMessage(new ChatMessage(ChatMessage.QUITTER,""));
            }
            else if(msg.equalsIgnoreCase("AFFICHER CARTE")){
                client.sendMessage(new ChatMessage(ChatMessage.AFFICHECARTE,""));
            }
            // message to check who are present in chatroom
            else if(msg.equalsIgnoreCase("WHO IS IN")) {
                client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));
            }
            else if(msg.equalsIgnoreCase("JOUER")) {
                client.sendMessage(new ChatMessage(ChatMessage.JOUER, ""));
            }
            else if(msg.equalsIgnoreCase("OUI")){
                    client.sendMessage(new ChatMessage(ChatMessage.YES,""));
            }
            else if(msg.equalsIgnoreCase("MANUEL")){
                client.sendMessage(new ChatMessage(ChatMessage.MANUEL,""));
            }
            else if(msg.equalsIgnoreCase("AUTOMATIQUE")){
                client.sendMessage(new ChatMessage(ChatMessage.AUTOMATIQUE,""));
            }
            else if(msg.equalsIgnoreCase("INSTRUCTION")){
                client.sendMessage(new ChatMessage(ChatMessage.INSTRUCTION,""));
            }
            else if(msg.equalsIgnoreCase("LOCATION")){
                client.sendMessage(new ChatMessage(ChatMessage.LOCATION,""));
            }
            else if (msg.equalsIgnoreCase("1")) {
                client.sendMessage(new ChatMessage(ChatMessage.UN, ""));
            }
            else if (msg.equalsIgnoreCase("2")) {
                client.sendMessage(new ChatMessage(ChatMessage.DEUX, ""));
            }
            else if (msg.equalsIgnoreCase("3")) {
                client.sendMessage(new ChatMessage(ChatMessage.TROIS, ""));
            }
            else if (msg.equalsIgnoreCase("4")) {
                client.sendMessage(new ChatMessage(ChatMessage.QUATRE, ""));
            }
            else if (msg.equalsIgnoreCase("5")) {
                client.sendMessage(new ChatMessage(ChatMessage.CINQ, ""));
            }
            else if (msg.equalsIgnoreCase("6")) {
                client.sendMessage(new ChatMessage(ChatMessage.SIX, ""));
            }
            else if (msg.equalsIgnoreCase("7")) {
                client.sendMessage(new ChatMessage(ChatMessage.SEPT, ""));
            }
            else if (msg.equalsIgnoreCase("8")) {
                client.sendMessage(new ChatMessage(ChatMessage.HUIT, ""));
            }
            else if (msg.equalsIgnoreCase("9")) {
                client.sendMessage(new ChatMessage(ChatMessage.NEUF, ""));
            }
            else if (msg.equalsIgnoreCase("VERTICAL")){
                client.sendMessage(new ChatMessage(ChatMessage.VERTICAL,""));
            }
            else if(msg.equalsIgnoreCase("HORIZONTAL")){
                client.sendMessage(new ChatMessage(ChatMessage.HORIZONTAL,""));
            }
            // regular text message
            else {
                client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
            }
        }
        scan.close();
        client.disconnect();
    }

    class ListenFromServer extends Thread {

        public void run() {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    System.out.println(msg);
                    System.out.print("> ");
                }
                catch(IOException e) {
                    display(notif + "Serveur a fermer la connexion: " + e + notif);
                    break;
                }
                catch(ClassNotFoundException e2) {
                }
            }
        }
    }
}

