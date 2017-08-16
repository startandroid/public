package ru.startandroid.messagelist.data.messages;

import com.google.gson.annotations.SerializedName;

public class ApiMessage {

    @SerializedName("id")
    private long id;

    @SerializedName("time")
    private long time;

    @SerializedName("text")
    private String text;

    @SerializedName("image")
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ApiMessage{" +
                "id=" + id +
                ", time=" + time +
                '}';
    }
}
