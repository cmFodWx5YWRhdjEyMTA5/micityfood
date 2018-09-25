package com.verbosetech.cookfu.rest_detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.DataAdapters.ResturantsByAddress;
import com.verbosetech.cookfu.Database.Database;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.activity.CartActivity;
import com.verbosetech.cookfu.adapter.ViewPagerStateAdapter;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {
    private static String EXTRA_REST_NAME = "restaurant_name";
    private static String EXTRA_REST_ADDRESS = "address";
    private static String EXTRA_REST_COURSINE = "cuisine";
    private static String EXTRA_REST_AVGTIME = "delivery_est";
    private static String EXTRA_REST_MINORDER = "minimum_order";
    private static String EXTRA_REST_CHARGE = "delivery_fee";
    private static String EXTRA_REST_LOGO = "logo";
    private static String EXTRA_MARCHANT_ID = "marchant_id";
    public List<ResturantsByAddress> dataList;
    public ImageView details_image;
    public TextView restName, restDesc, avg_delivery, restMinOrderPrice, delivery_fee;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView cartNotificationCount;
    private Toolbar toolbar;
    private String title, cousine, delivery_est, minimum_order, delivery_fe, logo, marchantID;

    public static Intent newIntent(Context context, ResturantsByAddress restaurant) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(EXTRA_REST_NAME, restaurant.getRestaurant_name());
        intent.putExtra(EXTRA_REST_COURSINE, restaurant.getCuisine());
        intent.putExtra(EXTRA_REST_AVGTIME, restaurant.getDelivery_est());
        intent.putExtra(EXTRA_REST_MINORDER, restaurant.getMinimum_order());
        intent.putExtra(EXTRA_REST_CHARGE, restaurant.getDelivery_fee());
        intent.putExtra(EXTRA_REST_LOGO, restaurant.getRest_logo());
        intent.putExtra(EXTRA_MARCHANT_ID, restaurant.getMerchant_id());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_detail);
        dataList = new ArrayList<>();
        initUi();
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest_detail, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = cartActionView.findViewById(R.id.notification_count);
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RestaurantDetailActivity.this, CartActivity.class));
            }
        });
        setCartCount();
        return super.onCreateOptionsMenu(menu);
    }
    private void setCartCount() {
        int NOTIFICATION_COUNT = new Database(this).getProfilesCount();
        if (cartNotificationCount != null) {
            if (NOTIFICATION_COUNT <= 0) {
                cartNotificationCount.setVisibility(View.GONE);
            } else {
                cartNotificationCount.setVisibility(View.VISIBLE);
                cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
            }
        }
    }

    private void setupViewPager() {
        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        adapter.addFrag(new CuisineFragment(), "Cuisine");
        adapter.addFrag(new ReviewFragment(), "Review");
        adapter.addFrag(new InfoFragment(), "Info");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUi() {
        details_image = findViewById(R.id.details_image);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        details_image = findViewById(R.id.details_image);
        restName = findViewById(R.id.restName);
        restDesc = findViewById(R.id.restDesc);
        avg_delivery = findViewById(R.id.avg_delivery);
        restMinOrderPrice = findViewById(R.id.restMinOrderPrice);
        delivery_fee = findViewById(R.id.delivery_fee);

        getAndSetData();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void getAndSetData() {
        title = getIntent().getStringExtra(EXTRA_REST_NAME);
        if (!title.equals("")) {
            restName.setText(title);
        }
        cousine = getIntent().getStringExtra(EXTRA_REST_COURSINE);
        if (!cousine.equals("")) {
            restDesc.setText(cousine);
        }
        delivery_est = getIntent().getStringExtra(EXTRA_REST_AVGTIME);
        if (!delivery_est.equals("")) {
            avg_delivery.setText(delivery_est + " mins");
        } else
            avg_delivery.setText("N/A");
        minimum_order = getIntent().getStringExtra(EXTRA_REST_MINORDER);
        if (!minimum_order.equals("")) {
            restMinOrderPrice.setText(minimum_order);
        } else
            restMinOrderPrice.setText("N/A");
        delivery_fe = getIntent().getStringExtra(EXTRA_REST_CHARGE);
        if (!delivery_fe.equals("")) {
            delivery_fee.setText(delivery_fe);
        } else
            delivery_fee.setText("N/A");
        logo = getIntent().getStringExtra(EXTRA_REST_LOGO);
        if (!logo.equals("")) {
            Glide.with(this).load(logo).into(details_image);
        }
        marchantID = getIntent().getStringExtra(EXTRA_MARCHANT_ID);
        if (!marchantID.equals(null)) {
            Log.d("RestaurantDetailActivity MarID", "!equal blank " + marchantID );
            CutsomSharedPreferences.Write(CutsomSharedPreferences.MARCHANT_ID, marchantID);
        }else{
            Log.d("RestaurantDetailActivity MarID", "equal blank " + marchantID );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartCount();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCartCount();
    }

}
