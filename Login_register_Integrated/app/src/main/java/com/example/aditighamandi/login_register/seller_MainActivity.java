package com.example.aditighamandi.login_register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.view.*;
import android.support.v7.widget.Toolbar.LayoutParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class seller_MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout seller_layout;
    EditText numItems;
    Button bPost,bEnter;
    String set_item;

    List<EditText> allED = new ArrayList<EditText>();

    int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seller_layout = (LinearLayout) this.findViewById(R.id.seller_layout);
        numItems = (EditText) this.findViewById(R.id.numItems);
        bEnter = (Button) this.findViewById(R.id.bEnter);
        bPost = (Button) this.findViewById(R.id.bPost);
        bEnter.setOnClickListener(this);

        bPost.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bEnter:
                number = Integer.parseInt(numItems.getText().toString());
                for (int i = 0; i < number; i++) {
                    EditText editText = new EditText(this);
                    editText.setText(String.valueOf(i));
                    allED.add(editText);
                    seller_layout.addView(editText);

                }
                break;

            case R.id.bPost:
                String item;
                for(int i=0; i < allED.size(); i++){
                    item = allED.get(i).getText().toString();
                    Item item_new = new Item(item);
                    storeUserDataInBackground(item_new);
                }

                break;
        }
    }

    private String getEncodedData(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for(String key : data.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(data.get(key),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(sb.length()>0)
                sb.append("&");

            sb.append(key + "=" + value);
        }
        return sb.toString();
    }
    public void storeUserDataInBackground(Item item){
        new storedUserDataAsyncTask(item).execute();
    }

    public class storedUserDataAsyncTask extends AsyncTask<Void,Void,Void> {
        Item item;
        public storedUserDataAsyncTask(Item item){
            this.item = item;
        }
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {


            Map<String,String> dataToSend = new HashMap<>();
            dataToSend.put("item_name", item.item_name);

            String encodedStr = getEncodedData(dataToSend);
            BufferedReader reader = null;
            // String toSend = item.item_name;

            try {
                //Converting address String to URL
                URL url = new URL("http","10.0.2.2",80,"Save_item.php");
                //URL url = new URL(SERVER_ADDRESS + "Register.php");
                //Opening the connection (Not setting or using CONNECTION_TIMEOUT)
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //Post Method
                con.setRequestMethod("POST");
                //To enable inputting values using POST method
                //(Basically, after this we can write the dataToSend to the body of POST method)
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                //Writing dataToSend to outputstreamwriter
                writer.write(encodedStr);
                //Sending the data to the server - This much is enough to send data to server


                //But to read the response of the server, you will have to implement the procedure below
                writer.flush();

                //Data Read Procedure - Basically reading the data comming line by line
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line;
                while((line = reader.readLine()) != null) { //Read till there is something available
                    sb.append(line + "\n");     //Reading and saving line by line - not all at once
                }
                line = sb.toString();           //Saving complete data received in string, you can do it differently

                //Just check to the values received in Logcat
                Log.i("custom_check","The values received in the store part are as follows:");
                Log.i("custom_check",line);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(reader != null) {
                    try {
                        reader.close();     //Closing the
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //Same return null, but if you want to return the read string (stored in line)
            //then change the parameters of AsyncTask and return that type, by converting
            //the string - to say JSON or user in your case



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }

}