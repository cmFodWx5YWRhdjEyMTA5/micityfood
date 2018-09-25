package com.verbosetech.cookfu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.model.CategoryFood;

import java.util.ArrayList;

/**
 * Created by a_man on 20-01-2018.
 */

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CategoryFood> dataList;

    public FoodCategoryAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new CategoryFood("Fast food", 88, R.drawable.rest_res_1));
        this.dataList.add(new CategoryFood("South indian", 56, R.drawable.rest_res_2));
        this.dataList.add(new CategoryFood("Chinese", 48, R.drawable.rest_res_3));
        this.dataList.add(new CategoryFood("North indian", 36, R.drawable.rest_res_4));
        this.dataList.add(new CategoryFood("Fast food", 88, R.drawable.rest_res_1));
        this.dataList.add(new CategoryFood("South indian", 56, R.drawable.rest_res_2));
        this.dataList.add(new CategoryFood("Chinese", 48, R.drawable.rest_res_3));
        this.dataList.add(new CategoryFood("North indian", 36, R.drawable.rest_res_4));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_food_category, parent, false));
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
        private TextView name, count;
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_food_category_name);
            count = itemView.findViewById(R.id.item_food_category_count);
            image = itemView.findViewById(R.id.item_food_category_image);
        }

        public void setData(CategoryFood categoryFood) {
            name.setText(categoryFood.getName());
            count.setText(categoryFood.getCountRestaurant());
            Glide.with(context).load(categoryFood.getImageRes()).into(image);
        }
    }
}
