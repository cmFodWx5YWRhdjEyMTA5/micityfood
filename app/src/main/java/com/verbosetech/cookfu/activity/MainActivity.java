package com.verbosetech.cookfu.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.verbosetech.cookfu.CustomAnimation.DepthPageTransformer;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.adapter.DrawerListAdapter;
import com.verbosetech.cookfu.adapter.ViewPageAdapterForBotNav;
import com.verbosetech.cookfu.fragment.DetailsFragment;
import com.verbosetech.cookfu.fragment.FavoriteFragment;
import com.verbosetech.cookfu.fragment.HomeFragment;
import com.verbosetech.cookfu.fragment.MyReviewsFragment;
import com.verbosetech.cookfu.fragment.OrdersFragment;
import com.verbosetech.cookfu.fragment.SupportFragment;
import com.verbosetech.cookfu.model.NavItem;
import com.verbosetech.cookfu.util.PermissionUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PermissionUtils.PermissionResultCallback {
    public static boolean isPermissionGranted;
    private final String FRAG_TAG_HOME = "Cookfu";
    private final String FRAG_TAG_FAVORITE = "Favorites";
    private final String FRAG_TAG_REVIEWS = "My Reviews";
    private final String FRAG_TAG_SUPPORT = "Contact us";
    private final String FRAG_TAG_ORDERS = "My Orders";
    private final String FRAG_TAG_DETAILS = "My Details";
    public TabLayout tabLayout;
    ListView mDrawerList;
    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private LinearLayout toolbarLayout;
    private Handler mHandler;

    // list of permissions
    private String fragTagCurrent = null;
    private int REQUEST_CODE_LOCATION = 99;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // set an exit transition
            getWindow().setExitTransition(new Explode());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        permissionUtils = new PermissionUtils(this);


        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

        ImageView background = findViewById(R.id.background);
        ImageView headerIcon = findViewById(R.id.headerIcon);
        Glide.with(this).load(R.drawable.chef_logo).into(headerIcon);
        Glide.with(this).load(R.drawable.background).into(background);
        //TODO Navigation Icons
        mNavItems.add(new NavItem("Home", "List of restaurants", R.drawable.ic_store_white_24dp));
        mNavItems.add(new NavItem("My orders", "Current and past orders", R.drawable.ic_restaurant_menu_white_24dp));
        mNavItems.add(new NavItem("My details", "Profile, address, payment info", R.drawable.ic_person_pin_white_24dp));
        mNavItems.add(new NavItem("Favorites", "Favoured chefs", R.drawable.ic_favorite_white_24dp));
        mNavItems.add(new NavItem("Reviews", "List of reviews", R.drawable.ic_local_activity_white_24dp));
        mNavItems.add(new NavItem("Support", "Have a chat with us", R.drawable.ic_chat_white_24dp));
        mNavItems.add(new NavItem("Rate us", "Rate us on playstore", R.drawable.ic_thumb_up_white_24dp));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerList = (ListView) findViewById(R.id.navList);
        toolbarLayout = findViewById(R.id.locationContainer);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        int[] icons = {
                R.drawable.tab_home,
                R.drawable.tab_search,
                R.drawable.tab_offers,
                R.drawable.tab_profile,
                R.drawable.tab_cart};
        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                fragTagCurrent = null;
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(4);
                        break;
                    case 2:
                        viewPager.setCurrentItem(3);
                        break;
                    case 3:
                        viewPager.setCurrentItem(2);
                        break;
                    case 4:
                        viewPager.setCurrentItem(1);
                        break;
                    case 5:
//                        fragTagCurrent = FRAG_TAG_SUPPORT;
                        break;
                    case 6:
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                if (fragTagCurrent != null) {
                    loadFragment(fragTagCurrent);
                    fragTagCurrent = null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

//        toolbarLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(MainActivity.this, LocationActivity.class), REQUEST_CODE_LOCATION);
//            }
//        });


        //Adding Bottom Navigation

        //TODO the main bottom navigation
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.main_tab_content);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActionBar mActionBar = getActionBar();
                if (position == 0) {
                    setTitle("Home");
                } else if (position == 1) {
                    setTitle("Search");
                } else if (position == 2) {
                    setTitle("Offers");
                } else if (position == 3) {
                    setTitle("You");
                } else if (position == 4) {
                    setTitle("Cart");
                }

            }
        });
        setupViewPager(viewPager);

        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(80, 80);
        for (int i = 0; i < icons.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(icons[i]);
            imageView.setLayoutParams(params);
            tabLayout.getTabAt(i).setCustomView(imageView);
        }
        tabLayout.getTabAt(0).select();


        viewPager.setOffscreenPageLimit(5);
//        presentShowcaseSequence();

//        loadFragment(FRAG_TAG_HOME);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPageAdapterForBotNav adapter = new ViewPageAdapterForBotNav(getSupportFragmentManager());
        adapter.insertNewFragment(new HomeFragment());
        adapter.insertNewFragment(new FavoriteFragment());
        adapter.insertNewFragment(new MyReviewsFragment());
        adapter.insertNewFragment(new DetailsFragment());
        adapter.insertNewFragment(new OrdersFragment());
        viewPager.setAdapter(adapter);
    }

    private void loadFragment(final String fragTag) {
        Fragment fragment = null;
        toolbarLayout.setVisibility(View.GONE);
        switch (fragTag) {
            case FRAG_TAG_HOME:
                toolbarLayout.setVisibility(View.VISIBLE);
                fragment = new HomeFragment();
                break;
            case FRAG_TAG_FAVORITE:
                fragment = new FavoriteFragment();
                break;
            case FRAG_TAG_REVIEWS:
                fragment = new MyReviewsFragment();
                break;
            case FRAG_TAG_DETAILS:
                fragment = new DetailsFragment();
                break;
            case FRAG_TAG_ORDERS:
                fragment = new OrdersFragment();
                break;
            case FRAG_TAG_SUPPORT:
                fragment = new SupportFragment();
                break;
        }

        getSupportActionBar().setTitle(fragTag);
        getSupportActionBar().setDisplayShowTitleEnabled(!fragTag.equals(FRAG_TAG_HOME));

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app

//        final Fragment finalFragment = fragment;
//        Runnable mPendingRunnable = new Runnable() {
//            @Override
//            public void run() {
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//                fragmentTransaction.replace(R.id.mainFrame, finalFragment, fragTag);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        };
//
//        if (mHandler == null)
//            mHandler = new Handler();
//        if (getSupportFragmentManager().findFragmentByTag(fragTag) == null)
//            mHandler.post(mPendingRunnable);


    }
    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }

    //TODO SHOWCASE
//    private void presentShowcaseSequence() {
//        TabLayout tabLayout = ButterKnife.findById(MainActivity.this, R.id.tab_layout);
//        View t1,t2,t3,t4,t5 = null;
//        if (tabLayout != null) {
//            TabLayout.Tab tab = tabLayout.getTabAt(1);
//            if (tab != null) {
//
//                ShowcaseConfig config = new ShowcaseConfig();
//                config.setDelay(500);
//                MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);
//
//                sequence.setConfig(config);
//                t1 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
//                t2 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
//                t3 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(2);
//                t4 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(3);
//                t5 = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(4);
//                sequence.addSequenceItem(
//                        new MaterialShowcaseView.Builder(this)
//                                .setTarget(t1)
//                                .setDismissText("GOT IT")
//                                .setContentText("Here you can view your profile")
//                                .withCircleShape()
//                                .build()
//                );
//                sequence.addSequenceItem(
//                        new MaterialShowcaseView.Builder(this)
//                                .setTarget(t2)
//                                .setDismissText("GOT IT")
//                                .setContentText("Here you can see all the study materials.")
//                                .withCircleShape()
//                                .build()
//                );
//                sequence.addSequenceItem(
//                        new MaterialShowcaseView.Builder(this)
//                                .setTarget(t3)
//                                .setDismissText("GOT IT")
//                                .setContentText("This is where you get to the Library and the store.")
//                                .withCircleShape()
//                                .build()
//                );
//                sequence.addSequenceItem(
//                        new MaterialShowcaseView.Builder(this)
//                                .setTarget(t4)
//                                .setDismissText("GOT IT")
//                                .setContentText("This contains all your schedules related to the University")
//                                .withCircleShape()
//                                .build()
//                );
//                sequence.addSequenceItem(
//                        new MaterialShowcaseView.Builder(this)
//                                .setTarget(t5)
//                                .setDismissText("GOT IT")
//                                .setContentText("This contains your academic performance")
//                                .withCircleShape()
//                                .build()
//                );
//
//
//                sequence.start();
//            }
//        }
//
//    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_home_dark)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}
