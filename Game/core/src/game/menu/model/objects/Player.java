package game.menu.model.objects;

public class Player {
    String playerId = "";
    String passWord = "";
    String name = "";
    public Player(String a, String b, String c){
        playerId = a;
        passWord = b;
        name = c;
    }

    public String getName() {
        return name;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
