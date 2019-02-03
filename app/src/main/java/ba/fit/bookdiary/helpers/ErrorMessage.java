package ba.fit.bookdiary.helpers;

public class ErrorMessage {

    public static String getErrorMessage(int statusCode){
        switch(statusCode){
            case 403:
                return "Wrong username or password";
                default:
                    return "An error occurred";
        }
    }
}
