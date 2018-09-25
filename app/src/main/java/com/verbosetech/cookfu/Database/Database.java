package com.verbosetech.cookfu.Database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.verbosetech.cookfu.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    public static final String DB_NAME = "Food_DB.db";
    public static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<CartItem> getCart() {
    SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Name","Price","Photo","Quantity"};
        String sqlTable = "OrderList";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null,null, null, null, null);

        final List<CartItem> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new CartItem(
                        c.getString(c.getColumnIndex("Name")),
                        c.getInt(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Photo")),
                        c.getInt(c.getColumnIndex("Quantity"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }
    public void addToCart(CartItem cartItem){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT into OrderList(Name, Price, Photo, Quantity) VALUES('%s','%s','%s','%s');",
        cartItem.getName(),
                cartItem.getPrice(),
                cartItem.getImageRes(),
                cartItem.getQuantity());
        db.execSQL(query);
    }
    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE from OrderList");
        db.execSQL(query);
    }
    public boolean isDbEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"Name","Price","Photo","Quantity"};
        String sqlTable = "OrderList";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null,null, null, null, null);
        if (c != null && c.moveToFirst()) {
            c.close();
            return  false;
        } else {
            return true;
        }
    }
    public int getProfilesCount() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"Name","Price","Photo","Quantity"};
        String sqlTable = "OrderList";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null,null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }
}
