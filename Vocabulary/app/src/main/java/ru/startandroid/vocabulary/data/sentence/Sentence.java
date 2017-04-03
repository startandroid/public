package ru.startandroid.vocabulary.data.sentence;

public class Sentence {

    private long id;
    private String russian;
    private String english;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRussian() {
        return russian;
    }

    public void setRussian(String russian) {
        this.russian = russian;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
