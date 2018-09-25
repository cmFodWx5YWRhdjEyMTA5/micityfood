package com.verbosetech.cookfu.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.verbosetech.cookfu.DataAdapters.ResturantsByAddress;
import com.verbosetech.cookfu.Database.Database;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.adapter.CartAdapter;
import com.verbosetech.cookfu.checkout.CheckoutActivity;
import com.verbosetech.cookfu.model.CartItem;
import com.verbosetech.cookfu.rest_detail.RestaurantDetailActivity;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a_man on 23-01-2018.
 */

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView cartRecycler;
    private List<CartItem> cartItems;
    private CardView cart_cardView;
    private TextView sub_total, cart_gst, cart_total, cart_delivery_fee,cart_empty;
    private LinearLayout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CutsomSharedPreferences.init(this); // Initializing Custom Shared Preference
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        cart_cardView = findViewById(R.id.cart_cardView);
        sub_total=findViewById(R.id.sub_total);
        cart_gst = findViewById(R.id.cart_gst);
        cart_total= findViewById(R.id.cart_total);
        cart_delivery_fee = findViewById(R.id.cart_delivery_fee);
        cart_empty = findViewById(R.id.cart_empty);
        checkout = findViewById(R.id.checkout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartItems = new ArrayList<>();
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartRecycler = findViewById(R.id.cartRecycler);
        checkout.setOnClickListener(this);
        loadFoodList();
    }
    public void loadFoodList(){
        cartItems = new Database(this).getCart();
        setupCartRecycler(cartItems);
        double sub_tot = 0;
        double total = 0.0;
        Log.d("CartActivity", String.valueOf(cartItems));
        if (cartItems.isEmpty()){
            Log.d("Cart Items are" , "Emplty");
            cart_empty.setVisibility(View.VISIBLE);
            cart_cardView.setVisibility(View.GONE);
            checkout.setEnabled(false);
            checkout.setBackgroundColor(Color.GRAY);
            cart_total.setText("");
        }else{
            cart_empty.setVisibility(View.GONE);
            cart_cardView.setVisibility(View.VISIBLE);
            checkout.setEnabled(true);
            checkout.setBackgroundColor(getResources().getColor((R.color.colorAccent)));
            for(CartItem cartItem:cartItems) {
                sub_tot += cartItem.getPrice() * cartItem.getQuantity();
            }
            total = sub_tot + (sub_tot * 0.16) + 40 ;
            sub_total.setText("₹ "+String.valueOf(sub_tot));
            cart_total.setText("₹ " + String.valueOf(total));
        }

    }

    private void setupCartRecycler(List<CartItem> cartItems) {
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartRecycler.setAdapter(new CartAdapter(this, cartItems));
    }

    @Override
    public void onClick(View v) {
        if(v == checkout){
//            startActivity(new Intent(CartActivity.this, CheckoutActivity.class));
            new Database(CartActivity.this).cleanCart();
            finish();
        }
    }
}
