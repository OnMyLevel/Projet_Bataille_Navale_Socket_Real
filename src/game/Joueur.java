package game;

public class Joueur {

    private  String login;
    private  String password;
    private  int score;
    private int idGame;

    public Joueur(String login, String password, int score, int idGame) {
        this.login = login;
        this.password = password;
        this.score = score;
        this.idGame = idGame;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    @Override
    public String toString() {
        return "Joueur{" +
                "login='" + login + '\'' +
                ", score=" + score +
                '}';
    }

    public Joueur() {
    }

    public Joueur(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Joueur(String login, String password, int score) {
        this.login = login;
        this.password = password;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
