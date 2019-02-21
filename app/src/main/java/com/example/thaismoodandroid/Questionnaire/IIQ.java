package com.example.thaismoodandroid.Questionnaire;

import java.util.ArrayList;

public class IIQ implements Questionnare {

    private ArrayList<Question> questions;
    private int score;


    public IIQ() {
        questions = new ArrayList<>();
        questions.add(new Question("ใน 2 สัปดาห์ที่ผ่านมา รวมวันนี้ ท่านรู้สึก หดหู่ เศร้า หรือท้อแท้สิ้นหวัง หรือไม่", 1));
        questions.add(new Question("ใน 2 สัปดาห์ที่ผ่านมา รวมวันนี้ท่านรู้สึก เบื่อ ทาอะไรก็ไม่เพลิดเพลิน หรือไม่", 1));
        score = 0;
    }

    @Override
    public Result getResult() {
        getScore();
        if (score > 0)
            return new Result("มีแนวโน้มที่จะเป็นโรคซึมเศร้า", true);
        return new Result("ไม่เป็นโรคซึมเศร้า", false);
    }

    @Override
    public String getQuestions(int index) {
        return questions.get(index).getQuestion();
    }

    @Override
    public void setQuestionScore(int index, int score) {
        questions.get(index).setScore(score);
    }

    private void getScore(){
        for(Question q: questions)
            score += q.getScore();
    }

}

