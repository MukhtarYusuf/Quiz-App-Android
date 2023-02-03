package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MukTestInfoFragment extends Fragment implements View.OnClickListener {

    // Fragment Interface (To talk back to Activity)
    interface MukTestInfoFragmentListener {
        public void mukOnStartTest(MukTestInfoFragment mukTestInfoFragment);
    }

    // Views
    private TextView mukNameTextView;
    private TextView mukTestNameTextView;
    private TextView mukNumOfQuestionsTextView;
    private TextView mukTotalTimeTextView;
    private Button mukStartTestButton;

    // Variables
    private MukTest mukTest;
    private MukTestInfoFragmentListener mukTestInfoFragmentListener;

    // Fragment Lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mukView = inflater.inflate(R.layout.fragment_muk_test_info, container, false);

        // Instantiate Views
        mukNameTextView = (TextView) mukView.findViewById(R.id.nameTextView);
        mukTestNameTextView = (TextView) mukView.findViewById(R.id.testTextView);
        mukNumOfQuestionsTextView = (TextView) mukView.findViewById(R.id.questionsTextView);
        mukTotalTimeTextView = (TextView) mukView.findViewById(R.id.timeTextView);
        mukStartTestButton = (Button) mukView.findViewById(R.id.startTestButton);

        // Set up Listeners
        mukStartTestButton.setOnClickListener(this);

        updateUI();

        return mukView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mukTestInfoFragmentListener = null;
    }

    // View OnClickListener Methods
    @Override
    public void onClick(View v) {
        if(mukTestInfoFragmentListener != null) {
            mukTestInfoFragmentListener.mukOnStartTest(this);
        }
    }

    // Utilities
    private void updateUI() {
        if(mukTest == null)
            return;

        mukNameTextView.setText("Name: Mukhtar Yusuf");
        mukTestNameTextView.setText("Test: " + mukTest.getMUK_TEST_NAME());
        mukNumOfQuestionsTextView.setText("No. of Questions: " + mukTest.getMukQuestions().size());
        mukTotalTimeTextView.setText("Time Limit: " + mukGetFormattedTime());
    }

    protected String mukGetFormattedTime() {
        String mukFormattedTime = "";
        if(mukTest == null)
            return mukFormattedTime;

        int mukTimeLeft = mukTest.getMukTimeLeft();
        int mukHours = mukTimeLeft / 3600;
        int mukMinutes = (mukTimeLeft % 3600) / 60;
        int mukSeconds = (mukTimeLeft % 3600) % 60;

        String mukHoursString = mukHours == 0 ? "" : mukHours + "h ";
        String mukMinutesString = mukMinutes == 0 && mukHours == 0 ? "" : mukMinutes + "m ";
        String mukSecondsString = "" + mukSeconds + "s";
        mukFormattedTime = mukHoursString + mukMinutesString + mukSecondsString;

        return mukFormattedTime;
    }

    // Getters and Setters
    public MukTest getMukTest() {
        return mukTest;
    }

    public void setMukTest(MukTest mukTest) {
        this.mukTest = mukTest;
    }

    public MukTestInfoFragmentListener getMukTestInfoFragmentListener() {
        return mukTestInfoFragmentListener;
    }

    public void setMukTestInfoFragmentListener(MukTestInfoFragmentListener mukTestInfoFragmentListener) {
        this.mukTestInfoFragmentListener = mukTestInfoFragmentListener;
    }

}