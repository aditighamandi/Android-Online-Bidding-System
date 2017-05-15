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

public class buyer_MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLogout;
 //   EditText etName, etUsername, etCategory;
    Spinner Item_spinner;
    UserLocalStore userLocalStore;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer__main);


        Spinner sp = (Spinner) findViewById(R.id.Item_spinner);
        adapter = new ArrayAdapter<String>(this,R.layout.spinner_layout, R.id.txt,listItems);
        sp.setAdapter(adapter);


    //    etName = (EditText) findViewById(R.id.etName);
    //    etUsername = (EditText) findViewById(R.id.etUsername);
    //    etCategory = (EditText) findViewById(R.id.etCategory);

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

         //   displayUserDetails();
            BackTask bt = new BackTask();
            bt.execute();

    }
/*
    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        etName.setText(user.name);
        etUsername.setText(user.username);
        etCategory.setText(user.category);
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }
*/
    private class BackTask extends AsyncTask<Void,Void,Void>{
        ArrayList<String> list;

        protected void onPreExecute(){
            super.onPreExecute();
            list = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try{
                URL url = new URL("http","10.0.2.2",80,"FetchData_buyer.php");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream in = con.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(isw);
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    result = result+line;
                }
                isw.close();

                JSONArray jsonArray = new JSONArray(result);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("item_name"));
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result){
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }


}
