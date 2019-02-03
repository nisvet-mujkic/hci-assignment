package ba.fit.bookdiary.ViewModels;

import java.util.Date;

public class AddBookViewModel {
    public String Title;
    public String Author;
    public String Genre;
    public int Pages;
    public Date StartedReadingOn;

    public AddBookViewModel(String title, String author, String genre, int pages, Date startedReadingOn) {
        Title = title;
        Author = author;
        Genre = genre;
        Pages = pages;
        StartedReadingOn = startedReadingOn;
    }
}
