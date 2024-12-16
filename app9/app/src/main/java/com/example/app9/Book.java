package com.example.app9;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Books")
public class Book implements Parcelable {
    @PrimaryKey
    @NonNull
    private int id;
    private String title;
    private int idAuthor;
    private String author;

    private String publishDate;
    private int numberOfPages;
    private float rating;

    public Book(int id, String title, int idAuthor, String publishDate, String author, int numberOfPages, float rating) {
        this.id = id;
        this.title = title;
        this.idAuthor = idAuthor;
        this.publishDate = publishDate;
        this.author = author;
        this.numberOfPages = numberOfPages;
        this.rating = rating;
    }

    protected Book(Parcel in) {
        id = in.readInt();
        title = in.readString();
        idAuthor = in.readInt();
        author = in.readString();
        publishDate = in.readString();
        numberOfPages = in.readInt();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(idAuthor);
        dest.writeString(author);
        dest.writeString(publishDate);
        dest.writeInt(numberOfPages);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", idAuthor=").append(idAuthor);
        sb.append(", author='").append(author).append('\'');
        sb.append(", publishDate='").append(publishDate).append('\'');
        sb.append(", numberOfPages=").append(numberOfPages);
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }
}
