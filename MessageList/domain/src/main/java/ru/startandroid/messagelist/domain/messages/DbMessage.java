package ru.startandroid.messagelist.domain.messages;

public class DbMessage {

    private long id;

    private long time;

    private String text;

    private String image;

    private boolean favorite;

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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "DbMessage{" +
                "id=" + id +
                ", time=" + time +
                ", favorite=" + favorite +
                '}';
    }
}
