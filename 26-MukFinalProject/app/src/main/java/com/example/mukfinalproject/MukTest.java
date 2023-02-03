package com.example.mukfinalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MukTest implements Serializable {

    // Constants
    private final int MUK_TOTAL_TIME = 120;
    private final String MUK_TEST_NAME = "Android Test";

    // Variables
    private ArrayList<MukQuestion> mukQuestions;
    private int mukTimeLeft;
    private Date mukDateTaken;
    private boolean mukIsTryingAgain;
    static private Timer mukTimer;
    static private Context mukContext;

    // Constructors
    public MukTest(Context mukContext) {
        mukTimeLeft = MUK_TOTAL_TIME;
        mukQuestions = new ArrayList<>();
        mukDateTaken = new Date();
        Log.d(MukTest.class.getSimpleName(), mukGetFormattedDate());

        ArrayList<MukQuestion> mukQuestionBank = mukGetQuestionBank();
        for(int i = 0; i < 4; i++) { // Generate Random Questions
            Random mukRandom = new Random();
            int mukUpperBound = mukQuestionBank.size() - 1; // Exclude the Last Question
            int mukIndex = mukRandom.nextInt(mukUpperBound);

            mukQuestions.add(mukQuestionBank.remove(mukIndex)); // Add last (fixed) Question
        }

        int mukLastIndex = mukQuestionBank.size()-1;
        mukQuestions.add(3, mukQuestionBank.remove(mukLastIndex)); // Add Fixed Question at index 3

        MukTest.mukContext = mukContext;
    }

    // Instance Methods
    public void mukStartTimer() {
        TimerTask mukTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(mukTimeLeft > 0) {
                    mukTimeLeft--;
                    LocalBroadcastManager mukManager = LocalBroadcastManager.getInstance(mukContext);
                    Intent mukIntent = new Intent("MUK_CUSTOM_ACTION");

                    mukManager.sendBroadcast(mukIntent);
                }
            }
        };
        mukTimer = new Timer(true);
        mukTimer.schedule(mukTimerTask, 0, 1000);
    }

    public void mukStopTimer() {
        mukTimer.cancel();
    }

    public String mukGetFormattedDate() {
        String mukFormattedDate = String.format("%te %<tb, %<tY", mukDateTaken);

        return mukFormattedDate;
    }

    // Utilities
    private String[] mukGetQuestionTexts() {
        String[] mukQuestionTexts = {
                "How is memory managed in Android?",
                "Which of the following is true about making a networking request in Android?",
                "When a button is tapped, which method is called?",
                "The progress for the SeekBar is what dataType?",
                "The name of the class used to populate is spinner is known as:",
                "The weight of a view can also be used to determine:",
                "Which one of these is not a ViewGroup in Android?",
                "Which one of these classes is used to transition between Activities?",
                "To get a View from a layout from within an Activity, you can call:",
                "Which of the following is the icon for the Android IDE?"
        };

        return mukQuestionTexts;
    }

    private String[][] mukGetQuestionsOptions() {
        String[][] mukQuestionsOptions = {
                {"A. Automatic Value Counting", "B. Automatic Reference Counting", "C. Garbage Collection", "D. Manually"},
                {"A. Should be Done on the Main Thread", "B. Should be Done Asynchronously", "C. Should be Done on the UI Thread", "D. Should be Done Synchronously"},
                {"A. onTap()", "B. onSelect()", "C. onClick()", "D. onAction()"},
                {"A. double", "B. String", "C. int", "D. float"},
                {"A. ArrayAdapter", "B. SpinnerSource", "C. DataGetter", "D. SpinnerListener"},
                {"A. The Shadow", "B. The Elevation", "C. The Color", "D. The Width/Height"},
                {"A. ListView", "B. TextView", "C. LinearLayout", "D. RelativeLayout"},
                {"A. Segue", "B. Intent", "C. BroadcastReceiver", "D. Service"},
                {"A. getView()", "B. getWidget()", "C. findViewById()", "D. getViewById()"},
                {"Xcode", "Android Studio", "Visual Studio", "Ruby Mine"}
        };

        return mukQuestionsOptions;
    }

    private int[] mukGetQuestionAnswers() {
        int[] mukQuestionAnswers = {2, 1, 2, 2, 0, 3, 1, 1, 2, 1};
        return mukQuestionAnswers;
    }

    private ArrayList<MukQuestion> mukGetQuestionBank() {
        ArrayList<MukQuestion> mukQuestionBank = new ArrayList<>();

        String[] mukQuestionTexts = mukGetQuestionTexts();
        String[][] mukQuestionsOptions = mukGetQuestionsOptions();
        int[] mukQuestionAnswers = mukGetQuestionAnswers();

        for(int i = 0; i < mukQuestionTexts.length; i++) {
            String mukQuestionText = mukQuestionTexts[i];
            String[] mukQuestionOptions = mukQuestionsOptions[i];
            int mukQuestionAnswer = mukQuestionAnswers[i];

            MukQuestion mukQuestion = new MukQuestion(mukQuestionAnswer, mukQuestionText, mukQuestionOptions);
            mukQuestionBank.add(mukQuestion);
        }

        return mukQuestionBank;
    }

    public int mukGetTimeTaken() {
        return MUK_TOTAL_TIME - mukTimeLeft;
    }

    public int mukGetCorrectCount() {
        int mukCorrectCount = 0;
        for(int i = 0; i < mukQuestions.size(); i++) {
            MukQuestion mukQuestion = mukQuestions.get(i);
            if(mukQuestion.getMukChosenIndex() == mukQuestion.getMukAnswerIndex())
                mukCorrectCount++;
        }

        return mukCorrectCount;
    }

    // Getters and Setters
    public int getMUK_TOTAL_TIME() {
        return MUK_TOTAL_TIME;
    }

    public String getMUK_TEST_NAME() {
        return MUK_TEST_NAME;
    }

    public ArrayList<MukQuestion> getMukQuestions() {
        return mukQuestions;
    }

    public void setMukQuestions(ArrayList<MukQuestion> mukQuestions) {
        this.mukQuestions = mukQuestions;
    }

    public int getMukTimeLeft() {
        return mukTimeLeft;
    }

    public void setMukTimeLeft(int mukTimeLeft) {
        this.mukTimeLeft = mukTimeLeft;
    }

    public boolean getMukIsTryingAgain() {
        return mukIsTryingAgain;
    }

    public void setMukIsTryingAgain(boolean mukIsTryingAgain) {
        this.mukIsTryingAgain = mukIsTryingAgain;
    }

}
