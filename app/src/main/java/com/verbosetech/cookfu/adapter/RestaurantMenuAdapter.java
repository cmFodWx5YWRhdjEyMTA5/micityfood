package com.verbosetech.cookfu.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.DataAdapters.CuisineItems;
import com.verbosetech.cookfu.Database.Database;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.model.CartItem;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a_man on 24-01-2018.
 */

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.MyViewHolder> {
    private Context context;
    private List<CuisineItems> dataList;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private String temp_marchantID = null;

    //    private List<CuisineItems> cuisineItemsList;
    public RestaurantMenuAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public RestaurantMenuAdapter(Context context, List<CuisineItems> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant_menu, parent, false));
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
        private TextView itemName, itemDescription, itemPrice;
        private ImageView itemImage, menuItemAction;

        public MyViewHolder(View itemView) {
            super(itemView);
            CutsomSharedPreferences.init(context);
            cartItems = new ArrayList<>();
            itemName = itemView.findViewById(R.id.menuItemName);
            itemDescription = itemView.findViewById(R.id.menuItemDescription);
            itemPrice = itemView.findViewById(R.id.menuItemPrice);
            itemImage = itemView.findViewById(R.id.menuItemImage);
            menuItemAction = itemView.findViewById(R.id.menuItemAction);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    context.startActivity(FoodDetailActivity.newIntent(context, dataList.get(getAdapterPosition())));
//                }
//            });

            menuItemAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();
                    final String marchantID = dataList.get(pos).getMerchant_id();
                    temp_marchantID = CutsomSharedPreferences.Read(CutsomSharedPreferences.TEMP_MARCHANT_ID,null);
                    Log.d("Marchant ID", marchantID);
                    try {
                        if (marchantID.equals(temp_marchantID)) {
                            CutsomSharedPreferences.Write(CutsomSharedPreferences.TEMP_MARCHANT_ID, marchantID);
                            Log.d("Temp & Machant equal", temp_marchantID +" " + marchantID);
                            dataList.get(pos).setAdded(!dataList.get(pos).isAdded());
                            new Database(context).addToCart(new CartItem(dataList.get(pos).getItem_name(), Integer.parseInt(dataList.get(pos).getPrice()), dataList.get(pos).getPhoto(), 1));
                            notifyItemChanged(pos);
                        } else {
                            Log.d("Temp & Machant !equal", temp_marchantID +" " + marchantID);
                            int DATABASE_COUNT = new Database(context).getProfilesCount();
                            Log.d("Database Count", String.valueOf(DATABASE_COUNT));
                            if (DATABASE_COUNT >= 0) {

                                Log.d("Database Count", "Greater than 0");
                                new AlertDialog.Builder(context)
                                        .setIcon(R.drawable.ic_home_dark)
                                        .setTitle("You already have items from one Restaurant in the cart.")
                                        .setMessage("Are you sure you want to remove them and new items?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                temp_marchantID = marchantID;
                                                CutsomSharedPreferences.Write(CutsomSharedPreferences.TEMP_MARCHANT_ID, marchantID);
                                                dataList.get(pos).setAdded(!dataList.get(pos).isAdded());
                                                new Database(context).cleanCart();
                                                new Database(context).addToCart(new CartItem(dataList.get(pos).getItem_name(), Integer.parseInt(dataList.get(pos).getPrice()), dataList.get(pos).getPhoto(), 1));
                                                notifyItemChanged(pos);
                                            }

                                        })
                                        .setNegativeButton("No", null)
                                        .show();
                            } else {
                                Log.d("Database Count", "Less than 0");
                                temp_marchantID = marchantID;
                                CutsomSharedPreferences.Write(CutsomSharedPreferences.TEMP_MARCHANT_ID, marchantID);
                                new Database(context).cleanCart();
                                new Database(context).addToCart(new CartItem(dataList.get(pos).getItem_name(), Integer.parseInt(dataList.get(pos).getPrice()), dataList.get(pos).getPhoto(), 1));
                                notifyItemChanged(pos);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            });
        }

        public void setData(CuisineItems CuisineItems) {
            Log.d("Image String", CuisineItems.getPhoto());
            Glide.with(context).load(CuisineItems.getPhoto()).into(itemImage);
            String htmlAsString = CuisineItems.getItem_description();      // used by WebView
            Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);
            itemPrice.setText(String.valueOf(CuisineItems.getPrice()));
            itemDescription.setText(htmlAsSpanned);
            itemName.setText(CuisineItems.getItem_name());
            menuItemAction.setImageDrawable(ContextCompat.getDrawable(context, CuisineItems.isAdded() ? R.drawable.ic_done_primary_24dp : R.drawable.ic_add_circle_primary_24dp));
        }

    }
}
