package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MukQuestion5Fragment extends MukQuestionFragment implements View.OnClickListener,
    AdapterView.OnItemClickListener {

    // Views
    TextView mukTimeLeftTextView;
    TextView mukQuestionTitleTextView;
    TextView mukQuestionTextView;
    ListView mukQuestionOptionsListView;
    Button mukPreviousButton;
    Button mukNextButton;

    // Constants
    private final int MUK_QUESTION_INDEX = 4;

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
        View mukView = inflater.inflate(R.layout.fragment_muk_question5, container, false);

        // Get View References
        mukTimeLeftTextView = (TextView) mukView.findViewById(R.id.timeLeftTextView);
        mukQuestionTitleTextView = (TextView) mukView.findViewById(R.id.questionTitleTextView);
        mukQuestionTextView = (TextView) mukView.findViewById(R.id.questionTextView);

        mukQuestionOptionsListView = (ListView) mukView.findViewById(R.id.questionOptionsListView);
        mukQuestionOptionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> mukArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_checked,
                getMukQuestion().getMukOptions());
        mukQuestionOptionsListView.setAdapter(mukArrayAdapter);

        mukPreviousButton = (Button) mukView.findViewById(R.id.previousButton);
        mukNextButton = (Button) mukView.findViewById(R.id.nextButton);

        // Add Listeners
        mukQuestionOptionsListView.setOnItemClickListener(this);
        mukPreviousButton.setOnClickListener(this);
        mukNextButton.setOnClickListener(this);

        updateUI();

        return mukView;
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(getMukQuestion() != null)
            getMukQuestion().setMukChosenIndex(position);
    }

    // Utilities
    private void updateUI() {
        if(getMukQuestion() == null)
            return;

        int mukQuestionNumber = MUK_QUESTION_INDEX + 1;
        int mukTotalQuestions = getMukTest().getMukQuestions().size();
        mukQuestionTitleTextView.setText("Question " + mukQuestionNumber + " of " + mukTotalQuestions);
        mukQuestionTextView.setText(getMukQuestion().getMukQuestionText());

        mukUpdateTimeLeftTextView();
    }

    public void mukUpdateTimeLeftTextView() {
        if(mukTimeLeftTextView != null)
            mukTimeLeftTextView.setText("Time Left: " + mukGetFormattedTimeLeft());
    }
}