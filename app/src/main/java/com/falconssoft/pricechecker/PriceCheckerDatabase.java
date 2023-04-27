package com.falconssoft.pricechecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class PriceCheckerDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =24;//version Db
    private static final String DATABASE_Name = "PriceCheckerDBase";//name Db

    static SQLiteDatabase Idb;


    //___________________________________________________________________________________
    private static final String MAIN_SETTING_TABLE = "MAIN_SETTING_TABLE";

    private static final String IP_SETTING = "IP_SETTING";
    private static final String STORNO = "STORNO";
    private static final String IS_ASSEST = "IS_ASSEST";
    private static final String IS_QRJARD = "IS_QRJARD";
    private static final String Currency = "Currency";

    //_________________________________________________________________________________

    public PriceCheckerDatabase(Context context) {
        super(context, DATABASE_Name, null, DATABASE_VERSION);
    }
    //__________________________________________________________________________________


    @Override
    public void onCreate(SQLiteDatabase Idb) {
//=========================================================================================
        String CREATE_TABLE_MAIN_SETTING = "CREATE TABLE " + MAIN_SETTING_TABLE + "("
                + IP_SETTING + " NVARCHAR   ,"
                + STORNO + " NVARCHAR   ,"
                + IS_ASSEST + " NVARCHAR   ,"
                + IS_QRJARD + " NVARCHAR ,"
                + Currency + " Text "

                + ")";
        Idb.execSQL(CREATE_TABLE_MAIN_SETTING);
//=========================================================================================

    }

    @Override
    public void onUpgrade(SQLiteDatabase Idb, int oldVersion, int newVersion) {

       try {
            Idb.execSQL("ALTER TABLE MAIN_SETTING_TABLE ADD " + Currency + " Text"+" DEFAULT '0'");
       }catch (Exception e){
           Log.e("upgrade", "Currency,MAIN_SETTING_TABLE IS_ASSEST");
       }
//        try {
//            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + ORG_SALEPRICE + " TEXT"+" DEFAULT '0'");
//            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + OLD_SALE_PRICE + " TEXT"+" DEFAULT '0'");
//            Idb.execSQL("ALTER TABLE ITEM_UNITS ADD " + UPDATE_DATE + " TEXT"+" DEFAULT '0'");
//        }catch (Exception e){
//            Log.e("upgrade", "MAIN_SETTING_TABLE IS_ASSEST");
//        }


    }


    public void addAllMainSetting(String ip,String co,String Curr) {
        Idb = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(IP_SETTING, convertToEnglish( ip));
        values.put(STORNO, convertToEnglish(co ));
        values.put(Currency, convertToEnglish(Curr ));

        Idb.insert(MAIN_SETTING_TABLE, null, values);
        Idb.close();
    }



    public String getAllMainSetting() {
       String ipAddress = "";

        String selectQuery = "SELECT  * FROM " + MAIN_SETTING_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ipAddress=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return ipAddress;
    }

    public String getCurrencySetting() {
        String Currency = "";

        String selectQuery = "SELECT  Currency FROM " + MAIN_SETTING_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Currency=cursor.getString(0);

            } while (cursor.moveToNext());
        }
        return Currency;
    }

    public String getAllMainSetting_cono() {
        String ipAddress = "";

        String selectQuery = "SELECT  * FROM " + MAIN_SETTING_TABLE;
        Idb = this.getWritableDatabase();
        Cursor cursor = Idb.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ipAddress=cursor.getString(1);

            } while (cursor.moveToNext());
        }
        return ipAddress;
    }
   public void deleteAllSetting()
   {
//             Idb.execSQL("DELETE FROM "+tableName); //delete all rows in a table
       SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("delete from MAIN_SETTING_TABLE ");
       db.close();
         }









    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫","."));
        return newValue;
    }
}
