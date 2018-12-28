package fr.les_applis_gaillardes.topquizz.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.les_applis_gaillardes.topquizz.R;
import fr.les_applis_gaillardes.topquizz.model.HighScore;
import fr.les_applis_gaillardes.topquizz.model.User;

public class ScoreActivity extends AppCompatActivity {

    private TextView mRank1, mRank2, mRank3, mRank4, mRank5;
    private Button btnOrderScore, btnOrderAlpha;
    private HighScore mHighScore;
    private List<TextView> textViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Initialisation
        mRank1 = findViewById(R.id.activity_score_rank1_text);
        mRank2 = findViewById(R.id.activity_score_rank2_text);
        mRank3 = findViewById(R.id.activity_score_rank3_text);
        mRank4 = findViewById(R.id.activity_score_rank4_text);
        mRank5 = findViewById(R.id.activity_score_rank5_text);
        btnOrderScore = findViewById(R.id.activity_score_score_btn);
        btnOrderAlpha = findViewById(R.id.activity_score_alpha_btn);

        // Stock les scores dans un tableau
        textViewList = new ArrayList<>();
        textViewList.add(mRank1);
        textViewList.add(mRank2);
        textViewList.add(mRank3);
        textViewList.add(mRank4);
        textViewList.add(mRank5);

        // Récupère l'extra score
        Intent i = getIntent();
        mHighScore = (HighScore) i.getSerializableExtra("Scores");
        // Tableau des 5 meilleurs
        ArrayList<User> scoresFive = Utilities.tableau5Scores(mHighScore);
        Collections.sort(scoresFive, new User());
        Collections.reverse(scoresFive);

        // Update la table highScore
        if (mHighScore != null){
            Utilities.updateTab(mHighScore.getScoresTab(), textViewList);
        }

        // Bouton permettant le tri par rapport au score
        btnOrderScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mHighScore != null) {
                    // Creation de la table des 5 meilleurs
                    ArrayList<User> scoresFive;
                    scoresFive = Utilities.tableau5Scores(mHighScore);

                    // Sort out the scores of the biggest in the smallest
                    Collections.sort(scoresFive,new User());
                    Collections.reverse(scoresFive);

                    // Display the scores tab
                    Utilities.updateTab(scoresFive, textViewList);
                }
            }
        });

        // Bouton permettant le tri par rapport au nom
        btnOrderAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mHighScore != null) {
                    // Creation de la table des 5 meilleurs scores
                    ArrayList<User> scoresFive;
                    scoresFive = Utilities.tableau5Scores(mHighScore);
                    // Sort the scores tab by name
                    Collections.sort(scoresFive);
                    // Display the scores tab
                    Utilities.updateTab(scoresFive, textViewList);
                }
            }
        });
    }
}
