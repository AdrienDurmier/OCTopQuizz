package fr.les_applis_gaillardes.topquizz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighScore implements Serializable{

    private List<User> mHighScore;

    public HighScore() {
        mHighScore = new ArrayList<>();
    }

    public HighScore(ArrayList<User> scoresTab) {
        mHighScore = scoresTab;
    }

    public List<User> getScoresTab() {
        return mHighScore;
    }

    public void setScoresTab(List<User> scoresTab) {
        mHighScore = scoresTab;
    }

    // ajoute un nouveau score
    public void addScore(User user){
        mHighScore.add(user);
    }

}

