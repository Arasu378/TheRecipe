package com.app.kyrostechnologies.recipe;

import com.app.kyrostechnologies.recipe.pojocovergoogle.GoogleGetCoverPic;
import com.app.kyrostechnologies.recipe.pojozomato.ZomatoApi;
import com.app.kyrostechnologies.recipe.ratingspojo.RatingZomato;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Thirunavukkarasu on 02-03-2017.
 */

public interface GooglepojoInter {
    @GET("/plus/v1/people/{me}")
    Call<GoogleGetCoverPic>getCoverPic(@Path("me")String me, @Query("key")String key);
    @Headers({
            "content-type: application/json",
            "user-key: 07c037d8512b6aba744f0f569e3c0600"
    })
    @GET("/api/v2.1/geocode")
    Call<ZomatoApi>getZomatoRes(@Query("lat")String latitude,@Query("lon")String longitude);
    @Headers({
            "content-type: application/json",
            "user-key: 07c037d8512b6aba744f0f569e3c0600"
    })
    @GET("/api/v2.1/reviews")
    Call<RatingZomato>getRatings(@Query("res_id")String res_id,@Query("start")String start,@Query("count")String count);
}
