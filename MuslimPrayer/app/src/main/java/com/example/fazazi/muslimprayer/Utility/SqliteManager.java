package com.example.fazazi.muslimprayer.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.layali.codedreamer.muslim.Models.recitator.Recitator;

public class SqliteManager extends SQLiteOpenHelper {

private SQLiteDatabase Db ;
    public SqliteManager(Context context) {
        super(context, "Favorites", null, 1);
        Db = getWritableDatabase();
    }
    private String Name;
    private String Id;
    private String Url;
    private Integer recitationId;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Recitator(Name TEXT,Id TEXT,Url TEXT,recitationId TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FavoriteLogs");
        this.onCreate(db);
    }


    public  void addRecitator(Recitator Recitator){
        SQLiteDatabase Database  = getWritableDatabase();
        ContentValues Value = new ContentValues();


        Value.put("Name" ,Recitator.getName());
        Value.put("Id" , Recitator.getId());
        Value.put("Url" ,Recitator.getUrl());
        Value.put("recitationId" ,Recitator.getRecitationId());
        Database.insert("Recitator", null, Value);
        CloseDb(Database);
    }

    /* public List<Log> GetAllContacts() {

         List<Log> Logs = new ArrayList();


         String query = "SELECT * FROM FavoriteLogs";

         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.rawQuery("SELECT * FROM FavoriteLogs", null);


         Log Log = new Log();
         if (cursor.moveToFirst()) {

             while (cursor.isAfterLast() == false) {
                 Log.setPhoneNumber(cursor.getString(0));
                 Log.setDate(Date.valueOf(cursor.getString(1)));
                 Log.setDuration(cursor.getString(2));
                 Log.setType(cursor.getString(3));

                 Logs.add(Log);
                 cursor.moveToNext();
                 Log = new Log();
             }
         }
         CloseDb(db);
         return Logs;

     }



     public List<Favorite> getFavorites(){
             List<Favorite> Favorites = new ArrayList();


         String Query = "SELECT phoneNumber ,Date ,Duration ,Type  , COUNT(*) AS Total FROM FavoriteLogs" +
                 " GROUP BY phoneNumber LIMIT 10";
             Cursor cursor = Db.rawQuery(Query, null);

             Favorite Log  = new Favorite();
             if (cursor.moveToFirst()) {

                 while (cursor.isAfterLast() == false) {
                     Log.setPhoneNumber(cursor.getString(0));
                     Log.setDate(new Date(15,14,14));
                     Log.setDuration(cursor.getString(2));
                     Log.setType(cursor.getString(3));
                     Log.setCount(cursor.getInt(4));

                     Favorites.add(Log);
                     cursor.moveToNext();
                     Log = new Favorite();
                 }
             }
         CloseDb(Db);
             return Favorites;
         }
 */
    public int getSize(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from FavoriteLogs", null);
       // CloseDb(db);
        return c.getCount();

    }

    public void CloseDb(SQLiteDatabase db){
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
