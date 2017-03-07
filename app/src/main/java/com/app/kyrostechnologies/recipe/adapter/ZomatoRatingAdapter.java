package com.app.kyrostechnologies.recipe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.kyrostechnologies.recipe.R;
import com.app.kyrostechnologies.recipe.ratingspojo.Review;
import com.app.kyrostechnologies.recipe.ratingspojo.User;
import com.app.kyrostechnologies.recipe.ratingspojo.UserReview;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thirunavukkarasu on 04-03-2017.
 */

public class ZomatoRatingAdapter extends RecyclerView.Adapter<ZomatoRatingAdapter.MyViewHolder> {
    private List<UserReview>ratinglist;
    private Context mContext;
    public ZomatoRatingAdapter(List<UserReview>ratinglist,Context mContext){
        this.mContext=mContext;
        this.ratinglist=ratinglist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView foodie_pic_url;
        public TextView foodie_name,foodie_level,foodie_date,foodie_rated,foodie_rating_value
                ,foodie_comment,likesand_comments_foodie;
        public CardView foodie_rating;

        public MyViewHolder(View itemView) {
            super(itemView);
            foodie_pic_url=(ImageView)itemView.findViewById(R.id.foodie_pic_url);
            foodie_name=(TextView)itemView.findViewById(R.id.foodie_name);
            foodie_level=(TextView)itemView.findViewById(R.id.foodie_level);
            foodie_date=(TextView)itemView.findViewById(R.id.foodie_date);
            foodie_rated=(TextView)itemView.findViewById(R.id.foodie_rated);
            foodie_rating_value=(TextView)itemView.findViewById(R.id.foodie_rating_value);
            foodie_comment=(TextView)itemView.findViewById(R.id.foodie_comment);
            likesand_comments_foodie=(TextView)itemView.findViewById(R.id.likesand_comments_foodie);
            foodie_rating=(CardView)itemView.findViewById(R.id.foodie_rating);


        }
    }
    @Override
    public ZomatoRatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_list_item,null);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       UserReview userReview=ratinglist.get(position);
        Review review=userReview.getReview();
        double rating=review.getRating();
        String review_text=review.getReviewText();
        String id=review.getId();
        String rating_color=review.getRatingColor();
        String review_time_friendly=review.getReviewTimeFriendly();
        String rating_text=review.getRatingText();
        int timestamp=review.getTimestamp();
        int likes=review.getLikes();
        int comments_count=review.getCommentsCount();
        User user=review.getUser();
        String name=user.getName();
        String foodie_level=user.getFoodieLevel();
        int  foodie_level_num=user.getFoodieLevelNum();
        String foodie_color=user.getFoodieColor();
        String profile_image=user.getProfileImage();
        if(profile_image!=null){
            Picasso.with(mContext).load(profile_image).resize(50,50).into(holder.foodie_pic_url);
        }if(name!=null){
            holder.foodie_name.setText(name);
        }
        if(foodie_level!=null){
            holder.foodie_level.setText(foodie_level);
        }
        if(foodie_color!=null){
            String value="#"+foodie_color;
            holder.foodie_level.setTextColor(Color.parseColor(value));
        }if(review_time_friendly!=null){
            holder.foodie_date.setText(review_time_friendly);
        }if(rating!=0){
            String value=""+rating;
            holder.foodie_rating_value.setText(value);
        }if(rating_color!=null){
            String value="#"+rating_color;
            holder.foodie_rating.setCardBackgroundColor(Color.parseColor(value));
        }if(review_text!=null){
            holder.foodie_comment.setText(review_text);
        }
        String values=likes+" Likes. "+comments_count+" Comments.";
        holder.likesand_comments_foodie.setText(values);









    }



    @Override
    public int getItemCount() {
        return ratinglist.size();
    }
}
