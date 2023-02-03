package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MukQuestion1Fragment extends MukQuestionFragment implements View.OnClickListener {

    // Views
    TextView mukTimeLeftTextView;
    TextView mukQuestionTitleTextView;
    TextView mukQuestionTextView;
    Button mukQuestionOption1Button;
    Button mukQuestionOption2Button;
    Button mukQuestionOption3Button;
    Button mukQuestionOption4Button;
    Button mukNextButton;

    // Constants
    private final int MUK_QUESTION_INDEX = 0;

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
        View mukView = inflater.inflate(R.layout.fragment_muk_question1, container, false);

        // Get View References
        mukTimeLeftTextView = (TextView) mukView.findViewById(R.id.timeLeftTextView);
        mukQuestionTitleTextView = (TextView) mukView.findViewById(R.id.questionTitleTextView);
        mukQuestionTextView = (TextView) mukView.findViewById(R.id.questionTextView);
        mukQuestionOption1Button = (Button) mukView.findViewById(R.id.questionOption1Button);
        mukQuestionOption2Button = (Button) mukView.findViewById(R.id.questionOption2Button);
        mukQuestionOption3Button = (Button) mukView.findViewById(R.id.questionOption3Button);
        mukQuestionOption4Button = (Button) mukView.findViewById(R.id.questionOption4Button);
//        mukPreviousButton = (Button) mukView.findViewById(R.id.previousButton);
        mukNextButton = (Button) mukView.findViewById(R.id.nextButton);

        // Add Listeners
        mukQuestionOption1Button.setOnClickListener(this);
        mukQuestionOption2Button.setOnClickListener(this);
        mukQuestionOption3Button.setOnClickListener(this);
        mukQuestionOption4Button.setOnClickListener(this);
//        mukPreviousButton.setOnClickListener(this);
        mukNextButton.setOnClickListener(this);

        updateUI();

        return mukView;
    }

    // View OnClickListener Methods
    @Override
    public void onClick(View v) {
        if(getMukQuestion() == null)
            return;

        int mukChosenIndex = -1;
        switch (v.getId()) {
            case R.id.questionOption1Button:
                mukChosenIndex = 0;
                break;
            case R.id.questionOption2Button:
                mukChosenIndex = 1;
                break;
            case R.id.questionOption3Button:
                mukChosenIndex = 2;
                break;
            case R.id.questionOption4Button:
                mukChosenIndex = 3;
                break;
            case R.id.nextButton:
                if(getMukQuestionFragmentListener() != null) {
                    getMukQuestionFragmentListener().mukOnNextClicked(this);
                }
                break;
        }
        getMukQuestion().setMukChosenIndex(mukChosenIndex);
        mukUpdateOptionButtons();
    }

    // Utilities
    private void updateUI() {
        if(getMukQuestion() == null)
            return;

        int mukQuestionNumber = MUK_QUESTION_INDEX + 1;
        int mukTotalQuestions = getMukTest().getMukQuestions().size();
        mukQuestionTitleTextView.setText("Question " + mukQuestionNumber + " of " + mukTotalQuestions);
        mukQuestionTextView.setText(getMukQuestion().getMukQuestionText());
        mukQuestionOption1Button.setText(getMukQuestion().getMukOptions()[0]);
        mukQuestionOption2Button.setText(getMukQuestion().getMukOptions()[1]);
        mukQuestionOption3Button.setText(getMukQuestion().getMukOptions()[2]);
        mukQuestionOption4Button.setText(getMukQuestion().getMukOptions()[3]);

        mukUpdateTimeLeftTextView();
        mukUpdateOptionButtons();
    }

    public void mukUpdateTimeLeftTextView() {
        if(mukTimeLeftTextView != null)
            mukTimeLeftTextView.setText("Time Left: " + mukGetFormattedTimeLeft());
    }

    private void mukUpdateOptionButtons() {
        if(getMukQuestion() == null)
            return;

        mukUpdateButton(mukQuestionOption1Button, false);
        mukUpdateButton(mukQuestionOption2Button, false);
        mukUpdateButton(mukQuestionOption3Button, false);
        mukUpdateButton(mukQuestionOption4Button, false);

        int mukChosenIndex = getMukQuestion().getMukChosenIndex();
        if(mukChosenIndex == 0)
            mukUpdateButton(mukQuestionOption1Button, true);
        else if(mukChosenIndex == 1)
            mukUpdateButton(mukQuestionOption2Button, true);
        else if(mukChosenIndex == 2)
            mukUpdateButton(mukQuestionOption3Button, true);
        else if(mukChosenIndex == 3)
            mukUpdateButton(mukQuestionOption4Button, true);
    }

    private void mukUpdateButton(Button mukButton, boolean mukSelected) {
        if(mukSelected) {
            mukButton.setBackgroundColor(getResources().getColor(R.color.lambton_purple));
            mukButton.setTextColor(getResources().getColor(R.color.white));
        } else {
            mukButton.setBackgroundColor(getResources().getColor(R.color.white));
            mukButton.setTextColor(getResources().getColor(R.color.lambton_purple));
        }
    }

}