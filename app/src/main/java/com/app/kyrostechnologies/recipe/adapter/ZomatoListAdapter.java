package com.app.kyrostechnologies.recipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.kyrostechnologies.recipe.R;
import com.app.kyrostechnologies.recipe.RestaurantDetails;
import com.app.kyrostechnologies.recipe.pojozomato.Location_;
import com.app.kyrostechnologies.recipe.pojozomato.NearbyRestaurant;
import com.app.kyrostechnologies.recipe.pojozomato.Restaurant;
import com.app.kyrostechnologies.recipe.pojozomato.UserRating;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Thirunavukkarasu on 03-03-2017.
 */

public class ZomatoListAdapter extends RecyclerView.Adapter<ZomatoListAdapter.MyViewHolder> {
    private Context mContext;
    private List<NearbyRestaurant>zomatolist;
    private  int sd=0;

    public  ZomatoListAdapter(List<NearbyRestaurant>zomatolist, Context mContext){
       this.zomatolist=zomatolist;
        this.mContext=mContext;


    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout relative_background;
        public TextView rating_zomato,shop_name_zomato,shop_add_zomato,shop_special_zomato,shop_average_cost_zomato;
        public CardView card_view_rating_back,card_next_page;

        public MyViewHolder(View itemView) {
            super(itemView);
            relative_background=(LinearLayout)itemView.findViewById(R.id.relative_background);
            rating_zomato=(TextView)itemView.findViewById(R.id.rating_zomato);
            shop_name_zomato=(TextView)itemView.findViewById(R.id.shop_name_zomato);
            shop_add_zomato=(TextView)itemView.findViewById(R.id.shop_add_zomato);
            shop_special_zomato=(TextView)itemView.findViewById(R.id.shop_special_zomato);
            shop_average_cost_zomato=(TextView)itemView.findViewById(R.id.shop_average_cost_zomato);
            card_view_rating_back=(CardView)itemView.findViewById(R.id.card_view_rating_back);
            card_next_page=(CardView)itemView.findViewById(R.id.card_next_page);


        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_zomato_card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NearbyRestaurant nearbyRestaurant=zomatolist.get(position);
        Restaurant restaurant=nearbyRestaurant.getRestaurant();
        final String apikey=restaurant.getApikey();
        final String id=restaurant.getId();
        final String shopname=restaurant.getName();
        final String cuisines=restaurant.getCuisines();
        final int  average_cost_for_two=restaurant.getAverageCostForTwo();
        final int price_range=restaurant.getPriceRange();
        final String currency=restaurant.getCurrency();
        final String thumburl=restaurant.getThumb();
        final String featured_image=restaurant.getFeaturedImage();
        final int has_online_delivery=restaurant.getHasOnlineDelivery();
        final int is_delivering_now=restaurant.getIsDeliveringNow();
        final int has_table_booking=restaurant.getHasTableBooking();
        final String menuurl=restaurant.getMenuUrl();
        final String photos_url=restaurant.getPhotosUrl();
        final String events_url=restaurant.getEventsUrl();
        Location_ location=restaurant.getLocation();
        final String shopaddress=location.getAddress();
        final String locality=location.getLocality();
        final String locality_verbose=location.getLocalityVerbose();
        final String city=location.getCity();
        final int city_id=location.getCityId();
        final String latitude=location.getLatitude();
        final String longitude=location.getLongitude();
        final String zipcode=location.getZipcode();
       List<Object>offerslist= restaurant.getOffers();
        UserRating user_rating=restaurant.getUserRating();

        final String aggregate_rating=user_rating.getAggregateRating();
        final String rating_text=user_rating.getRatingText();
        final String rating_color=user_rating.getRatingColor();
          final String votes=user_rating.getVotes();
        if(average_cost_for_two!=0){
            String value=mContext.getResources().getString(R.string.Rs)+" "+average_cost_for_two +" for two (approx.)";
            holder.shop_average_cost_zomato.setText(value);
        }
        if(shopname!=null){
            holder.shop_name_zomato.setText(shopname);
        }
        if(locality_verbose!=null){
            holder.shop_add_zomato.setText(locality_verbose);
        }
        if(cuisines!=null){
            String value="Cusines : "+cuisines;
            holder.shop_special_zomato.setText(value);
        }
        if(aggregate_rating!=null && rating_color!=null){
            holder.rating_zomato.setText(aggregate_rating);
            String value="#"+rating_color;
            try{
                holder.card_view_rating_back.setCardBackgroundColor(Color.parseColor(value));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(thumburl!=null){
            Picasso.with(mContext).load(thumburl).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Drawable drawable=new BitmapDrawable(bitmap);
                    holder.relative_background.setBackground(drawable);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Log.e("Bitmap list","failed");
                    holder.relative_background.setBackgroundResource(R.drawable.food_drink);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        }
//        setAnimation(holder.itemView, position);
        holder.card_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i= new Intent(mContext, RestaurantDetails.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("apikey",apikey);
                    bundle.putString("id",id);
                    bundle.putString("shopname",shopname);
                    bundle.putString("cuisines",cuisines);
                    bundle.putInt("average_cost_for_two",average_cost_for_two);
                    bundle.putInt("price_range",price_range);
                    bundle.putString("currency",currency);
                    bundle.putString("thumburl",thumburl);
                    bundle.putString("featured_image",featured_image);
                    bundle.putInt("has_online_delivery",has_online_delivery);
                    bundle.putInt("is_delivering_now",is_delivering_now);
                    bundle.putInt("has_table_booking",has_table_booking);
                    bundle.putString("menuurl",menuurl);
                    bundle.putString("photos_url",photos_url);
                    bundle.putString("events_url",events_url);
                    bundle.putString("shopaddress",shopaddress);
                    bundle.putString("locality",locality);
                    bundle.putString("locality_verbose",locality_verbose);
                    bundle.putString("city",city);
                    bundle.putInt("city_id",city_id);
                    bundle.putString("latitude",latitude);
                    bundle.putString("longitude",longitude);
                    bundle.putString("zipcode",zipcode);
                    bundle.putString("aggregate_rating",aggregate_rating);
                    bundle.putString("rating_text",rating_text);
                    bundle.putString("rating_color",rating_color);
                    bundle.putString("votes",votes);
                    i.putExtras(bundle);
                    mContext.startActivity(i);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return zomatolist.size();
    }
    private int lastPosition = -1;

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
