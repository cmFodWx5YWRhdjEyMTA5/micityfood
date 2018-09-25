package com.verbosetech.cookfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.DataAdapters.ResturantsByAddress;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.rest_detail.RestaurantDetailActivity;

import java.util.List;

/**
 * Created by a_man on 22-01-2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private Context context;
    private List<ResturantsByAddress> dataList;

    public RestaurantAdapter(Context context, List<ResturantsByAddress> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false));
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
        private TextView name, description, restMinOrderPrice, deliveryfee, deliveryTime;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restName);
            description = itemView.findViewById(R.id.restDesc);
            restMinOrderPrice = itemView.findViewById(R.id.restMinOrderPrice);
            deliveryfee = itemView.findViewById(R.id.delivery_fee);
            deliveryTime = itemView.findViewById(R.id.del_estimate);
            imageView = itemView.findViewById(R.id.restRes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(RestaurantDetailActivity.newIntent(context, dataList.get(getAdapterPosition())));
                }

            });
        }

        public void setData(ResturantsByAddress restaurant) {
            name.setText(restaurant.getRestaurant_name());
            description.setText(restaurant.getCuisine());
            restMinOrderPrice.setText(restaurant.getMinimum_order());
            deliveryfee.setText(restaurant.getDelivery_fee());
            deliveryTime.setText(restaurant.getDelivery_est());
            Glide.with(context).load(restaurant.getRest_logo()).into(imageView);
        }
    }

}
