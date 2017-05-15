package com.example.aditighamandi.login_register;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.net.URL;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;

    UserLocalStore userLocalStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        boolean flag;
        flag = userLocalStore.getUserLoggedIn();
        if (flag == true) {
            User user_logged = userLocalStore.getLoggedInUser();
            if (user_logged.category.equalsIgnoreCase("seller") )
                startActivity(new Intent(this, seller_MainActivity.class));
            else if(user_logged.category.equalsIgnoreCase("buyer"))
                startActivity(new Intent(this, buyer_MainActivity.class));
            else
                startActivity(new Intent(this, Login.class));
        } else if (flag == false) {
            startActivity(new Intent(this, Login.class));
        }

    }



    //private boolean authenticate(){
       // return userLocalStore.getUserLoggedIn();
  //  }



}
