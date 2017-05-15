package com.example.aditighamandi.login_register;

import android.content.SharedPreferences;
import android.content.Context;

/**
 * Created by aditighamandi on 3/19/17.
 */

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){

        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);

    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name",user.name);
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.putString("category",user.category);
        spEditor.commit();

    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name","");
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password","");
        String category = userLocalDatabase.getString("category","");

        User storedUser = new User(name,username,password,category);


        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        /*
        int flag=0;
        if(userLocalDatabase.getBoolean("loggedIn",false)==true){
            flag =1;
        }else {
            flag = 0;
        }
        return flag;
        */
        //clearData();
        return userLocalDatabase.getBoolean("loggedIn",false);
    }


    public void clearData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
