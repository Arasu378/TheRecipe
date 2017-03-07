package com.app.kyrostechnologies.recipe;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.kyrostechnologies.recipe.adapter.ZomatoRatingAdapter;
import com.app.kyrostechnologies.recipe.ratingspojo.RatingZomato;
import com.app.kyrostechnologies.recipe.ratingspojo.UserReview;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantDetails extends AppCompatActivity {
    private  CollapsingToolbarLayout collapsingToolbar;
    private String apikey=null;
    private String id=null;
    private String shopname=null;
    private String cuisines=null;
    private int average_cost_for_two=0;
    private int price_range=0;
    private String  currency=null;
    private String thumburl=null;
    private String featured_image=null;
    private int has_online_delivery=0;
    private int is_delivering_now=0;
    private int has_table_booking=0;
    private String  menuurl=null;
    private String  photos_url=null;
    private String events_url=null;
    private String shopaddress=null;
    private String locality=null;
    private String locality_verbose=null;
    private String city=null;
    private int city_id=0;
    private String latitude=null;
    private String longitude=null;
    private String zipcode=null;
    private String aggregate_rating=null;
    private String rating_text=null;
    private String rating_color=null;
    private String votes=null;
    private ImageView header_feature_pic;
    private TextView shop_name_details,shop_address_details,
            shop_cuisines_details,rating_text12,votes_details,cuisinesdet
            ,avg_cost,address_shop_details,mapsdirect;
   private CardView card_back_ground;
    private RecyclerView recyclerView_ratings;

    private String count="20";
    private String start="1";
    private ZomatoRatingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle=getIntent().getExtras();
        apikey= bundle.getString("apikey");
        id= bundle.getString("id");
        shopname= bundle.getString("shopname");
        cuisines= bundle.getString("cuisines");
        average_cost_for_two= bundle.getInt("average_cost_for_two");
        price_range= bundle.getInt("price_range");
        currency= bundle.getString("currency");
        thumburl= bundle.getString("thumburl");
        featured_image= bundle.getString("featured_image");
        has_online_delivery= bundle.getInt("has_online_delivery");
        is_delivering_now= bundle.getInt("is_delivering_now");
        has_table_booking= bundle.getInt("has_table_booking");
        menuurl= bundle.getString("menuurl");
        photos_url= bundle.getString("photos_url");
        events_url= bundle.getString("events_url");
        shopaddress= bundle.getString("shopaddress");
        locality= bundle.getString("locality");
        locality_verbose= bundle.getString("locality_verbose");
        city= bundle.getString("city");
        city_id= bundle.getInt("city_id");
        latitude= bundle.getString("latitude");
        longitude= bundle.getString("longitude");
        zipcode= bundle.getString("zipcode");
        aggregate_rating= bundle.getString("aggregate_rating");
        rating_text= bundle.getString("rating_text");
        rating_color= bundle.getString("rating_color");
        votes= bundle.getString("votes");


         collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        toolbarTextAppernce();
        header_feature_pic=(ImageView)findViewById(R.id.header_feature_pic);
        shop_name_details=(TextView)findViewById(R.id.shop_name_details);
        shop_address_details=(TextView)findViewById(R.id.shop_address_details);
        shop_cuisines_details=(TextView)findViewById(R.id.shop_cuisines_details);
        rating_text12=(TextView)findViewById(R.id.rating_text);
        votes_details=(TextView)findViewById(R.id.votes_details);
        cuisinesdet=(TextView)findViewById(R.id.cuisinesdet);
        avg_cost=(TextView)findViewById(R.id.avg_cost);
        address_shop_details=(TextView)findViewById(R.id.address_shop_details);
        mapsdirect=(TextView)findViewById(R.id.mapsdirect);
        card_back_ground=(CardView)findViewById(R.id.card_back_ground);
        recyclerView_ratings=(RecyclerView)findViewById(R.id.recyclerView_ratings);
        if(featured_image!=null){
            Picasso.with(this).load(featured_image).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    header_feature_pic.setImageBitmap(bitmap);
                    header_feature_pic.setBackground(null);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    header_feature_pic.setBackgroundResource(R.drawable.food_drink);
                }
            });
        }
        if(average_cost_for_two!=0){
            String value=getResources().getString(R.string.Rs)+" "+average_cost_for_two +" for two (approx.)";

            avg_cost.setText(value);
        }
        if(cuisines!=null){
            String value=cuisines+".";
            cuisinesdet.setText(value);
            shop_cuisines_details.setText("Cuisines "+value);
        }
        if(locality_verbose!=null){
            shop_address_details.setText(locality_verbose);
        }
        if(shopname!=null){
            shop_name_details.setText(shopname);
            collapsingToolbar.setTitle(shopname);
        }
        if(votes!=null){
            String value="Based on "+votes+" ratings";
            votes_details.setText(value);
        }
        if(aggregate_rating!=null){
            rating_text12.setText(aggregate_rating);
        }
        if(rating_color!=null){
            String value="#"+rating_color;
            card_back_ground.setCardBackgroundColor(Color.parseColor(value));
        }
        if(shopaddress!=null){
            address_shop_details.setText(shopaddress);
        }
        mapsdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Directions clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        if(id!=null){
            getRatingsapi(id,start,count);
        }
    }

    private void getRatingsapi(String id,String start,String count) {
        GooglepojoInter apiservice=ApiClient.getZomatoretrofit().create(GooglepojoInter.class);
        Call<RatingZomato>call=apiservice.getRatings(id,start,count);
        call.enqueue(new Callback<RatingZomato>() {
            @Override
            public void onResponse(Call<RatingZomato> call, Response<RatingZomato> response) {
                Log.d("Zomatoresponserating", response.body().toString());
                Toast.makeText(getApplicationContext(), "Successfully received zomato rating api response", Toast.LENGTH_SHORT).show();
                RatingZomato ratingZomato= response.body();
                int  reviews_count=ratingZomato.getReviewsCount();
                int reviews_start=ratingZomato.getReviewsStart();
                int reviews_shown=ratingZomato.getReviewsShown();
                List<UserReview> userReviews=ratingZomato.getUserReviews();
                adapter=new ZomatoRatingAdapter(userReviews,RestaurantDetails.this);
                RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
                recyclerView_ratings.setLayoutManager(manager);
                recyclerView_ratings.setItemAnimator(new DefaultItemAnimator());
                recyclerView_ratings.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<RatingZomato> call, Throwable t) {
                Log.e("Error in  rating api", t.toString());
                Toast.makeText(getApplicationContext(), "Error in zomato rating api: " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                RestaurantDetails.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}

