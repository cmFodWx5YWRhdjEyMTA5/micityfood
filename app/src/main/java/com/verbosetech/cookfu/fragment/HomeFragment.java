package com.verbosetech.cookfu.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.verbosetech.cookfu.DataAdapters.ResturantsByAddress;
import com.verbosetech.cookfu.Database.Database;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.activity.CartActivity;
import com.verbosetech.cookfu.adapter.FoodCategoryAdapter;
import com.verbosetech.cookfu.adapter.RestaurantAdapter;
import com.verbosetech.cookfu.util.Constants;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;
import com.verbosetech.cookfu.util.PermissionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements ConnectionCallbacks,
        OnConnectionFailedListener, OnRequestPermissionsResultCallback, PermissionUtils.PermissionResultCallback, View.OnClickListener {
    // LogCat tag
    private static final String TAG = HomeFragment.class.getSimpleName();
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    public TextView locationText;
    public String user_address, user_address1, state, city, country, postalCode;
    public ActivityOptions options = null;
    public RestaurantAdapter adapter;
    double latitude;

    // Google client to interact with Google API
    double longitude;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    // list of permissions
    boolean isPermissionGranted;
    private RecyclerView recyclerFood, recyclerRestaurants;
    private RelativeLayout locationContainer;
    private TextView cartNotificationCount;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private List<ResturantsByAddress> resturantList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = cartActionView.findViewById(R.id.notification_count);
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        setCartCount();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setCartCount() {
        int NOTIFICATION_COUNT = new Database(getActivity().getApplicationContext()).getProfilesCount();
        if (cartNotificationCount != null) {
            if (NOTIFICATION_COUNT <= 0) {
                cartNotificationCount.setVisibility(View.GONE);
            } else {
                cartNotificationCount.setVisibility(View.VISIBLE);
                cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Check if we're running on Android 5.0 or higher
        CutsomSharedPreferences.init(getActivity().getApplicationContext()); // Initializing Custom Shared Preference
        recyclerFood = view.findViewById(R.id.recyclerFood);
        recyclerRestaurants = view.findViewById(R.id.recyclerRestaurants);
        locationText = view.findViewById(R.id.locationText);
        locationContainer = view.findViewById(R.id.locationContainer);
        resturantList = new ArrayList<>();
        adapter = new RestaurantAdapter(getActivity().getApplicationContext(), resturantList);
        recyclerRestaurants.setAdapter(adapter);
        locationContainer.setOnClickListener(this);
        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        setUpSharedPreferece();
        return view;
    }

    private void setUpSharedPreferece() {
        CutsomSharedPreferences.init(getActivity().getApplicationContext()); // Initializing Custom Shared Preference
        try {
            String lat = CutsomSharedPreferences.Read(CutsomSharedPreferences.LAT, null).trim();
            String lon = CutsomSharedPreferences.Read(CutsomSharedPreferences.LONG, null).trim();
            user_address = CutsomSharedPreferences.Read(CutsomSharedPreferences.USER_ADDRESS, null).trim();
            state = CutsomSharedPreferences.Read(CutsomSharedPreferences.USER_STATE, null).trim();
            city = CutsomSharedPreferences.Read(CutsomSharedPreferences.USER_CITY, null).trim();
            country = CutsomSharedPreferences.Read(CutsomSharedPreferences.USER_COUNTRY, null).trim();
            postalCode = CutsomSharedPreferences.Read(CutsomSharedPreferences.USER_PIN, null).trim();
            Log.d("Lat", lat);
            Log.d("Long", lon);
            if (lat.equals("0.0") && lon.equals("0.0")) {
                StartLocationService();
            } else {
                locationText.setText(user_address);
                getResturantsByAdd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to display the location on UI
     */

    private void getLocation() {

        if (isPermissionGranted) {

            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);

                Log.d("Setting Last Location", String.valueOf(mLastLocation));
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        } else {

        }

    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            String currentLocation;
            locationText.setText(address);
        }
    }

    /**
     * Creating google api client object
     */

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity().getApplicationContext());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
        setCartCount();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerFood();
        setupRecyclerRestaurants();
    }

    private void setupRecyclerRestaurants() {
        recyclerRestaurants.setNestedScrollingEnabled(false);
        recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupRecyclerFood() {
        recyclerFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFood.setAdapter(new FoodCategoryAdapter(getContext()));
    }

    public void getResturantsByAdd() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.d(TAG, " Start of Adapter");
        String testURL ="http://micityfood.com/mobileapp/api/search?callback=jQuery1113028314006927994306_1536234365897&address=Model%20Town%20Rd,%20Model%20Town%20Phase%202,%20Model%20Town,%20Bathinda,%20Punjab%20151001,%20India&search_mode=address&page=0&lang_id=en&lang=en&api_key=0313dfad2d6c8675839f38c8009a40a1&app_version=2.5&_=1536234365902";
        String addressUrl = Constants.GET_RESTURANTS_DETAILS_BY_ADDRESS + user_address + "a&search_mode=address&page=0&lang_id=en&lang=en&api_key=0313dfad2d6c8675839f38c8009a40a1&app_version=2.5&_=1536234365902";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, testURL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String respNew = response.substring(response.indexOf("(") + 1);
                        respNew.trim();
                        try {

                            JSONObject obj = new JSONObject(respNew);
                            Log.d(TAG, String.valueOf(obj));

                            Log.d("code", obj.getString("code"));
                            Log.d("msg", obj.getString("msg"));
                            JSONObject details = obj.getJSONObject("details");
                            Log.d("total", details.getString("total"));
                            String total = details.getString("total");
                            JSONArray rest_data = details.getJSONArray("data");
                            for (int i = 0; i < rest_data.length(); i++) {

                                //getting the json object of the particular index inside the array
                                JSONObject dataObject = rest_data.getJSONObject(i);

                                ResturantsByAddress resturantsByAddress = new ResturantsByAddress();
                                resturantsByAddress.setMerchant_id(dataObject.getString("merchant_id"));
                                resturantsByAddress.setRestaurant_name(dataObject.getString("restaurant_name"));
                                resturantsByAddress.setCuisine(dataObject.getString("cuisine"));
                                resturantsByAddress.setRest_logo(dataObject.getString("logo"));
                                resturantsByAddress.setMinimum_order(dataObject.getString("minimum_order"));
                                resturantsByAddress.setDelivery_est(dataObject.getString("delivery_est"));
                                resturantsByAddress.setDelivery_fee(dataObject.getString("delivery_fee"));

                                Log.d("Image " + i, dataObject.getString("logo"));
                                Log.d("Res Name " + i, dataObject.getString("restaurant_name"));
                                Log.d("Cuisine " + i, dataObject.getString("cuisine"));
                                Log.d("Delivery Fee " + i, dataObject.getString("delivery_fee"));
                                resturantList.add(resturantsByAddress);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                // hide the progress dialog
            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    //TODO Onclick listener
    @Override
    public void onClick(View view) {
        if (view == locationContainer) {
            StartLocationService();
        }

    }

    private void StartLocationService() {
        getLocation();
        Log.d("Last Location", String.valueOf(mLastLocation));
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            getAddress();

            getResturantsByAdd();

        } else {
            Toast.makeText(getActivity(), "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
        }
    }
}
