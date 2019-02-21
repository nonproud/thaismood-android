package com.example.thaismoodandroid.Questionnaire;

import java.util.ArrayList;

public class IXQ implements Questionnare {

    private ArrayList<Question> questions;
    private int score;

    public IXQ(){
        questions = new ArrayList<>();
        int score = 0;
        questions.add(new Question("เบื่อ ไม่สนใจอยากทาอะไร"));
        questions.add(new Question("เไม่สบายใจ ซึมเศร้า ท้อแท้"));
        questions.add(new Question("หลับยากหรือหลับๆตื่นๆหรือหลับมากไป"));
        questions.add(new Question("เหนื่อยง่ายหรือไม่ค่อยมีแรง"));
        questions.add(new Question("เบื่ออาหารหรือกินมากเกินไป"));
        questions.add(new Question("รู้สึกไม่ดีกับตัวเอง คิดว่าตัวเองล้มเหลวหรือครอบครัวผิดหวัง"));
        questions.add(new Question("สมาธิไม่ดี เวลาทาอะไร เช่น ดูโทรทัศน์ ฟังวิทยุ หรือทางานที่ต้องใช้ความตั้งใจ"));
        questions.add(new Question("พูดช้า ทาอะไรช้าลงจนคนอื่นสังเกตเห็นได้ หรือกระสับกระส่ายไม่สามารถอยู่นิ่งได้เหมือนที่เคยเป็น"));
        questions.add(new Question("คิดทาร้ายตนเอง หรือคิดว่าถ้าตายไปคงจะดี"));
    }

    @Override
    public Result getResult() {
        getScore();
        if(score < 7)
            return new Result("ไม่มีอาการของโรคซึมเศร้าหรือมีอาการของโรคซึมเศร้าระดับน้อยมาก", false);
        else if(score >= 7 && score <= 12)
            return new Result("มีอาการของโรคซึมเศร้า ระดับน้อย", true);
        else if(score >= 13 && score <= 18)
            return new Result("มีอาการของโรคซึมเศร้า ระดับปานกลาง", true);
        else
            return new Result("มีอาการของโรคซึมเศร้า ระดับรุนแรง", true);
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
