package com.example.thaismoodandroid.Questionnaire;

import java.util.ArrayList;

public interface Questionnare {
    public Result getResult();
    public String getQuestions(int index);
    public void setQuestionScore(int index, int score);
}
