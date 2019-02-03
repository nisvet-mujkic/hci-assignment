package ba.fit.bookdiary;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ba.fit.bookdiary.data.AutentifikacijaLoginPostVM;
import ba.fit.bookdiary.data.AutentifikacijaResultVM;
import ba.fit.bookdiary.data.Storage;
import ba.fit.bookdiary.data.UserViewModel;
import ba.fit.bookdiary.helpers.MyApiRequest;
import ba.fit.bookdiary.helpers.MyRunnable;
import ba.fit.bookdiary.helpers.MySession;

public class LoginActivity extends AppCompatActivity {
    private TextView txtViewRegister;
    private EditText passwordLogin;
    private EditText usernameLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtViewRegister = (TextView) findViewById(R.id.txtViewRegister);
        txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_txtViewRegisterClick();
            }
        });

        usernameLogin = (EditText) findViewById(R.id.usernameLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnLoginClick();
            }
        });
    }

    private void do_btnLoginClick() {
        String username = usernameLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        String deviceInfo = android.os.Build.DEVICE + " | " + android.os.Build.VERSION.RELEASE + " | " + android.os.Build.PRODUCT + " | " + Build.MODEL;

        AutentifikacijaLoginPostVM model = new AutentifikacijaLoginPostVM(username, password, deviceInfo);

        MyApiRequest.post(this, "Autentifikacija/Login", model, new MyRunnable<AutentifikacijaResultVM>() {
            @Override
            public void run(AutentifikacijaResultVM result) {
                checkLogin(result);
            }
        });
    }

    private void do_txtViewRegisterClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void checkLogin(AutentifikacijaResultVM result) {
        if (result == null) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Wrong username or password", Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(result);
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
