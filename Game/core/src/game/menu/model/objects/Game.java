package game.menu.model.objects;

public class Game {
    String gameId  = "";
    String date = "";
    int score = 0;
    String shipType = "";
    int enemiesDestroyed = 0;
    String difficulty = "";
    String anonName = "";
    int level = 0;
    String playerId = "";
    public Game(String gameIdIn, String dateIn, int scoreIn, String shipTypeIn,
                int enemiesDestroyedIn, String difficultyIn, String anonNameIn,
                int levelIn, String playerIdIn){
        gameId = gameIdIn;
        date = dateIn;
        score = scoreIn;
        shipType = shipTypeIn;
        enemiesDestroyed = enemiesDestroyedIn;
        playerId = playerIdIn;
        anonName = anonNameIn;
        difficulty = difficultyIn;
        level = levelIn;
    }

    public int getEnemiesDestroyed() {
        return enemiesDestroyed;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getGameId() {
        return gameId;
    }

    public String getShipType() {
        return shipType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setEnemiesDestroyed(int enemiesDestroyed) {
        this.enemiesDestroyed = enemiesDestroyed;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getAnonName() {
        return anonName;
    }

    public void setAnonName(String anonName) {
        this.anonName = anonName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
