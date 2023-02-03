package com.example.mukfinalproject;

import android.util.Log;

import java.io.Serializable;

public class MukQuestion implements Serializable {

    // Variables
    private int mukAnswerIndex;
    private int mukChosenIndex;
    private String mukQuestionText;
    private String[] mukOptions;

    // Constructors
    public MukQuestion(int mukAnswerIndex, String mukQuestionText, String[] mukOptions) {
        this.mukAnswerIndex = mukAnswerIndex;
        this.mukChosenIndex = -1;
        this.mukQuestionText = mukQuestionText;
        this.mukOptions = mukOptions; // Reference pass is ok here
    }

    // Utilities
    public boolean mukIsRightAnswer() {
        return mukChosenIndex == mukAnswerIndex;
    }

    // Getters and Setters
    public int getMukAnswerIndex() {
        return mukAnswerIndex;
    }

    public void setMukAnswerIndex(int mukAnswerIndex) {
        this.mukAnswerIndex = mukAnswerIndex;
    }

    public int getMukChosenIndex() {
        return mukChosenIndex;
    }

    public void setMukChosenIndex(int mukChosenIndex) {
        if(mukChosenIndex >= 0 && mukChosenIndex < mukOptions.length) {
            if (this.mukChosenIndex == mukChosenIndex && this.mukChosenIndex != -1) // Deselect if Already Selected
                this.mukChosenIndex = -1;
            else
                this.mukChosenIndex = mukChosenIndex;
        }
        Log.d(MukQuestion.class.getSimpleName(), "Right Answer: " + mukIsRightAnswer());
    }

    public String getMukQuestionText() {
        return mukQuestionText;
    }

    public void setMukQuestionText(String mukQuestionText) {
        this.mukQuestionText = mukQuestionText;
    }

    public String[] getMukOptions() {
        return mukOptions;
    }

    public void setMukOptions(String[] mukOptions) {
        this.mukOptions = mukOptions;
    }

}
