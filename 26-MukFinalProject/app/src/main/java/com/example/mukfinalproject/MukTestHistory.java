package com.example.mukfinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MukTestHistory {

    // Constants
    private final String MUK_PREFERENCE_KEY = "MukScoreHistory";

    // Variables
    private ArrayList<MukTest> mukTests;
    private Context mukContext;

    // Constructors
    public MukTestHistory(Context mukContext) {
        this.mukContext = mukContext;
        SharedPreferences mukSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mukContext);
        String mukJSON = mukSharedPreferences.getString(MUK_PREFERENCE_KEY, "");

        Gson mukGson = new Gson();
        Type mukType = new TypeToken< ArrayList<MukTest>>() {}.getType();

        mukTests = mukGson.fromJson(mukJSON, mukType);
        if(mukTests == null)
            mukTests = new ArrayList<>();
    }

    // Instance Methods
    public void addAndSave(MukTest mukTest) {
        mukTests.add(0, mukTest);

        SharedPreferences.Editor mukEditor = PreferenceManager.getDefaultSharedPreferences(mukContext).edit();
        Gson mukGson = new Gson();
        String mukJSON = mukGson.toJson(mukTests);
        mukEditor.putString(MUK_PREFERENCE_KEY, mukJSON);
        mukEditor.commit();
    }

    // Getters and Setters
    public ArrayList<MukTest> getMukTests() {
        return mukTests;
    }

    public void setMukTests(ArrayList<MukTest> mukTests) {
        this.mukTests = mukTests;
    }
}
