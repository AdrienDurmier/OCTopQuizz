package fr.les_applis_gaillardes.topquizz.controller;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.les_applis_gaillardes.topquizz.model.HighScore;
import fr.les_applis_gaillardes.topquizz.model.User;


public class Utilities {

    /**
     * Méthode pour créer un tableau des 5 meilleurs scores
     */
    public static ArrayList<User> tableau5Scores(HighScore mHighScore) {
        ArrayList<User> scoresFive = new ArrayList<User>();
        Collections.sort(mHighScore.getScoresTab(),new User());
        Collections.reverse(mHighScore.getScoresTab());
        for (int i = 0;i<mHighScore.getScoresTab().size();i++){
            scoresFive.add(mHighScore.getScoresTab().get(i));
        }
        return scoresFive;
    }

    /**
     * Méthode qui met à jour l'affichage du tableau de scores
     */
    public static void updateTab(List<User> userList, List<TextView> textViewList) {
        String name;
        int score, index = 0;
        for (TextView textView : textViewList) {
            if (index < userList.size() ) {
                if (userList.get(index) != null) {
                    name = userList.get(index).getFirstname();
                    score = userList.get(index).getScore();
                    textView.setText(name.toUpperCase() + " : " + score + " points");
                }
            } else {
                textView.setText("-");
                textView.setTextColor(Color.GRAY);
                textView.setTypeface(Typeface.DEFAULT,Typeface.ITALIC);
            }
            index++;
        }
    }

}
