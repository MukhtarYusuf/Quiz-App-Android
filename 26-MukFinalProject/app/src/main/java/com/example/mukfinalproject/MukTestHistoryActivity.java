package com.example.mukfinalproject;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class MukTestHistoryActivity extends AppCompatActivity {

    // Views
    private TextView mukScoreHistoryTextView;
    private ListView mukScoreHistoryListView;

    // Variables
    private MukTestHistory mukTestHistory;

    // Activity Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muk_test_history);

        // Initialize Variables
        mukTestHistory = new MukTestHistory(this);

        // Get View References
        mukScoreHistoryTextView = (TextView) findViewById(R.id.scoreHistoryTextView);
        mukScoreHistoryListView = (ListView) findViewById(R.id.scoreHistoryListView);

        // Setup ListView
        ArrayList<HashMap<String, String>> mukAdapterData = new ArrayList<>();
        ArrayList<MukTest> mukTests = mukTestHistory.getMukTests();

        for(int i = 0; i < mukTests.size(); i++) {
            HashMap<String, String> mukMap = new HashMap<>();
            MukTest mukTest = mukTests.get(i);
            String mukIndexString = "" + (i + 1);
            String mukScoreString = mukTest.mukGetCorrectCount() + " / " + mukTest.getMukQuestions().size();
            String mukDateString = mukTest.mukGetFormattedDate();

            mukMap.put("index", mukIndexString);
            mukMap.put("score", mukScoreString);
            mukMap.put("date", mukDateString);

            mukAdapterData.add(mukMap);
        }

        int mukResource = R.layout.score_history_row_layout;
        String[] mukFrom = {"index", "score", "date"};
        int[] mukTo = {R.id.scoreIndexTextView, R.id.scoreTextView, R.id.dateTextView};

        SimpleAdapter mukSimpleAdapter = new SimpleAdapter(this, mukAdapterData, mukResource, mukFrom, mukTo);
        mukScoreHistoryListView.setAdapter(mukSimpleAdapter);
    }

}