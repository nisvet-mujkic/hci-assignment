package ba.fit.bookdiary.data;

import java.util.ArrayList;
import java.util.List;

import ba.fit.bookdiary.helpers.MyObjects;

public class Storage {
    public static List<UserViewModel> users;

    public static List<UserViewModel> getUsers(){
        if(users == null){
            users = new ArrayList<>();
            users.add(new UserViewModel("nisvet", "mujkic", "admin", "admin"));
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
            books.add(new BookViewModel("The Secret to Success", "Self-help", "Eric Thomas", 270, 2011));
            books.add(new BookViewModel("The Compound Effect", "Self-help", "Darren Hardy", 200, 2017));
        }
        return books;
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
}
