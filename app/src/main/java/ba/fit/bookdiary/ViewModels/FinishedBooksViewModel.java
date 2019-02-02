package ba.fit.bookdiary.ViewModels;

import java.util.List;

public class FinishedBooksViewModel {
    public List<Row> rows;

    public class Row
    {
        public int bookId;
        public String title;
        public String author;
        public String genre;
        public int mark;
        public String finishedReadingOn;
    }
}
