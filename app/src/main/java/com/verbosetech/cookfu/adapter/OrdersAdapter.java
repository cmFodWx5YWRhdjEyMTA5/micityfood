package com.verbosetech.cookfu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.model.Order;

import java.util.ArrayList;

/**
 * Created by a_man on 24-01-2018.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Order> dataList;

    public OrdersAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new Order("14 May", "China gate chef", "Mulund, Mumbai", 1225, 0));
        this.dataList.add(new Order("10 May", "Sugar and spice chef", "Vila parle, Mumbai", 980, 1));
        this.dataList.add(new Order("9 May", "Old spice chef", "Mulund, Mumbai", 1258, 1));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
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
        private View orderPreparingContainer;
        private TextView orderDate, orderPlaceName, orderPlaceAddress, orderTotal, orderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderPreparingContainer = itemView.findViewById(R.id.orderPreparingContainer);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderPlaceName = itemView.findViewById(R.id.orderPlaceName);
            orderPlaceAddress = itemView.findViewById(R.id.orderPlaceAddress);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderStatus = itemView.findViewById(R.id.orderStatus);
        }

        public void setData(Order order) {
            orderPreparingContainer.setVisibility(order.getStatus() == 0 ? View.VISIBLE : View.GONE);
            order.setDate(order.getDate().replace(" ", "\n"));
            orderDate.setText(order.getDate());
            orderPlaceAddress.setText(order.getPlaceAddress());
            orderPlaceName.setText(order.getPlaceName());
            orderTotal.setText(String.valueOf(order.getOrderTotal()));
            orderStatus.setText(order.getStatus() == 0 ? "Preparing" : "Delivered");
            orderStatus.setBackground(ContextCompat.getDrawable(context, order.getStatus() == 0 ? R.drawable.round_circle_accent : R.drawable.round_primary));
        }
    }
}
