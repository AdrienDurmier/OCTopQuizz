package fr.les_applis_gaillardes.topquizz.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.les_applis_gaillardes.topquizz.R;
import fr.les_applis_gaillardes.topquizz.model.HighScore;
import fr.les_applis_gaillardes.topquizz.model.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton, mScoreButton;
    private User mUser;
    private HighScore mHighScore;
    private SharedPreferences mPreferences;

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";
    public static final String PREF_KEY_SCORETAB = "PREF_KEY_SCORETAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();
        mPreferences = getPreferences(MODE_PRIVATE);
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mPlayButton.setEnabled(false);
        mScoreButton = findViewById(R.id.activity_main_score_btn);

        Gson gson = new Gson();

        String json = mPreferences.getString(PREF_KEY_SCORETAB,null);
        if (json != null) {
            mHighScore = gson.fromJson(json,HighScore.class);
            mScoreButton.setEnabled(true);
        } else {
            mHighScore = new HighScore();
            mScoreButton.setEnabled(false);
        }

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Evenement pour démarrer le jeu
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = mNameInput.getText().toString();
                mUser.setFirstname(firstname);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstname()).apply();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

        // Evenement pour démarrer l'activity des meilleurs scores
        mScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreActivityIntent = new Intent(MainActivity.this, ScoreActivity.class);
                scoreActivityIntent.putExtra("Scores", mHighScore);
                startActivity(scoreActivityIntent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            // Score
            mUser.setScore(score);
            mHighScore.addScore(mUser);
            final Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .create();
            String json = gson.toJson(mHighScore);
            mPreferences.edit().putString(PREF_KEY_SCORETAB, json).apply();

            greetUser();
        }
    }

    private void greetUser(){
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if(firstname != null){
            int score = mPreferences.getInt(PREF_KEY_SCORE,0);

            String fulltext = "Welcome back, " + firstname + " !"
                    + "\nYour last score was " + score
                    + "\n will you do better this time?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }
}
