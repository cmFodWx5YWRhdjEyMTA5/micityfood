package com.verbosetech.cookfu.rest_detail;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.verbosetech.cookfu.DataAdapters.CuisineItems;
import com.verbosetech.cookfu.DataAdapters.ResturantCatagory;
import com.verbosetech.cookfu.R;
import com.verbosetech.cookfu.adapter.CuisineAdapter;
import com.verbosetech.cookfu.util.Constants;
import com.verbosetech.cookfu.util.CutsomSharedPreferences;
import com.verbosetech.cookfu.util.MyLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CuisineFragment extends Fragment {
    private static String TAG = "";
    private RecyclerView cuisineRecycler;
    private MyLinearLayoutManager linearLayoutManager;
    private String marchantID;
    private CuisineAdapter cuisineAdapter;
    private List<ResturantCatagory> resturantCatagories;
    private List<CuisineItems> cuisineItemsList;

    public CuisineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuisine, container, false);

        CutsomSharedPreferences.init(getActivity().getApplicationContext()); // Initializing Custom Shared Preference
        cuisineRecycler = view.findViewById(R.id.cuisineRecycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GettingResturantCatagory();
    }

    public void GettingResturantCatagory() {
        try {
            resturantCatagories = new ArrayList<>();
            marchantID = CutsomSharedPreferences.Read(CutsomSharedPreferences.MARCHANT_ID, null);
            Log.d("Marchant ID", marchantID);
            TAG = getActivity().getClass().getSimpleName();
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Cuisine Loading...");
            progressDialog.show();
            Log.d(TAG, " Start of Cuisine Adapter");
            String addressUrl = Constants.GETTING_RESTAURANT_CATEGORY + marchantID + "lang_id=en&lang=en&api_key=0313dfad2d6c8675839f38c8009a40a1&app_version=2.5&_=1536234365904";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, addressUrl,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            String respNew = response.substring(response.indexOf("(") + 1);
                            respNew.trim();
                            Log.d("Resturant Response", respNew);
                            try {
                                JSONObject obj = new JSONObject(respNew);
                                Log.d(TAG, String.valueOf(obj));
                                String msg = obj.getString("msg");
                                String code = obj.getString("code");

                                Log.d("Resturant code", code);
                                Log.d("Resturant msg", msg);
                                JSONObject details = obj.getJSONObject("details");
                                JSONArray rest_data = details.getJSONArray("data");
                                for (int i = 0; i < rest_data.length(); i++) {
                                    ResturantCatagory resCatagory = new ResturantCatagory();
                                    JSONObject dataObject = rest_data.getJSONObject(i);
                                    resCatagory.setCat_id(dataObject.getString("cat_id"));
                                    resCatagory.setCat_merchant_id(dataObject.getString("merchant_id"));
                                    resCatagory.setCategory_name(dataObject.getString("category_name"));
                                    Log.d("Cuisine Category", dataObject.getString("category_name"));
                                    resCatagory.setCategory_description(dataObject.getString("category_description"));
                                    resCatagory.setPhoto(dataObject.getString("photo"));
                                    resCatagory.setStatus(dataObject.getString("status"));
                                    resCatagory.setSequence(dataObject.getString("sequence"));
                                    resCatagory.setDate_created(dataObject.getString("date_created"));
                                    resCatagory.setDate_modified(dataObject.getString("date_modified"));
                                    resCatagory.setIp_address(dataObject.getString("ip_address"));
                                    resCatagory.setSpicydish(dataObject.getString("spicydish"));
                                    resCatagory.setSpicydish_notes(dataObject.getString("spicydish_notes"));
                                    resCatagory.setDish(dataObject.getString("dish"));
                                    resCatagory.setCategory_name_trans(dataObject.getString("category_name_trans"));
                                    resCatagory.setCategory_description_trans(dataObject.getString("category_description_trans"));
                                    resCatagory.setParent_cat_id(dataObject.getString("parent_cat_id"));
                                    resCatagory.setMonday(dataObject.getString("monday"));
                                    resCatagory.setTuesday(dataObject.getString("tuesday"));
                                    resCatagory.setWednesday(dataObject.getString("wednesday"));
                                    resCatagory.setThursday(dataObject.getString("thursday"));
                                    resCatagory.setFriday(dataObject.getString("friday"));
                                    resCatagory.setSaturday(dataObject.getString("saturday"));
                                    resCatagory.setSunday(dataObject.getString("sunday"));
                                    resCatagory.setPhoto_url(dataObject.getString("photo_url"));
                                    resCatagory.setDish_list(dataObject.getString("dish_list"));
                                    resturantCatagories.add(resCatagory);
                                    sendingItemRequest(dataObject.getString("merchant_id"), dataObject.getString("cat_id"));
                                }
                                setupCuisineRecycler();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                            }
//                        adapter.notifyDataSetChanged();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendingItemRequest(final String marchantID, final String catID) {
        cuisineItemsList = new ArrayList<>();
        TAG = getActivity().getClass().getSimpleName();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cuisine Loading...");
        progressDialog.show();
        Log.d(TAG, " Start of Cuisine Adapter");
        String addressUrl = Constants.GET_ITEM + marchantID + "&cat_id=" + catID + "&lang_id=en&lang=en&api_key=0313dfad2d6c8675839f38c8009a40a1&app_version=2.5&_=1536234365908";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, addressUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        String respNew = response.substring(response.indexOf("(") + 1);
                        respNew.trim();
                        Log.d("Cuisine Response", respNew);
                        try {
                            JSONObject obj = new JSONObject(respNew);
                            Log.d(TAG, String.valueOf(obj));
                            String msg = obj.getString("msg");
                            String code = obj.getString("code");
                            Log.d("Item code", code);
                            Log.d("Item msg", msg);
                            try {
                                if (msg.equals("OK")) {
                                    Log.d("MSG", "is Ok");
                                    JSONObject details = obj.getJSONObject("details");
                                    JSONArray rest_data = details.getJSONArray("item");
                                    for (int i = 0; i < rest_data.length(); i++) {
                                        CuisineItems cuisineItems = new CuisineItems();
                                        //getting the json object of the particular index inside the array
                                        JSONObject dataObject = rest_data.getJSONObject(i);
                                        cuisineItems.setCat_id(catID);
                                        cuisineItems.setMerchant_id(marchantID);;
                                        cuisineItems.setItem_id(dataObject.getString("item_id"));
                                        cuisineItems.setItem_name(dataObject.getString("item_name"));
                                        cuisineItems.setItem_description(dataObject.getString("item_description"));
                                        cuisineItems.setDiscount(dataObject.getString("discount"));
                                        cuisineItems.setPhoto(dataObject.getString("photo"));
                                        cuisineItems.setSpicydish(dataObject.getString("spicydish"));
                                        cuisineItems.setSingle_item(dataObject.getString("single_item"));
                                        cuisineItems.setSingle_details(dataObject.getString("single_details"));
                                        cuisineItems.setNot_available(dataObject.getString("not_available"));
                                        cuisineItems.setIcon_dish(dataObject.getString("icon_dish"));
                                        cuisineItems.setSpicydish(dataObject.getString("spicydish"));
                                        JSONArray price_details = dataObject.getJSONArray("prices");
                                        for (int j = 0; j < price_details.length(); j++) {
                                            JSONObject priceDetails = price_details.getJSONObject(i);
                                            cuisineItems.setPrice(priceDetails.getString("price"));
                                            cuisineItems.setSize(priceDetails.getString("size"));
                                            cuisineItems.setSize_id(priceDetails.getString("size_id"));
                                            cuisineItems.setSize_trans(priceDetails.getString("size_trans"));
                                            cuisineItems.setFormatted_price(priceDetails.getString("formatted_price"));
                                            cuisineItems.setDiscount_price(priceDetails.getString("discount_price"));
                                            cuisineItems.setFormatted_discount_price(priceDetails.getString("formatted_discount_price"));
                                            cuisineItems.setPrice_pretty(priceDetails.getString("price_pretty"));
                                        }
                                        cuisineItemsList.add(cuisineItems);
                                        try{Log.d("Cuisine Items" , String.valueOf(cuisineItemsList));}catch(Exception e){e.printStackTrace();}

                                    }
                                }else
                                {

                                    Log.d("MSG", "not Ok");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                            progressDialog.dismiss();
                        }
//                        adapter.notifyDataSetChanged();
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

    private void setupCuisineRecycler() {
        linearLayoutManager = new MyLinearLayoutManager(getContext());
        cuisineRecycler.setLayoutManager(linearLayoutManager);
        Log.d("Resturant Array", String.valueOf(resturantCatagories));
        cuisineRecycler.setAdapter(new CuisineAdapter(getContext(), cuisineItemsList, resturantCatagories, new CuisineAdapter.CuisineListToggleListener() {
            @Override
            public void OnListExpanded(final boolean selected) {
                if (selected) {
                    cuisineRecycler.scrollToPosition(0);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutManager.setScrollEnabled(!selected);
                    }
                }, 500);
            }
        }));
    }
}
