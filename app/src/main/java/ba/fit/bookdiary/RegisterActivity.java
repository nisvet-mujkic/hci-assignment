package ba.fit.bookdiary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ba.fit.bookdiary.data.KorisnikPregledVM;
import ba.fit.bookdiary.data.UserRegisterViewModel;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyObjects;
import ba.fit.bookdiary.helpers.MyRunnable;

public class RegisterActivity extends AppCompatActivity {
    private EditText passwordRepeat;
    private EditText password;
    private EditText username;
    private Button signUp;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.usernameRegister);
        password = (EditText)findViewById(R.id.passwordRegister);
        passwordRepeat = (EditText)findViewById(R.id.repeatPasswordRegister);

        signUp = (Button)findViewById(R.id.signUpBtn);

        findViewById(R.id.cancelSignUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RegisterActivity.super.onBackPressed();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_signUpBtnClick();
            }
        });
    }

    private void do_signUpBtnClick() {
        String usernameStr = username.getText().toString().trim();
        String pwd = password.getText().toString();
        String repeatPwd = passwordRepeat.getText().toString();

        if(usernameStr.isEmpty()){
            username.setError("You must enter username");
            return;
        }

        if(pwd.isEmpty()){
            password.setError("You must enter password");
            return;
        }

        if(!MyObjects.equals(repeatPwd, pwd)){
            passwordRepeat.setError("Passwords don't match");
            return;
        }

        UserRegisterViewModel user = new UserRegisterViewModel(usernameStr, pwd);

        activity = this;

        MyApiRequest.post(this, "Register", user, new MyRunnable<Object>() {
            @Override
            public void run(Object o) {
                startActivity(new Intent(activity, LoginActivity.class));
            }
        });
    }
}
