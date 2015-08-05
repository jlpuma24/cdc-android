package co.com.script.conciertodeconciertos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 12/05/2015.
 */
public class SQLiteDB extends SQLiteOpenHelper{

    public static final String NAMEDB = "cdc.bd";

    public SQLiteDB(Context context) {
        super(context, NAMEDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE artistas (id  INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT);");
        db.execSQL("CREATE TABLE news (id  INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT, imagen TEXT);");
        db.execSQL("CREATE TABLE agenda (id  INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, hora TEXT, minuto TEXT, indicativo TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS artistas");
        db.execSQL("DROP TABLE IF EXISTS news");
        db.execSQL("DROP TABLE IF EXISTS agenda");
        onCreate(db);
    }

    public void deleteDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS artistas");
        db.execSQL("DROP TABLE IF EXISTS news");
        db.execSQL("DROP TABLE IF EXISTS agenda");
        onCreate(db);
    }
}
