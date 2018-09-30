package reseau;


import java.io.*;


public class ChatMessage implements Serializable {

    // The different types of message sent by the Client
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary text message
    // LOGOUT to disconnect from the Server
    static final int WHOISIN = 100, MESSAGE = 101, LOGOUT = 102, JOUER = 103, YES = 105;
    static final int MANUEL = 106, AUTOMATIQUE = 107, INSTRUCTION = 108, LOCATION = 109, AFFICHECARTE = 110;
    static final int SOUSMARIN = 111, TORPILLEUR = 112, CROISEUR = 113, CUIRASE = 114, PORTEAVIONS = 115, QUITTER = 116, HORIZONTAL = 117, VERTICAL = 118;
    static final int NUMBER = 120, UN = 1, DEUX = 2, TROIS = 3, QUATRE = 4, CINQ = 5, SIX = 6, SEPT = 7, HUIT = 8, NEUF = 9;
    /*static final String WHOISIN = "whoisin", MESSAGE="message", LOGOUT="logout", YES="oui";
    static final String MANUEL = "manuel", AUTOMATIQUE = "automatique", INSTRUCTION = "instruction", LOCATION = "location", AFFICHECARTE = "affichercarte";
    static final String SOUSMARIN = "sousmarin", TORPILLEUR = "torpilleur", CROISEUR = "croisseur", CUIRASE = "cuirrase", PORTEAVIONS = "porteavions", QUITTER = "quitter", HORIZONTAL = "horizontal", VERTICAL = "verical";
    static final String NUMBER = "1", JOUER = "jouer";
    static final String UN="1",DEUX="2",TROIS="3",QUATRE="4",CINQ="5",SIX="6",SEPT="7",HUIT="8",NEUF="9";*/
    private int type;
    private String message;

    // constructor
    public ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    int getType() {
        return type;
    }

    String getMessage() {
        return message;
    }
}
