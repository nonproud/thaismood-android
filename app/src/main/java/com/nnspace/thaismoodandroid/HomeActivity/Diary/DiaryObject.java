package com.nnspace.thaismoodandroid.HomeActivity.Diary;

public class DiaryObject {

    int id;
    private String title = "หัวข้อ", abStory = "เรื่องย่อ", story, date = "2019/5/22";

    public DiaryObject(int id, String title, String story, String date){
        this.id = id;
        this.title = title;
        this.story = story;
        this.date = date;
        this.abStory =  story.substring(0, Math.min(story.length(), 50)) + " ...";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbStory() {
        return abStory;
    }

    public void setAbStory(String abStory) {
        this.abStory = abStory;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
