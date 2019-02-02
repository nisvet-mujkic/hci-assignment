package ba.fit.bookdiary.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

import ba.fit.bookdiary.R;
import ba.fit.bookdiary.ViewModels.EditProfileViewModel;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;

public class EditProfileFragment extends DialogFragment {
    private EditText firstName;
    private EditText username;
    private EditText lastName;
    private EditText email;
    private EditText preferredGenre;
    private Button save;

    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_profile, container, false);

        firstName = (EditText)view.findViewById(R.id.firstNameEditText);
        username = (EditText)view.findViewById(R.id.usernameEditText);
        lastName = (EditText)view.findViewById(R.id.lastNameEditText);
        email = (EditText)view.findViewById(R.id.emailEditText);
        preferredGenre = (EditText)view.findViewById(R.id.preferredGenreEditText);

        getProfileInfo();

        save = (Button)view.findViewById(R.id.editProfileSaveBtn);

        view.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_saveBtnClick();
            }
        });

        return view;
    }

    private void getProfileInfo(){
        MyApiRequest.get(getActivity(), "Accounts/GetProfileInfo", new MyRunnable<EditProfileViewModel>() {
            @Override
            public void run(EditProfileViewModel model){
                if(model != null){
                    firstName.setText(model.firstName);
                    lastName.setText(model.lastName);
                    preferredGenre.setText(model.preferredGenre);
                    email.setText(model.email);
                    username.setText(model.username);
                }
            }
        });
    }

    private void do_saveBtnClick() {
        EditProfileViewModel viewModel = new EditProfileViewModel();
        viewModel.email = email.getText().toString();
        viewModel.firstName = firstName.getText().toString();
        viewModel.lastName = lastName.getText().toString();
        viewModel.preferredGenre = preferredGenre.getText().toString();
        viewModel.username = username.getText().toString();

        MyApiRequest.post(getActivity(), "Accounts/EditProfile", viewModel, null);

        getDialog().dismiss();
    }
}
