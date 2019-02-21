package com.example.thaismoodandroid.Questionnaire;

public class Question {
    private String question;
    private int score;
    private boolean isSelected;

    public Question(String question, int score){
        this.question = question;
        this.score = score;
        this.isSelected = false;
    }

    public Question(String question){
        this.question = question;
        score = 0;
    }

    public String getQuestion(){
        return question;
    }

    public int getScore(){
        if(isSelected)
            return score;
        return 0;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setSelected(){
        isSelected = true;
    }
}
