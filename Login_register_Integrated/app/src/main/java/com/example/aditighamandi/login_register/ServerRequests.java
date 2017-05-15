package com.example.aditighamandi.login_register;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.View;

import org.apache.http.params.HttpParams;

import android.util.Pair;

import java.net.URLEncoder;
import java.util.List;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import java.io.BufferedReader;
import java.io.IOException;

import java.net.*;

import java.io.*;

import android.util.*;

import org.json.JSONObject;
/**
 * Created by aditighamandi on 3/20/17.
 */

public class ServerRequests {

    ProgressDialog progressDialog;

    public static final int CONNECTION_TIMEOUT= 1000*15;

    public static final String SERVER_ADDRESS = "localhost:8888/";

    public ServerRequests(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");

    }

    public void storeUserDataInBackground(User user, getUserCallback userCallback){

        progressDialog.show();
        new storedUserDataAsyncTask(user,userCallback).execute();

    }

    public void fetchDataInBackground(User user, getUserCallback callback){
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callback).execute();

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

    public class storedUserDataAsyncTask extends AsyncTask<Void,Void,Void>{

        User user;
        getUserCallback userCallback;

        public storedUserDataAsyncTask(User user, getUserCallback userCallback){
            this.user = user;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {
            Map<String,String> dataToSend = new HashMap<>();
            dataToSend.put("name", user.name);
            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);
            dataToSend.put("category", user.category);

            String encodedStr = getEncodedData(dataToSend);
            BufferedReader reader = null;

            try {
                //Converting address String to URL
                URL url = new URL("http","10.0.2.2",80,"Register.php");
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
            progressDialog.dismiss();
            userCallback.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        getUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, getUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }

        @Override
        protected User doInBackground(Void... params) {
            Map<String,String> dataToSend = new HashMap<>();

            dataToSend.put("username", user.username);
            dataToSend.put("password", user.password);

            String encodedStr = getEncodedData(dataToSend);

            BufferedReader reader = null;
            User returnedUser = null;
            try {
                //Converting address String to URL
                URL url = new URL("http","10.0.2.2",80,"FetchData.php");
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

                JSONObject jObject = new JSONObject(line);

                if(jObject.length()== 0){
                    returnedUser = null;
                } else {
                    String name = jObject.getString("name");

                    String category = jObject.getString("category");

                    returnedUser = new User(name, user.username, user.password,category);
                }
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

            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);

            super.onPostExecute(returnedUser);
        }
    }




}
