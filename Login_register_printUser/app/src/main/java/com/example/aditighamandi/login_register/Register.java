package com.example.aditighamandi.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etUsername, etPassword, etName, etCategory;
    RadioGroup rgCategory;
    RadioButton rbSeller,rbBuyer;
    String seller_buyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bRegister = (Button) findViewById(R.id.bRegister);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        rgCategory = (RadioGroup) findViewById(R.id.rgCategory);
       // rbSeller = (RadioButton) findViewById(R.id.RBseller);
       // rbBuyer = (RadioButton) findViewById(R.id.RBbuyer);
        bRegister.setOnClickListener(this);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.RBseller:
                if (checked)
                    // Pirates are the best
                    rbSeller = (RadioButton) findViewById(R.id.RBseller);
                    seller_buyer = rbSeller.getText().toString();
                    break;
            case R.id.RBbuyer:
                if (checked)
                    rbBuyer = (RadioButton) findViewById(R.id.RBbuyer);
                    seller_buyer = rbBuyer.getText().toString();
                    // Ninjas rule
                    break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bRegister:
                String name = etName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

             //   int category_id = rgCategory.getCheckedRadioButtonId();
             //   rbcategory = (RadioButton) findViewById(category_id);
             //   String category = rbcategory.getText().toString();
                User user = new User(name,username,password,seller_buyer);

                registerUser(user);
                break;
        }
    }

    private void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new getUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
}
