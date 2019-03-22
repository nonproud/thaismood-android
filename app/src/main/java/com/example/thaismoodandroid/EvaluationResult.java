package com.example.thaismoodandroid;

public class EvaluationResult {
    private int score;
    private boolean isPositive;
    private String resultString;

    public EvaluationResult(int score, boolean isPositive, String resultString){
        this.score = score;
        this.isPositive = isPositive;
        this.resultString = resultString;
    }

    public int getScore() {
        return score;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getResultString() {
        return resultString;
    }
}
