package com.example.mukfinalproject;

import androidx.fragment.app.Fragment;

public class MukQuestionFragment extends Fragment {

    interface MukQuestionFragmentListener {
        public void mukOnPreviousClicked(MukQuestionFragment mukQuestionFragment);
        public void mukOnNextClicked(MukQuestionFragment mukQuestionFragment);
    }

    // Variables
    private MukTest mukTest;
    private MukQuestion mukQuestion;
    private MukQuestionFragmentListener mukQuestionFragmentListener;

    // Fragment Lifecycle
//    @Override
//    public void onDestroy() { // Is This Really Needed?
//        super.onDestroy();
//        mukQuestionFragmentListener = null;
//    }

    // Utilities
    protected String mukGetFormattedTimeLeft() {
        String mukFormattedTimeLeft = "";
        if(mukTest == null)
            return mukFormattedTimeLeft;

        int mukTimeLeft = mukTest.getMukTimeLeft();
        int mukHours = mukTimeLeft / 3600;
        int mukMinutes = (mukTimeLeft % 3600) / 60;
        int mukSeconds = (mukTimeLeft % 3600) % 60;

        String mukHoursString = mukHours == 0 ? "" : mukHours + "h ";
        String mukMinutesString = mukMinutes == 0 && mukHours == 0 ? "" : mukMinutes + "m ";
        String mukSecondsString = "" + mukSeconds + "s";
        mukFormattedTimeLeft = mukHoursString + mukMinutesString + mukSecondsString;

        return mukFormattedTimeLeft;
    }

    // Getters and Setters
    public MukTest getMukTest() {
        return mukTest;
    }

    public void setMukTest(MukTest mukTest) {
        this.mukTest = mukTest;
    }

    public MukQuestion getMukQuestion() {
        return mukQuestion;
    }

    public void setMukQuestion(MukQuestion mukQuestion) {
        this.mukQuestion = mukQuestion;
    }

    public MukQuestionFragmentListener getMukQuestionFragmentListener() {
        return mukQuestionFragmentListener;
    }

    public void setMukQuestionFragmentListener(MukQuestionFragmentListener mukQuestionFragmentListener) {
        this.mukQuestionFragmentListener = mukQuestionFragmentListener;
    }

}
