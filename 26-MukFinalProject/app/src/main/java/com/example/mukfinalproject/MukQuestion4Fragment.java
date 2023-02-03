package com.example.mukfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MukQuestion4Fragment extends MukQuestionFragment implements View.OnClickListener,
    MukCustomAdapter.MukOnItemClickListener {

    // Views
    TextView mukTimeLeftTextView;
    TextView mukQuestionTitleTextView;
    TextView mukQuestionTextView;
    RecyclerView mukRecyclerView;
    Button mukPreviousButton;
    Button mukNextButton;

    // Constants
    private final int MUK_QUESTION_INDEX = 3;

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
        View mukView = inflater.inflate(R.layout.fragment_muk_question4, container, false);

        // Get View References
        mukTimeLeftTextView = (TextView) mukView.findViewById(R.id.timeLeftTextView);
        mukQuestionTitleTextView = (TextView) mukView.findViewById(R.id.questionTitleTextView);
        mukQuestionTextView = (TextView) mukView.findViewById(R.id.questionTextView);

        mukRecyclerView = (RecyclerView) mukView.findViewById(R.id.questionOptionsRecyclerView);
        GridLayoutManager mukGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mukRecyclerView.setLayoutManager(mukGridLayoutManager);

        MukCustomAdapter mukCustomAdapter = new MukCustomAdapter(getMukQuestion());
        mukRecyclerView.setAdapter(mukCustomAdapter);

        mukPreviousButton = (Button) mukView.findViewById(R.id.previousButton);
        mukNextButton = (Button) mukView.findViewById(R.id.nextButton);

        // Add Listeners
        mukPreviousButton.setOnClickListener(this);
        mukNextButton.setOnClickListener(this);
        mukCustomAdapter.setMukOnItemClickListener(this);

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

    // MukCustomAdapter OnItemClickListener Methods
    @Override
    public void mukOnItemClick(MukCustomAdapter mukCustomAdapter, int mukPosition) {
        if(getMukQuestion() == null)
            return;

        getMukQuestion().setMukChosenIndex(mukPosition);
        mukCustomAdapter.notifyDataSetChanged();
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