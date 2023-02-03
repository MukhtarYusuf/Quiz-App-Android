package com.example.mukfinalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity implements MukTestInfoFragment.MukTestInfoFragmentListener,
    MukQuestionFragment.MukQuestionFragmentListener {

    // Variables
    private MukTest mukTest;
    private MukTestInfoFragment mukTestInfoFragment;
    private MukQuestion1Fragment mukQuestion1Fragment;
    private MukQuestion2Fragment mukQuestion2Fragment;
    private MukQuestion3Fragment mukQuestion3Fragment;
    private MukQuestion4Fragment mukQuestion4Fragment;
    private MukQuestion5Fragment mukQuestion5Fragment;
    private BroadcastReceiver mukBroadcastReceiver;

    // Activity Lifecycle Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mukSetUp();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        if(mukTest.getMukIsTryingAgain())
            mukSetUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            boolean mukIsTryAgain = data.getBooleanExtra("mukIsTryAgain", false);
            mukTest.setMukIsTryingAgain(mukIsTryAgain);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mukBroadcastReceiver);
    }

    // MukTestInfoFragmentListener Methods
    @Override
    public void mukOnStartTest(MukTestInfoFragment mukTestInfoFragment) {
        mukTest.mukStartTimer();
        mukGoToQuestion1();
    }

    // MukQuestionFragmentListener Methods
    @Override
    public void mukOnPreviousClicked(MukQuestionFragment mukQuestionFragment) {
        if(mukQuestionFragment == mukQuestion2Fragment)
            mukGoToQuestion1();
        else if(mukQuestionFragment == mukQuestion3Fragment)
            mukGoToQuestion2();
        else if(mukQuestionFragment == mukQuestion4Fragment)
            mukGoToQuestion3();
        else if(mukQuestionFragment == mukQuestion5Fragment)
            mukGoToQuestion4();
    }

    @Override
    public void mukOnNextClicked(MukQuestionFragment mukQuestionFragment) {
        if(mukQuestionFragment == mukQuestion1Fragment)
            mukGoToQuestion2();
        else if(mukQuestionFragment == mukQuestion2Fragment)
            mukGoToQuestion3();
        else if(mukQuestionFragment == mukQuestion3Fragment)
            mukGoToQuestion4();
        else if(mukQuestionFragment == mukQuestion4Fragment)
            mukGoToQuestion5();
        else if(mukQuestionFragment == mukQuestion5Fragment)
            mukGoToResult();
    }

    // Utilities
    private void mukSetUp() {
        mukTest = new MukTest(this);
        mukBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mukHandleTimeUpdated();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mukBroadcastReceiver, new IntentFilter("MUK_CUSTOM_ACTION"));

        mukTestInfoFragment = new MukTestInfoFragment();
        mukTestInfoFragment.setMukTest(mukTest);
        mukTestInfoFragment.setMukTestInfoFragmentListener(this);

        mukQuestion1Fragment = new MukQuestion1Fragment();
        mukQuestion1Fragment.setMukQuestionFragmentListener(this);
        mukQuestion1Fragment.setMukTest(mukTest);

        mukQuestion2Fragment = new MukQuestion2Fragment();
        mukQuestion2Fragment.setMukQuestionFragmentListener(this);
        mukQuestion2Fragment.setMukTest(mukTest);

        mukQuestion3Fragment = new MukQuestion3Fragment();
        mukQuestion3Fragment.setMukQuestionFragmentListener(this);
        mukQuestion3Fragment.setMukTest(mukTest);

        mukQuestion4Fragment = new MukQuestion4Fragment();
        mukQuestion4Fragment.setMukQuestionFragmentListener(this);
        mukQuestion4Fragment.setMukTest(mukTest);

        mukQuestion5Fragment = new MukQuestion5Fragment();
        mukQuestion5Fragment.setMukQuestionFragmentListener(this);
        mukQuestion5Fragment.setMukTest(mukTest);

        mukGoToTestInfo();
    }

    private void mukGoToTestInfo() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukTestInfoFragment)
                .commit();
    }

    private void mukGoToQuestion1() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukQuestion1Fragment)
                .commit();
    }

    private void mukGoToQuestion2() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukQuestion2Fragment)
                .commit();
    }

    private void mukGoToQuestion3() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukQuestion3Fragment)
                .commit();
    }

    private void mukGoToQuestion4() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukQuestion4Fragment)
                .commit();
    }

    private void mukGoToQuestion5() {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mukQuestion5Fragment)
                .commit();
    }

    private void mukGoToResult() {
        mukTest.mukStopTimer();

        Intent mukIntent = new Intent(this, MukResultsActivity.class);
        mukIntent.putExtra("mukTest", mukTest);

        startActivityForResult(mukIntent, 1);
    }

    private void mukHandleTimeUpdated() {
        if(mukTest.getMukTimeLeft() > 0) {
            mukQuestion1Fragment.mukUpdateTimeLeftTextView();
            mukQuestion2Fragment.mukUpdateTimeLeftTextView();
            mukQuestion3Fragment.mukUpdateTimeLeftTextView();
            mukQuestion4Fragment.mukUpdateTimeLeftTextView();
            mukQuestion5Fragment.mukUpdateTimeLeftTextView();
        } else {
            mukGoToResult();
        }
    }

}