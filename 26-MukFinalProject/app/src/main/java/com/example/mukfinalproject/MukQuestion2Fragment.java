package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MukQuestion2Fragment extends MukQuestionFragment implements CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {

    // Views
    TextView mukTimeLeftTextView;
    TextView mukQuestionTitleTextView;
    TextView mukQuestionTextView;
    CheckBox mukQuestionOption1CheckBox;
    CheckBox mukQuestionOption2CheckBox;
    CheckBox mukQuestionOption3CheckBox;
    CheckBox mukQuestionOption4CheckBox;
    Button mukPreviousButton;
    Button mukNextButton;

    // Constants
    private final int MUK_QUESTION_INDEX = 1;

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
        View mukView = inflater.inflate(R.layout.fragment_muk_question2, container, false);

        // Get View References
        mukTimeLeftTextView = (TextView) mukView.findViewById(R.id.timeLeftTextView);
        mukQuestionTitleTextView = (TextView) mukView.findViewById(R.id.questionTitleTextView);
        mukQuestionTextView = (TextView) mukView.findViewById(R.id.questionTextView);
        mukQuestionOption1CheckBox = (CheckBox) mukView.findViewById(R.id.questionOption1CheckBox);
        mukQuestionOption2CheckBox = (CheckBox) mukView.findViewById(R.id.questionOption2CheckBox);
        mukQuestionOption3CheckBox = (CheckBox) mukView.findViewById(R.id.questionOption3CheckBox);
        mukQuestionOption4CheckBox = (CheckBox) mukView.findViewById(R.id.questionOption4CheckBox);
        mukPreviousButton = (Button) mukView.findViewById(R.id.previousButton);
        mukNextButton = (Button) mukView.findViewById(R.id.nextButton);

        // Add Listeners
        mukQuestionOption1CheckBox.setOnCheckedChangeListener(this);
        mukQuestionOption2CheckBox.setOnCheckedChangeListener(this);
        mukQuestionOption3CheckBox.setOnCheckedChangeListener(this);
        mukQuestionOption4CheckBox.setOnCheckedChangeListener(this);
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

    // CompoundButton OnCheckChangedListener Methods
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(getMukQuestion() == null || !buttonView.isPressed()) // Don't Execute if Caused Programmatically
            return;

        int mukChosenIndex = -1;
        switch(buttonView.getId()) {
            case R.id.questionOption1CheckBox:
                mukChosenIndex = 0;
                break;
            case R.id.questionOption2CheckBox:
                mukChosenIndex = 1;
                break;
            case R.id.questionOption3CheckBox:
                mukChosenIndex = 2;
                break;
            case R.id.questionOption4CheckBox:
                mukChosenIndex = 3;
                break;
        }
        getMukQuestion().setMukChosenIndex(mukChosenIndex);
        mukUpdateOptionCheckboxes();
    }

    // Utilities
    private void updateUI() {
        if(getMukQuestion() == null)
            return;

        int mukQuestionNumber = MUK_QUESTION_INDEX + 1;
        int mukTotalQuestions = getMukTest().getMukQuestions().size();
        mukQuestionTitleTextView.setText("Question " + mukQuestionNumber + " of " + mukTotalQuestions);
        mukQuestionTextView.setText(getMukQuestion().getMukQuestionText());
        mukQuestionOption1CheckBox.setText(getMukQuestion().getMukOptions()[0]);
        mukQuestionOption2CheckBox.setText(getMukQuestion().getMukOptions()[1]);
        mukQuestionOption3CheckBox.setText(getMukQuestion().getMukOptions()[2]);
        mukQuestionOption4CheckBox.setText(getMukQuestion().getMukOptions()[3]);

        mukUpdateTimeLeftTextView();
        mukUpdateOptionCheckboxes();
    }

    private void mukUpdateOptionCheckboxes() {
        int mukChosenIndex = getMukQuestion().getMukChosenIndex();
        if(mukChosenIndex != 0)
            mukQuestionOption1CheckBox.setChecked(false);

        if(mukChosenIndex != 1)
            mukQuestionOption2CheckBox.setChecked(false);

        if(mukChosenIndex != 2)
            mukQuestionOption3CheckBox.setChecked(false);

        if(mukChosenIndex != 3)
            mukQuestionOption4CheckBox.setChecked(false);
    }

    public void mukUpdateTimeLeftTextView() {
        if(mukTimeLeftTextView != null)
            mukTimeLeftTextView.setText("Time Left: " + mukGetFormattedTimeLeft());
    }
}