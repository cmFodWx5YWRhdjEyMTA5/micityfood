package com.verbosetech.cookfu.DataAdapters;

public class ResturantParse {

    public static String[] merchant_id;
    public static String[] restaurant_name;
    public static String[] rest_address;
    //    public static String[] ratings;
//    public static String[] votes;
    public static String[] cuisine;
    public static String[] delivery_fee;
    public static String[] minimum_order;
    public static String[] delivery_est;
    public static String[] is_open;
    public static String[] tag_raw;
    //    public static String[] cod;
//    public static String[] online;
    public static String[] rest_logo;
    //    public static String[] offers;
    public static String[] service;
    //    public static String[] rest_delivery;
//    public static String[] pickup;
//    public static String[] dinein;
    public static String[] distance;
    public static String[] delivery_estimation;
    public static String[] delivery_distance;
    public static String[] is_sponsored;
//    public static String[] payment_available;


//    public JSONArray resturants = null;
//    List<ResturantsByAddress> resturantsByAddresses;
//    private String json;
//
//    public ResturantParse(String json) {
//        this.json = json;
//    }
//
//    protected void resturantParse(){
//        JSONObject jsonObject=null;
//
//        try {
//
//            resturants = new JSONArray(json);
//            merchant_id = new String[resturants.length()];
//            restaurant_name = new String[resturants.length()];
//            resturantsByAddresses = new ArrayList<ResturantsByAddress>();
//
//            for(int i=0;i< resturants.length();i++){
//                resturants resturants_object =  new ResturantsByAddress();
//
//                JSONObject jsonObject = resturants.getJSONObject(i);
//
//                merchant_id[i] = jsonObject.getString("merchant_id");
//                restaurant_name[i] = jsonObject.getString("restaurant_name");
//
//                resturants_object.setMerchant_id(merchant_id[i]);
//                resturants_object.setUrl(restaurant_name[i]);
//                resturantsByAddresses.add(resturants_object);
//
//
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//    List<resturants> getresturantsByAddresses()
//    {
//        //function to return the final populated list
//        return resturantsByAddresses;
//    }

}
