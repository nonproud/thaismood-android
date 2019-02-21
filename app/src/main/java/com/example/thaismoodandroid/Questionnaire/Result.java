package com.example.thaismoodandroid.Questionnaire;

public class Result {

    private String resultMessage;
    private boolean isPositive;

    public Result(String resultMessage, boolean isPositive){
        this.resultMessage = resultMessage;
        this.isPositive = isPositive;
    }

    public String getResultMessage(){
        return resultMessage;
    }

    public boolean getIsPositive(){
        return isPositive;
    }
}
