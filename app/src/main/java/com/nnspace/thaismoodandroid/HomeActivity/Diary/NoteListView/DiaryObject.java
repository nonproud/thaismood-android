package com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView;

public class DiaryObject extends DiaryBaseItem {

    private int id;
    private String title;
    private String story;
    private String abStory;
    private String date;
    private int moodEmo;

    public DiaryObject(int id, String title, String story, String date, int moodEmo) {
        super(1);
        this.id = id;
        this.title = title;
        this.story = story;
        this.date = date;
        this.moodEmo = moodEmo;
        this.abStory = story.substring(0, Math.min(story.length(), 100));
    }

    public DiaryObject(int id, String title, String story, String date) {
        super(1);
        this.id = id;
        this.title = title;
        this.story = story;
        this.date = date;
        this.abStory = story.substring(0, Math.min(story.length(), 100));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStory(String story) {
        this.story = abStory;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMoodEmo(int moodEmo) {
        this.moodEmo = moodEmo;
    }

    public void setLevelEmo(int levelEmo) {
        this.levelEmo = levelEmo;
    }

    public String getTitle() {
        return title;
    }

    public String getStory() {return story;}

    public String getAbStory() {
        return abStory;
    }

    public String getDate() {
        return date;
    }

    public int getMoodEmo() {
        return moodEmo;
    }

    public int getLevelEmo() {
        return levelEmo;
    }

    private int levelEmo;


}
