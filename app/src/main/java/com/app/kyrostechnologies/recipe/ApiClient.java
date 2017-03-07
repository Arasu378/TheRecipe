package com.app.kyrostechnologies.recipe;

import com.app.kyrostechnologies.recipe.data.URLSConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Thirunavukkarasu on 02-03-2017.
 */

public class ApiClient {
private static Retrofit retrofit=null;
    private static Retrofit zomatoretrofit=null;

    public static Retrofit getgoogleRetrofit(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(URLSConstant.Google_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getZomatoretrofit(){
        if(zomatoretrofit==null){
            zomatoretrofit=new Retrofit.Builder()
                    .baseUrl(URLSConstant.Zomato_GeoCodeUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return zomatoretrofit;
    }
}
