package ba.fit.bookdiary.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ba.fit.bookdiary.data.UserViewModel;

public class MySession {

    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String USER = "SESSION_USER";

    public static void setUser(UserViewModel user){
        Gson gson = new GsonBuilder().create();

        String stringifiedUser = user != null ? gson.toJson(user) : "";

        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER, stringifiedUser);
        editor.apply();
    }

    public static UserViewModel getUser(){
        Gson gson = new GsonBuilder().create();

        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String stringfiedUser = sharedPreferences.getString(USER, "");

        if(stringfiedUser.length() == 0)
            return null;

        return gson.fromJson(stringfiedUser, UserViewModel.class);
    }

}
