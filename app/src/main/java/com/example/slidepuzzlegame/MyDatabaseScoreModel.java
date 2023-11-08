package com.example.slidepuzzlegame;

public class MyDatabaseScoreModel {

    private int scoreId;
    private int userId;
    private int moves;
    private int timeTaken;

    public MyDatabaseScoreModel(int scoreId, int userId, int moves, int timeTaken) {
        this.scoreId = scoreId;
        this.userId = userId;
        this.moves = moves;
        this.timeTaken = timeTaken;
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }
}
