package com.verbosetech.cookfu.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.fragment.AuthFragment;
import com.verbosetech.cookfu.interactor.AuthMainInteractor;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;
import com.verbosetech.cookfu.util.GPSTracker;
import com.verbosetech.cookfu.util.LocationHelper;
import com.verbosetech.cookfu.util.PermissionUtils;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity implements ConnectionCallbacks,
        OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback {
    //LOCATION SERVICE
    // LogCat tag
    private static final String TAG = SplashActivity.class.getSimpleName();
    private final String FRAG_TAG_SIGN_IN_UP = "SIGN_IN";
    public Location mLastLocation;
    // GPSTracker class
    GPSTracker gps;
    double latitude;
    double longitude;
    LocationHelper locationHelper;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    boolean isPermissionGranted;
    private TextView splashMessage;
    private ImageView splashLogo;
    // list of permissions
    private CardView cardView;
    private FrameLayout frameLayout;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // set an exit transition
            getWindow().setExitTransition(new Explode());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        CutsomSharedPreferences.init(this); // Initializing Custom Shared Preference
        locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();

        splashLogo = findViewById(R.id.splashLogo);
        splashMessage = findViewById(R.id.splashMessage);
        cardView = findViewById(R.id.cardView);
        frameLayout = findViewById(R.id.frameLayout);
        ImageView background = findViewById(R.id.background);

        Glide.with(this).load(R.drawable.chef_logo).into(splashLogo);
        Glide.with(this).load(R.drawable.background).into(background);
        setupAuthLayout();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endSplash();
            }
        }, 1800);
        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }

    }

    private void getCurrentLocation() {
        mLastLocation = locationHelper.getLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            getAddress();

        } else {
        }
    }


    public void getAddress() {
        Address locationAddress;

        locationAddress = locationHelper.getAddress(latitude, longitude);

        if (locationAddress != null) {

            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();
            Log.d("Address", address);

            saveSharedPreferece(address, city, state, country, postalCode, latitude, longitude);
        } else
            showToast("Something went wrong");
    }

    private void saveSharedPreferece(String address, String city, String state, String country, String postalCode, double latitude, double longitude) {
        CutsomSharedPreferences.Write(CutsomSharedPreferences.USER_ADDRESS, address);
        CutsomSharedPreferences.Write(CutsomSharedPreferences.USER_CITY, city);
        CutsomSharedPreferences.Write(CutsomSharedPreferences.USER_STATE, state);
        CutsomSharedPreferences.Write(CutsomSharedPreferences.USER_COUNTRY, country);
        CutsomSharedPreferences.Write(CutsomSharedPreferences.USER_PIN, postalCode);
        CutsomSharedPreferences.Write(CutsomSharedPreferences.LAT, String.valueOf(latitude));
        CutsomSharedPreferences.Write(CutsomSharedPreferences.LONG, String.valueOf(longitude));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        locationHelper.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationHelper.checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }


    // Permission check functions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void setupAuthLayout() {
        mHandler = new Handler();
        loadFragment(FRAG_TAG_SIGN_IN_UP);
    }

    private void loadFragment(final String fragTag) {

        Fragment fragment = null;
        switch (fragTag) {
            case FRAG_TAG_SIGN_IN_UP:
                fragment = AuthFragment.newInstance(new AuthMainInteractor() {
                    @Override
                    public void moveToMain() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent,
                                    ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
                            Log.d("Mode Started in", "With Animation");
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            Log.d("Mode Started in", "Without Animation");
                        }
                        finish();
                    }
                });
                break;
        }
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        final Fragment finalFragment = fragment;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.splashFrame, finalFragment, fragTag);
                fragmentTransaction.commit();
            }
        };

        mHandler.post(mPendingRunnable);

    }

    private void endSplash() {
        splashLogo.animate().translationY(-1 * getResources().getDimension(R.dimen.splashLogoMarginTop)).setDuration(600).start();
        AlphaAnimation animation1 = new AlphaAnimation(1.0f, 0.0f);
        animation1.setDuration(600);
        animation1.setFillAfter(true);
        AlphaAnimation animation2 = new AlphaAnimation(0.0f, 1.0f);
        animation2.setDuration(600);
        animation2.setFillAfter(true);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup.LayoutParams params = frameLayout.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                frameLayout.setLayoutParams(params);
                splashMessage.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                getCurrentLocation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashMessage.startAnimation(animation1);
        cardView.startAnimation(animation2);
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


}
