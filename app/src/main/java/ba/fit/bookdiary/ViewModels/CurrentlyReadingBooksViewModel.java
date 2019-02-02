package ba.fit.bookdiary.ViewModels;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentlyReadingBooksViewModel {
    public List<Row> rows = new ArrayList<>();

    public static class Row
    {
        public int bookId;
        public String title;
        public String author;
        public String genre;
        public int pages;
        public String startedReadingOn;
    }
}
