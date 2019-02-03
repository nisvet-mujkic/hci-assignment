package ba.fit.bookdiary.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ba.fit.bookdiary.LoginActivity;
import ba.fit.bookdiary.MainActivity;
import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.ChangePasswordViewModel;
import ba.fit.bookdiary.data.AutentifikacijaResultVM;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyObjects;
import ba.fit.bookdiary.helpers.MyRunnable;
import ba.fit.bookdiary.helpers.MySession;


public class ChangePasswordFragment extends Fragment {
    private Button change;
    private EditText repeatNewPassword;
    private EditText newPassword;

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        newPassword = (EditText)view.findViewById(R.id.newPassword);
        repeatNewPassword = (EditText)view.findViewById(R.id.repeatNewPassword);

        change = (Button)view.findViewById(R.id.changePasswordBtn);

        view.findViewById(R.id.cancelPasswordChangeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_changeBtnClick();
            }
        });

        return view;
    }

    private void do_changeBtnClick() {
        String newPwd = newPassword.getText().toString().trim();
        String repeatNewPwd = repeatNewPassword.getText().toString().trim();

        if(newPwd.isEmpty()){
            newPassword.setError("You must enter a new password");
            return;
        }

        if(!MyObjects.equals(repeatNewPwd, newPwd)){
            repeatNewPassword.setError("Passwords must match");
            return;
        }

        ChangePasswordViewModel changePasswordViewModel = new ChangePasswordViewModel(repeatNewPwd);

        MyApiRequest.post(getActivity(), "Accounts/ChangePassword", changePasswordViewModel, new MyRunnable<Object>() {
            @Override
            public void run(Object o) {
                MySession.setKorisnik( null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
