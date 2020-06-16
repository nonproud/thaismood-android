package com.nnspace.thaismoodandroid.Models;

public interface IEvaluation {

    public String getStringIndicator(int no);
    public void finishTask();
    public void nextQuestion();
    public void prevQuestion();
    public void next();
    public void clearButton();
}
