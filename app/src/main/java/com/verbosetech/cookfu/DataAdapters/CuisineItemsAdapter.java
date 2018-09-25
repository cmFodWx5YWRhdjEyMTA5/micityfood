package com.verbosetech.cookfu.DataAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.verbosetech.cookfu.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CuisineItemsAdapter {
    private static String TAG = "";
    private List<CuisineItems> cuisineItemsList;


    public void sendingArrayReqest(String marchantID, String catID, final Context mContext) {
        cuisineItemsList = new ArrayList<>();
        TAG = mContext.getClass().getSimpleName();
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
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
                                if (msg.equals("Ok")) {
                                    JSONObject details = obj.getJSONObject("details");
                                    Log.d("show_cat_image", details.getString("show_cat_image"));
//                                    cuisineItems.setIndex(details.getString("disabled_ordering"));
//                                    cuisineItems.setMobile_menu(details.getString("mobile_menu"));
                                    JSONArray rest_data = details.getJSONArray("item");
                                    for (int i = 0; i < rest_data.length(); i++) {
                                        CuisineItems cuisineItems = new CuisineItems();
                                        //getting the json object of the particular index inside the array
                                        JSONObject dataObject = rest_data.getJSONObject(i);
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
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext.getApplicationContext(),
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
                Toast.makeText(mContext.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                // hide the progress dialog
            }
        });
        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}