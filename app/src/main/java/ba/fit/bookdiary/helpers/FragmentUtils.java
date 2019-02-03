package ba.fit.bookdiary.helpers;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class FragmentUtils {

    public static void replaceFragment(Activity activity, int id, Fragment fragment) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    public static void openFragmentAsDialog(Activity activity, DialogFragment dialogFragment, String tag){
        FragmentManager fragmentManager = activity.getFragmentManager();
        dialogFragment.show(fragmentManager, tag);
    }
}
