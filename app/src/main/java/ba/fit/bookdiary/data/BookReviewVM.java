package ba.fit.bookdiary.data;

import java.io.Serializable;

public class BookReviewVM implements Serializable{
    private int bookId;
    private String review;
    private String quote;
    private int mark;

    public BookReviewVM(int bookId, String review, String quote, int mark) {
        this.bookId = bookId;
        this.review = review;
        this.quote = quote;
        this.mark = mark;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
