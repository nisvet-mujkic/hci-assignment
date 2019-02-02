package ba.fit.bookdiary.data;

import java.util.Date;

public class CurrentlyReadingViewModel {
    private int bookId;
    private int userId;
    private Date startedOn;
    private Date completedOn;

    public CurrentlyReadingViewModel(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.startedOn = new Date();
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }
}
