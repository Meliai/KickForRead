package com.rudainc.kickforread.models;

import android.os.Parcel;
import android.os.Parcelable;

import butterknife.Optional;

public class BooksModel implements Parcelable{

    private String id;
    private String title;
    private String author;
    private String category;
    private String label;
    private String start_date;
    private String isFinished;

    public BooksModel(String id, String title, String author, String category, String label, String start_date, String isFinished) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.label = label;
        this.start_date = start_date;
        this.isFinished = isFinished;
    }

    protected BooksModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        category = in.readString();
        label = in.readString();
        start_date = in.readString();
        isFinished = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(category);
        dest.writeString(label);
        dest.writeString(start_date);
        dest.writeString(isFinished);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BooksModel> CREATOR = new Creator<BooksModel>() {
        @Override
        public BooksModel createFromParcel(Parcel in) {
            return new BooksModel(in);
        }

        @Override
        public BooksModel[] newArray(int size) {
            return new BooksModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getLabel() {
        return label;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getIsFinished() {
        return isFinished;
    }
}
