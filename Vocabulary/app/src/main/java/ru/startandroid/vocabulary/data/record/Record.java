package ru.startandroid.vocabulary.data.record;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable {

    private long id;
    private String word;
    private String translate;
    private String sample;
    private String definition;
    private long rememberedCount;
    private boolean enabled;
    private long added;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public long getRememberedCount() {
        return rememberedCount;
    }

    public void setRememberedCount(long rememberedCount) {
        this.rememberedCount = rememberedCount;
    }

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(word);
        dest.writeString(translate);
        dest.writeString(sample);
        dest.writeString(definition);
        dest.writeLong(rememberedCount);
        dest.writeByte((byte) (isEnabled() ? 1 : 0));
        dest.writeLong(added);
    }

    public Record() {

    }

    public Record(Parcel parcel) {
        setId(parcel.readLong());
        setWord(parcel.readString());
        setTranslate(parcel.readString());
        setSample(parcel.readString());
        setDefinition(parcel.readString());
        setRememberedCount(parcel.readLong());
        setEnabled(parcel.readByte() == 1);
        setAdded(parcel.readLong());
    }

    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {

        @Override
        public Record createFromParcel(Parcel parcel) {
            return new Record(parcel);
        }

        @Override
        public Record[] newArray(int i) {
            return new Record[i];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return id == record.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", rememberedCount=" + rememberedCount +
                ", enabled=" + enabled +
                '}';
    }
}
