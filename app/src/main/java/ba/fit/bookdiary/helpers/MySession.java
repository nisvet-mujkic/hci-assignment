package ba.fit.bookdiary.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ba.fit.bookdiary.data.AutentifikacijaResultVM;


public class MySession {
    private static final String PREFS_NAME = "DatotekaZaSharedPrefernces";
    private static String nekikey = "Key_korisnik";

    public static AutentifikacijaResultVM getKorisnik() {
        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String strJson = sharedPreferences.getString(nekikey, "");

        if (strJson.length() == 0)
            return null;

        AutentifikacijaResultVM result = MyGson.build().fromJson(strJson, AutentifikacijaResultVM.class);
        return result;
    }

    public static void setKorisnik(AutentifikacijaResultVM authKorisnik) {
        String strJson = authKorisnik != null ? MyGson.build().toJson(authKorisnik) : "";
        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nekikey, strJson);

        editor.apply();
    }
}