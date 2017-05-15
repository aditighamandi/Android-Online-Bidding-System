package com.example.aditighamandi.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
    EditText etName, etUsername, etCategory;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etCategory = (EditText) findViewById(R.id.etCategory);

        bLogout = (Button) findViewById(R.id.bLogout);
        bLogout.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogout:
                userLocalStore.clearData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true){
            displayUserDetails();
        }else{
            startActivity(new Intent(this,Login.class));
        }
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        etName.setText(user.name);
        etUsername.setText(user.username);
        etCategory.setText(user.category);




    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }



}
