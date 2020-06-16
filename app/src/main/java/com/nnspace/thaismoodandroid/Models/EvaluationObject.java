package com.nnspace.thaismoodandroid.Models;

import android.content.Context;

import com.nnspace.thaismoodandroid.R;

public class EvaluationObject {
    private int score2q, score9Q, score8Q, scoreMDQ;
    private String q2qRestult, q9qResult, q8qResult, mdqResult;
    private String date;
    private Context context;

    public EvaluationObject(Context context, int s2q, int s9q, int s8q, int smdq, String date){
        this.context = context;
        this.score2q = s2q;
        this.score9Q = s9q;
        this.score8Q = s8q;
        this.scoreMDQ = smdq;
        this.date = date;
        setQ2qResult();
        setQ9qRestult();
        setQ8qRestult();
        setMdqRestult();
    }

    public int getScore2q() {
        return score2q;
    }

    public void setScore2q(int score2q) {
        this.score2q = score2q;
    }

    public int getScore9Q() {
        return score9Q;
    }

    public void setScore9Q(int score9Q) {
        this.score9Q = score9Q;
    }

    public int getScore8Q() {
        return score8Q;
    }

    public void setScore8Q(int score8Q) {
        this.score8Q = score8Q;
    }

    public int getScoreMDQ() {
        return scoreMDQ;
    }

    public void setScoreMDQ(int scoreMDQ) {
        this.scoreMDQ = scoreMDQ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQ2qRestult() {
        return q2qRestult;
    }

    public String getQ9qResult() {
        return q9qResult;
    }

    public String getQ8qResult() {
        return q8qResult;
    }

    public String getMdqResult() {
        return mdqResult;
    }

    private void setQ2qResult(){
        if(getScore2q() == 0){
            q2qRestult = context.getResources().getString(R.string.q2q_result1);
        }else if(getScore2q() > 0){
            q2qRestult = context.getResources().getString(R.string.q2q_result2);
        }else if(getScore2q() < 0){
            q2qRestult = "-";
        }
    }

    private void setQ9qRestult(){
        if(getScore9Q() < 0){
            q9qResult = "-";
        }else if(getScore9Q() <= 6){
            q9qResult = context.getResources().getString(R.string.q9q_result1);
        }else if(getScore9Q() >= 7 && getScore9Q() <= 12){
            q9qResult = context.getResources().getString(R.string.q9q_result2);
        }else if(getScore9Q() >= 13 && getScore9Q() <= 18){
            q9qResult = context.getResources().getString(R.string.q9q_result3);
        }else{
            q9qResult = context.getResources().getString(R.string.q9q_result4);
        }
    }

    private void setQ8qRestult(){
        if(getScore8Q() < 0){
            q8qResult = "-";
        }else if(getScore8Q() == 0){
            q8qResult = context.getResources().getString(R.string.q8q_result1);
        }else if(getScore8Q() >= 1 && getScore8Q() <= 8){
            q8qResult = context.getResources().getString(R.string.q8q_result2);
        }else if(getScore8Q() >= 9 && getScore8Q() <= 16){
            q8qResult = context.getResources().getString(R.string.q8q_result3);
        }else{
            q8qResult = context.getResources().getString(R.string.q8q_result4);
        }
    }

    private void setMdqRestult(){
        mdqResult = "";
    }
}
