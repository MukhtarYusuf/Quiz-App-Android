package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MukQuestion3Fragment extends MukQuestionFragment implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    // Views
    TextView mukTimeLeftTextView;
    TextView mukQuestionTitleTextView;
    TextView mukQuestionTextView;
    RadioGroup mukQuestionOptionsRadioGroup;
    RadioButton mukQuestionOption1RadioButton;
    RadioButton mukQuestionOption2RadioButton;
    RadioButton mukQuestionOption3RadioButton;
    RadioButton mukQuestionOption4RadioButton;
    Button mukPreviousButton;
    Button mukNextButton;

    // Constants
    private final int MUK_QUESTION_INDEX = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getMukTest() != null) {
            setMukQuestion(getMukTest().getMukQuestions().get(MUK_QUESTION_INDEX));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mukView = inflater.inflate(R.layout.fragment_muk_question3, container, false);

        // Get View References
        mukTimeLeftTextView = (TextView) mukView.findViewById(R.id.timeLeftTextView);
        mukQuestionTitleTextView = (TextView) mukView.findViewById(R.id.questionTitleTextView);
        mukQuestionTextView = (TextView) mukView.findViewById(R.id.questionTextView);
        mukQuestionOptionsRadioGroup = (RadioGroup) mukView.findViewById(R.id.questionOptionsRadioGroup);
        mukQuestionOption1RadioButton = (RadioButton) mukView.findViewById(R.id.questionOption1RadioButton);
        mukQuestionOption2RadioButton = (RadioButton) mukView.findViewById(R.id.questionOption2RadioButton);
        mukQuestionOption3RadioButton = (RadioButton) mukView.findViewById(R.id.questionOption3RadioButton);
        mukQuestionOption4RadioButton = (RadioButton) mukView.findViewById(R.id.questionOption4RadioButton);
        mukPreviousButton = (Button) mukView.findViewById(R.id.previousButton);
        mukNextButton = (Button) mukView.findViewById(R.id.nextButton);

        // Add Listeners
        mukQuestionOptionsRadioGroup.setOnCheckedChangeListener(this);
        mukPreviousButton.setOnClickListener(this);
        mukNextButton.setOnClickListener(this);

        updateUI();

        return mukView;
    }

    // View OnClickListener Methods
    @Override
    public void onClick(View v) {
        MukQuestionFragmentListener mukQuestionFragmentListener = getMukQuestionFragmentListener();
        if(mukQuestionFragmentListener == null)
            return;

        switch(v.getId()) {
            case R.id.previousButton:
                mukQuestionFragmentListener.mukOnPreviousClicked(this);
                break;
            case R.id.nextButton:
                mukQuestionFragmentListener.mukOnNextClicked(this);
                break;
        }
    }

    // RadioGroup OnCheckChangedListener Methods
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(getMukQuestion() == null)
            return;

        int mukChosenIndex = -1;
        switch(checkedId) {
            case R.id.questionOption1RadioButton:
                mukChosenIndex = 0;
                break;
            case R.id.questionOption2RadioButton:
                mukChosenIndex = 1;
                break;
            case R.id.questionOption3RadioButton:
                mukChosenIndex = 2;
                break;
            case R.id.questionOption4RadioButton:
                mukChosenIndex = 3;
                break;
        }
        getMukQuestion().setMukChosenIndex(mukChosenIndex);
    }

    // Utilities
    private void updateUI() {
        if(getMukQuestion() == null)
            return;

        int mukQuestionNumber = MUK_QUESTION_INDEX + 1;
        int mukTotalQuestions = getMukTest().getMukQuestions().size();
        mukQuestionTitleTextView.setText("Question " + mukQuestionNumber + " of " + mukTotalQuestions);
        mukQuestionTextView.setText(getMukQuestion().getMukQuestionText());
        mukQuestionOption1RadioButton.setText(getMukQuestion().getMukOptions()[0]);
        mukQuestionOption2RadioButton.setText(getMukQuestion().getMukOptions()[1]);
        mukQuestionOption3RadioButton.setText(getMukQuestion().getMukOptions()[2]);
        mukQuestionOption4RadioButton.setText(getMukQuestion().getMukOptions()[3]);

        mukUpdateTimeLeftTextView();
    }

    public void mukUpdateTimeLeftTextView() {
        if(mukTimeLeftTextView != null)
            mukTimeLeftTextView.setText("Time Left: " + mukGetFormattedTimeLeft());
    }
}