package com.example.thaismoodandroid.Questionnaire;

import java.util.ArrayList;

public class VIIIQ implements Questionnare {

    private ArrayList<Question> questions;
    private int score;

    public VIIIQ(){
        questions = new ArrayList<>();
        int score = 0;
        questions.add(new Question("คิดอยากตาย หรือ คิดว่าตายไปจะดีกว่า"));
        questions.add(new Question("อยากทาร้ายตัวเอง หรือ ทาให้ตัวเองบาดเจ็บ"));
        questions.add(new Question("คิดเกี่ยวกับการฆ่าตัวตาย"));
        questions.add(new Question("ท่านสามารถควบคุมความอยากฆ่าตัวตายที่ท่านคิดอยู่นั้นได้หรือไม่ หรือ บอกได้ไหมว่าคงจะไม่ทาตามความคิดนั้นในขณะนี้"));
        questions.add(new Question("มีแผนการที่จะฆ่าตัวตาย"));
        questions.add(new Question("ได้เตรียมการที่จะทาร้ายตนเองหรือเตรียมการจะฆ่าตัวตายโดยตั้งใจว่าจะให้ตายจริง ๆ"));
        questions.add(new Question("ได้ทาให้ตนเองบาดเจ็บแต่ไม่ตั้งใจที่จะทาให้เสียชีวิต"));
        questions.add(new Question("ได้พยายามฆ่าตัวตายโดยคาดหวัง/ตั้งใจที่จะให้ตาย"));
        questions.add(new Question("ท่านเคยพยายามฆ่าตัวตาย"));
    }

    @Override
    public Result getResult() {
        getScore();
        if(score < 1)
            return new Result("ไม่มีแนวโน้มฆ่าตัวตายในปัจจุบัน", false);
        else if(score >= 1 && score <= 8)
            return new Result("มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน ระดับน้อย", false);
        else if(score >= 9 && score <= 16)
            return new Result("มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน ระดับปานกลาง", false);
        else
            return new Result("มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน ระดับรุนแรง", true);
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
