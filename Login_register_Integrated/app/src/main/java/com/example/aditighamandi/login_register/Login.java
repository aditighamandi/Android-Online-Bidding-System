package com.example.aditighamandi.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText etUsername, etPassword;
    TextView registerLink;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        registerLink = (TextView) findViewById(R.id.registerLink);
        bLogin.setOnClickListener(this);
        registerLink.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(username, password);

                authenticate(user);


                break;
            case R.id.registerLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void authenticate(User user) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchDataInBackground(user, new getUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                }else{
                    logUserIn(returnedUser);
                }
            }
        });
    }

        private void showErrorMessage(){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
            dialogBuilder.setMessage("Incorrect user details");
            dialogBuilder.setPositiveButton("ok",null);
            dialogBuilder.show();
    }



    public void logUserIn(User returnedUser) {

        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }

}
