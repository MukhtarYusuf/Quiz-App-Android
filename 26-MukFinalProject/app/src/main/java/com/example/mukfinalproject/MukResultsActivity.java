package com.example.mukfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MukResultsActivity extends AppCompatActivity implements View.OnClickListener {

    // Views
    private ImageView mukImageView;
    private TextView mukMessageTextView;
    private TextView mukNameTextView;
    private TextView mukTimeTakenTextView;
    private TextView mukScoreTextView;
    private LinearLayout mukGoodScoreLinearLayout;
    private Button mukSaveScoreButton;
    private Button mukScoreHistoryButton;
    private Button mukTryAgainButton;

    // Variables
    private MukTest mukTest;

    // Activity Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muk_results);

        // Get View References
        mukImageView = (ImageView) findViewById(R.id.imageView);
        mukMessageTextView = (TextView) findViewById(R.id.messageTextView);
        mukNameTextView = (TextView) findViewById(R.id.nameTextView);
        mukTimeTakenTextView = (TextView) findViewById(R.id.timeTakenTextView);
        mukScoreTextView = (TextView) findViewById(R.id.scoreTextView);
        mukGoodScoreLinearLayout = (LinearLayout) findViewById(R.id.goodScoreLinearLayout);
        mukSaveScoreButton = (Button) findViewById(R.id.saveScoreButton);
        mukScoreHistoryButton = (Button) findViewById(R.id.scoreHistoryButton);
        mukTryAgainButton = (Button) findViewById(R.id.tryAgainButton);

        // Add Listeners
        mukSaveScoreButton.setOnClickListener(this);
        mukScoreHistoryButton.setOnClickListener(this);
        mukTryAgainButton.setOnClickListener(this);

        // Get Test
        Intent mukIntent = getIntent();
        mukTest = (MukTest) mukIntent.getSerializableExtra("mukTest");

        updateUI();
    }

    // View OnClickListener Methods
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.saveScoreButton:
                mukSaveScore();
                break;
            case R.id.scoreHistoryButton:
                mukGoToTestHistory();
                break;
            case R.id.tryAgainButton:
                mukTryAgain();
        }
    }

    // Utilities
    private void updateUI() {
        if(mukTest == null)
            return;

        String mukScoreString = "";
        String mukMessage = "";
        int mukTotalQuestions = mukTest.getMukQuestions().size();
        int mukCorrectCount = mukTest.mukGetCorrectCount();

        if(mukCorrectCount <= 2) {
            mukMessage = "Please Try Again!";
            mukImageView.setImageResource(R.drawable.try_again_icon);
        }
        else if(mukCorrectCount == 3) {
            mukMessage = "Good Job!";
            mukImageView.setImageResource(R.drawable.bronze_medal_logo);
        }
        else if(mukCorrectCount == 4) {
            mukMessage = "Excellent Work!";
            mukImageView.setImageResource(R.drawable.silver_medal_logo);
        }
        else {
            mukMessage = "You're a Genius!";
            mukImageView.setImageResource(R.drawable.gold_medal_logo);
        }

        mukScoreString = mukCorrectCount + "/" + mukTotalQuestions;

        mukMessageTextView.setText(mukMessage);
        mukNameTextView.setText("Mukhtar Yusuf");
        mukTimeTakenTextView.setText("Time Taken: " + mukGetFormattedTimeTaken());
        mukScoreTextView.setText("Score: " + mukScoreString);

        if(mukCorrectCount <= 2) {
            mukGoodScoreLinearLayout.setVisibility(View.GONE);
            mukTryAgainButton.setVisibility(View.VISIBLE);
        } else {
            mukGoodScoreLinearLayout.setVisibility(View.VISIBLE);
            mukTryAgainButton.setVisibility(View.GONE);
        }
    }

    private void mukSaveScore() {
        MukTestHistory mukTestHistory = new MukTestHistory(this);
        mukTestHistory.addAndSave(mukTest);
        mukSaveScoreButton.setEnabled(false);
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    private void mukGoToTestHistory() {
        Intent mukIntent = new Intent(this, MukTestHistoryActivity.class);
        startActivity(mukIntent);
    }

    private void mukTryAgain() {
        Intent mukIntent = new Intent();
        mukIntent.putExtra("mukIsTryAgain", true);
        setResult(RESULT_OK, mukIntent);

        finish();
    }

    private String mukGetFormattedTimeTaken() {
        String mukFormattedTimeTaken = "";
        if(mukTest == null)
            return mukFormattedTimeTaken;

        int mukTimeTaken = mukTest.mukGetTimeTaken();
        int mukHours = mukTimeTaken / 3600;
        int mukMinutes = (mukTimeTaken % 3600) / 60;
        int mukSeconds = (mukTimeTaken % 3600) % 60;

        String mukHoursString = mukHours == 0 ? "" : mukHours + "h ";
        String mukMinutesString = mukMinutes == 0 && mukHours == 0 ? "" : mukMinutes + "m ";
        String mukSecondsString = "" + mukSeconds + "s";
        mukFormattedTimeTaken = mukHoursString + mukMinutesString + mukSecondsString;

        return mukFormattedTimeTaken;
    }
}