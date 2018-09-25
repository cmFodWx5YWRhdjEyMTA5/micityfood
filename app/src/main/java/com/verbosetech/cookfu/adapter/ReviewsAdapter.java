package com.verbosetech.cookfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.model.Review;

import java.util.ArrayList;

/**
 * Created by a_man on 24-01-2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Review> dataList;

    public ReviewsAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new Review("Khana khazana chef", "MG point", "Awesome food taste as well as fast food delivery services. Really appreciate the packaging and quality of food. Go for it"));
        this.dataList.add(new Review("Sugar and spice chef", "Grant road", "Very slow delivery. Took more than one hour to deliver. Although taste was good."));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, location, reviewText, rating, ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.reviewName);
            location = itemView.findViewById(R.id.reviewLocation);
            reviewText = itemView.findViewById(R.id.reviewText);
        }

        public void setData(Review review) {
            name.setText(review.getTitle());
            location.setText(review.getLocation());
            reviewText.setText(review.getReview());
        }
    }
}
