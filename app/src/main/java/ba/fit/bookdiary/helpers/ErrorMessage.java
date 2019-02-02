package ba.fit.bookdiary.helpers;

public class ErrorMessage {

    public static String getErrorMesage(int statusCode){
        switch(statusCode){
            case 403:
                return "Wrong username or password";
                default:
                    return "Something went wrong";
        }
    }
}
