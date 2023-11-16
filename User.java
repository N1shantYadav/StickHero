package com.example.stickhero;
import java.util.*;

class User implements GameFeatures{

    private int score, cherries;
    private static ArrayList<User> savedGames = new ArrayList<User>();


    public User(int score, int cherries) {
        this.score = score;
        this.cherries = cherries;
    }

    public void restartGame(){
        this.score = 0;
        this.cherries = 0;
    }

    public static void saveGame(int score, int cherries){
        savedGames.add(new User(score, cherries));
    }

    public static ArrayList<User> getSavedGames(){
        return savedGames;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }
}

