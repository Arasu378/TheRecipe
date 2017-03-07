package com.app.kyrostechnologies.recipe.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Thirunavukkarasu on 01-03-2017.
 */

public class PreferenceManager {
    private static PreferenceManager store;
    private final SharedPreferences sp;
    private PreferenceManager(Context context){
        String filename="login_credentials";
        sp=context.getApplicationContext().getSharedPreferences(filename,Context.MODE_PRIVATE);
    }
    public static PreferenceManager getStore(Context context){
        if(store==null){
            store=new PreferenceManager(context);
        }
        return store;
    }
    public String getDisplayName(){
        return sp.getString("DisplayName",null);
    }
    public void putDisplayName(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("DisplayName", value);
        editor.commit();
    }
    public String getEmail(){
        return sp.getString("Email",null);
    }
    public void putEmail(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Email", value);
        editor.commit();
    } public String getId(){
        return sp.getString("Id",null);
    }
    public void putId(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("Id", value);
        editor.commit();
    }public String getProfilePic(){
        return sp.getString("ProfilePic",null);
    }
    public void putProfilePic(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ProfilePic", value);
        editor.commit();
    }public String getServerAuthCode(){
        return sp.getString("ServerAuthCode",null);
    }
    public void putServerAuthCode(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("ServerAuthCode", value);
        editor.commit();
    }
    public String getIsSuccess(){
        return sp.getString("IsSuccess",null);
    }
    public void putIsSuccess(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("IsSuccess",value);
        editor.commit();
    }
    public String getCoverPicture(){
        return sp.getString("CoverPicture",null);
    }
    public void putCoverPicture(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("CoverPicture",value);
        editor.commit();
    }
    public String getwhichone(){
        return sp.getString("whichone",null);
    }
    public void putwhichone(String value){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.putString("whichone",value);
        editor.commit();
    }
    public void clear(){
        SharedPreferences.Editor editor;
        editor=sp.edit();
        editor.clear();
        editor.commit();
    }

}
