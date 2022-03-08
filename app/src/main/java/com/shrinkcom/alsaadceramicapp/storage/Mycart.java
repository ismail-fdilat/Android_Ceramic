package com.shrinkcom.alsaadceramicapp.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mycart {

    public final static String id_ = "id";
    public final static String user_id = "user_id";
    public final static String name = "name";
    public final static String image = "image";
    public final static String day = "day";
    public final static String min = "min";
    public final static String max = "max";
    public final static String desp = "desp";
    public final static String price = "price";
    public final static String idouto_ = "autoidid";
    public final static String productid = "pid";
    public final static String productname = "pname";
    public final static String productimage = "pimage";
    public final static String productprice = "pprice";
    public final static String productquantity = "pquantity";
    public final static String productdesp = "ppdesp";
    public final static String productcolor = "pcolor";
    public final static String productsize = "psize";
    public final static String p_size_name = "p_size_name";
    public final static String productsizeid = "psizeid";
    public final static String sellerId = "sellerId";
    public final static String city = "city";
    public final static String mainQty = "mainQty";


    final static String DATABASE_NAME = "AlSaadHomeAPP.DB";
    final static String Cart_Table = "CART_TABLE";
    final static int DATABASE_VERSION = 2;
    final static String Cart_product = "PRODUCT_TABLE";



    final static String DATABASE_CREATE1 = "create table " + Cart_product + "(" + idouto_ + " integer primary key autoincrement,"
            + productid + " text," + productname + " text," + productimage + " text," + productprice + " text," + productquantity + " text," + productdesp + " text," + productcolor + " text,"+ productsize + " text,"+ p_size_name + " text,"+ user_id + " text," + sellerId + " text,"+ city + " text," + mainQty + " text)";


    final static String DATABASE_CREATE = "create table " + Cart_Table + "(" + id_ + " integer primary key autoincrement,"
            + user_id + " integer," + name + " text," + image + " text," + day + " text," + min + " text," + max + " text," + price + " text," + desp + " text);";

    private static final String TAG = "Mycart";
    final DatabaseHelper dbHelper;
    final Context context;
    SQLiteDatabase db;


    // TODO: 3/24/2020  create another table

    public final static String idtav_ = "id";
    public final static String userapp_id = "user_id";
    final static String ID_Table = "ID_TABLE";

    final static String TABLEID = "create table " + ID_Table + "(" + idtav_ + " integer primary key autoincrement,"
            + userapp_id + " integer);";


    public Mycart(Context con) {
        this.context = con;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 INSERT DATA CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//


    public long insertData(String user_id, String name, String image, String day, String min, String max, String desp, String price) {
        ContentValues values = new ContentValues();
        values.put(Mycart.user_id, user_id);
        values.put(Mycart.name, name);
        values.put(Mycart.image, image);
        values.put(Mycart.day, day);
        values.put(Mycart.min, min);
        values.put(Mycart.max, max);
        values.put(Mycart.desp, desp);
        values.put(Mycart.price, price);
        return db.insert(Cart_Table, null, values);

    }


    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 DELETE DATA CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//

    public void deleteSchedule(String scheduleid) {

        db.execSQL("delete from " + Cart_Table + " where user_id='" + scheduleid + "'");
        // db.close();
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 DELETE ALL CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//


    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 GET ALL CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//

    public Cursor getAllData() {
        return db.query(true, Cart_Table, new String[]{Mycart.id_, Mycart.user_id, Mycart.name, Mycart.image,
                Mycart.day, Mycart.min, Mycart.max, Mycart.price, Mycart.desp
        }, null, null, null, null, Mycart.id_ + " DESC", null);
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 GET WISH LIST  CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//

    public Cursor getWishListData() {
        Cursor cursor = null;
        String sql = "SELECT * FROM " + Cart_Table + " ORDER BY " + Mycart.id_ + " ASC ";

        cursor = db.rawQuery(sql, null);
        return cursor;
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 CHECK AVAILABLITY   CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//


    public int checkAvailable(String product_id,String strize,String userid) {
        Cursor cursor = null;
        String sql = "SELECT * FROM " + Cart_product + " WHERE " + Mycart.productid + "='" + product_id +"'";
       Log.e("SIZEREEEE",">>>"+sql);
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();
            return 1;
        } else {
            //PID Not Found
            cursor.close();
            return 0;
        }
    }


    // TODO: 3/18/2020  check available using userid






    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 DELETE ALL   CART TABLE
    //-------------------------------------------------//---------------------------------------------------------//

    public void deletealldata(String userid) {

        db.execSQL("delete from " + Cart_product);

       // db.execSQL("delete from " + Cart_Table + " where MyCart.user_id='" + scheduleid + "'");
       // db.execSQL("delete from " + Cart_product);
    }


    //_________________________________________________________________________________________________________________//



    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 INSERT DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//

    public long insertDatashopping(String productid, String productname, String productimage, String productprice, String productquantity, String despcription, String psize, String p_size_name , String userid,String sellerId ,String City,String mainQty) {
      Log.e("SIZEEEE",">>>"+psize);
      Log.e("SIZEEEE",">>>"+p_size_name);
      ContentValues values = new ContentValues();

        values.put(Mycart.productid, productid);
        values.put(Mycart.productname, productname);
        values.put(Mycart.productimage, productimage);
        values.put(Mycart.productprice, productprice);
        values.put(Mycart.productquantity, productquantity);
        values.put(Mycart.productdesp, despcription);
        values.put(Mycart.productsize, psize);
        values.put(Mycart.p_size_name, p_size_name);
        values.put(Mycart.user_id, userid);
        values.put(Mycart.sellerId, sellerId);
        values.put(Mycart.city, City);
        values.put(Mycart.mainQty, mainQty);
//        Log.d("cityent",City);


        return db.insert(Cart_product, null, values);
    }


    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 UPDATE DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//


    public void updateQuantity(String productid, String quantity) {
        ContentValues cv = new ContentValues();
        cv.put(Mycart.productquantity, quantity);
        db.update(Cart_product, cv, Mycart.productid + "=" + productid, null);
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 GETALL DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//


    public Cursor
    getAllDatashopping(String userid) {

        Cursor c =  db.rawQuery("SELECT * FROM " + Cart_product ,null);

        //Cursor c = db.rawQuery("SELECT * FROM Cart_product WHERE Mycart.user_id = '" + userid + "'", null);


        /* return db.query(true, Cart_product, new String[]{Mycart.idouto_, Mycart.productid, Mycart.productname, Mycart.productimage,
                Mycart.productprice, Mycart.productquantity, Mycart.productdesp, Mycart.productcolor, Mycart.productsize
        }, null, null, null, null, Mycart.idouto_ + " ASC", null);
    }*/

       return c;
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 DELETE ALL DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//


    public void deleteAllDatashopping() {
        // SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Cart_product, null, null);
        db.close();
    }


    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 CHEKAVAILABLITY DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//

    public int checkAvailableshopping(String product_id) {
        Cursor cursor = null;
        String sql = "SELECT * FROM " + Cart_product + " WHERE " + Mycart.productid + "='" + product_id + "'";
        cursor = db.rawQuery(sql, null);


        if (cursor.getCount() > 0) {
            //PID Found
            cursor.close();
            return 1;
        } else {
            //PID Not Found
            cursor.close();
            return 0;
        }
    }

    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 DELETE  DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//

    public boolean deleteproduct(String product_id) {
        return db.delete(Cart_product, Mycart.productid + "='" + product_id + "'", null) > 0;
    }


    //-------------------------------------------------//---------------------------------------------------------//
    // TODO 18-02-2020 COUNT  DATA CART PRODUCT
    //-------------------------------------------------//---------------------------------------------------------//

    public long countproduct() {

        long count = DatabaseUtils.queryNumEntries(db, Cart_product);

        return count;
    }


    // TODO: 3/24/2020  gat iand insert data



    public long insertDataUNIQUEID( String user_id) {
        ContentValues values = new ContentValues();
        values.put(Mycart.userapp_id, user_id);

        return db.insert(ID_Table, null, values);

    }

    public Cursor getAllDataUNIWUEID() {

        Cursor c =  db.rawQuery("SELECT * FROM " + ID_Table ,null);
        return c;

    }





    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            try {
                database.execSQL(DATABASE_CREATE);
                database.execSQL(DATABASE_CREATE1);
                database.execSQL(TABLEID);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);


            onCreate(db);
        }
    }


}

