package ba.fit.bookdiary.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ba.fit.bookdiary.helpers.MyObjects;

public class Storage {
    public static List<UserViewModel> users;

    public static List<UserViewModel> getUsers(){
        if(users == null){
            users = new ArrayList<>();
            users.add(new UserViewModel(1,"nisvet", "mujkic", "", ""));
        }
        return users;
    }

    public static void addUser(UserViewModel user){
        users.add(user);
    }

    public static boolean removeUser(UserViewModel user){
        return users.remove(user);
    }

    public static List<BookViewModel> books;

    public static List<BookViewModel> getBooks(){
        if(books == null){
            books = new ArrayList<>();
            books.add(new BookViewModel(1, "The Secret to Success", "Self-help", "Eric Thomas", 270, 2011));
            books.add(new BookViewModel(2, "The Compound Effect", "Self-help", "Darren Hardy", 200, 2017));
            books.add(new BookViewModel(3, "Deep Work", "Self-Development", "Cal Newport", 200, 2017));
            books.add(new BookViewModel(4, "Atomic Habits", "Self-help", "James Clear", 200, 2017));
            books.add(new BookViewModel(5, "The Power of Habit", "Self-help", "John Doe", 200, 2017));
            books.add(new BookViewModel(6, "Clean Code", "Programming", "Bob Martin", 400, 2000));
            books.add(new BookViewModel(7, "Eloquent JavaScript", "Programming", "Marijn Haverbeke", 200, 2017));
        }
        return books;
    }

    public static BookViewModel getBookById(int bookId){
        for (BookViewModel book : getBooks())
            if(book.getId() == bookId)
                return book;
        return null;
    }

    public static void addBook(BookViewModel book){
        books.add(book);
    }

    public static boolean removeBook(BookViewModel book){
        return books.remove(book);
    }

    public static UserViewModel checkLoginStatus(String username, String password){
        for (UserViewModel user : getUsers())
            if(MyObjects.equals(user.getUsername(), username) && MyObjects.equals(user.getPassword(), password))
                return user;
        return null;
    }

    public static List<CurrentlyReadingViewModel> readingList;
    public static  List<CurrentlyReadingViewModel> getReadingList(){
        if(readingList == null){
            readingList = new ArrayList<>();
            readingList.add(new CurrentlyReadingViewModel(1, 1));
            readingList.add(new CurrentlyReadingViewModel(2, 1));
        }
        return readingList;
    }

    public static void addBookToReadingList(int bookId, int userId){
        readingList.add(new CurrentlyReadingViewModel(bookId, userId));
    }

    public static boolean removeBookFromReadingList(int bookId, int userId){
        return true;
    }

    public static List<String> getGenres(){
        List<String> genres = new ArrayList<>();
        genres.add("Self-development");
        genres.add("Self-help");
        genres.add("Sci-Fi");
        genres.add("Novel");
        return genres;
    }

    public static List<BookReviewVM> reviews;
    public static List<BookReviewVM> getReviews(){
        if(reviews == null){
            reviews = new ArrayList<>();
            reviews.add(new BookReviewVM(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit", "Lorem ipsum dolor sit amet", 4));
            reviews.add(new BookReviewVM(2, "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", 2));
            reviews.add(new BookReviewVM(3, "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", 3));
            reviews.add(new BookReviewVM(4, "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", 4));
            reviews.add(new BookReviewVM(5, "Lorem ipsum dolor sit amet", "Lorem ipsum dolor sit amet", 5));
        }
        return reviews;
    }

    public static List<BookReviewVM> getReviewsByQuery(String query){
        return findBooks(query);
    }

    private static List<BookReviewVM> findBooks(String query){
        List<BookReviewVM> matchingReviews = new ArrayList<>();

        for(BookReviewVM review : getReviews())
            if(matchesQuery(review.getBookId(), query))
                matchingReviews.add(review);

        return matchingReviews;
    }

    private static boolean matchesQuery(int bookId, String query){
        BookViewModel book = getBookById(bookId);

        if(book != null){
            if(book.getTitle().contains(query) || book.getGenre().contains(query))
                return true;
            return false;
        }

        return false;
    }


    private static List<String> getQuotes(){
       List<String> quotes = new ArrayList<>();

       quotes.add("\"A reader lives a thousand lives before he dies... The man who never reads lives only one.\"");
       quotes.add("\"I read a book one day and my whole life was changed.\"");
       quotes.add("\"Beware of the person of one book.\"");
       quotes.add("\"Some books leave us free and some books make us free.\"");
       quotes.add("\"Rainy days should be spent at home with a cup of tea and a good book.\"");

       return quotes;
    }

    private static List<String> getDates(){
        List<String> dates = new ArrayList<>();

        dates.add("14.01.2019");
        dates.add("07.01.2019");
        dates.add("04.01.2019");
        dates.add("17.01.2019");
        dates.add("19.01.2019");
        dates.add("02.01.2019");
        dates.add("08.01.2019");
        dates.add("20.01.2019");
        dates.add("22.01.2019");

        return dates;
    }

    public static String getRandomDate(){
        Random random = new Random();
        int rnd = random.nextInt(getDates().size() - 1);
        return getDates().get(rnd);
    }

    public static String getRandomQuote(){
        Random random = new Random();
        int rnd = random.nextInt(getQuotes().size() - 1);
        return getQuotes().get(rnd);
    }

    private static List<String> getNumbers(){
        List<String> dates = new ArrayList<>();

        dates.add("1");
        dates.add("3");
        dates.add("7");
        dates.add("8");
        dates.add("2");
        dates.add("10");
        dates.add("9");
        dates.add("6");
        dates.add("5");

        return dates;
    }

    public static String getRandomNumber(){
        Random random = new Random();
        int rnd = random.nextInt(getNumbers().size() - 1);
        return getNumbers().get(rnd);
    }

}
