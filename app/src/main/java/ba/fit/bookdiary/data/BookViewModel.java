package ba.fit.bookdiary.data;

import java.io.Serializable;

public class BookViewModel implements Serializable {
    private String title;
    private String genre;
    private String author;
    private int pages;
    private int publishedIn;

    public BookViewModel(String title, String genre, String author, int pages, int publishedIn) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.pages = pages;
        this.publishedIn = publishedIn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPublishedIn() {
        return publishedIn;
    }

    public void setPublishedIn(int publishedIn) {
        this.publishedIn = publishedIn;
    }

    @Override
    public String toString(){
        return getTitle() + " - " + getAuthor();
    }
}
