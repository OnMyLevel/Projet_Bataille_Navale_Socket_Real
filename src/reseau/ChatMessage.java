package reseau;


import java.io.*;


public class ChatMessage implements Serializable {

    // The different types of message sent by the Client
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary text message
    // LOGOUT to disconnect from the Server
    static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, JOUER = 3, YES = 5;
    static final int MANUEL = 6, AUTOMATIQUE = 7, INSTRUCTION=8, LOCATION=9;
    private int type;
    private String message;

    // constructor
    public ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    String getMessage() {
        return message;
    }
}
