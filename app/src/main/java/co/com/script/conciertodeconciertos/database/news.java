package co.com.script.conciertodeconciertos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 04/08/2015.
 */
public class news {

    public static final String TABLE_NAME = "news";
    public static final String TITULO = "titulo";
    public static final String DESCRIPCION = "descripcion";
    public static final String IMAGEN = "imagen";
    private ContentValues cv;

    public news(){

    }

    public void insert(SQLiteDatabase db, String titulo, String descripcion, String imagen){
        cv = new ContentValues();
        cv.put(TITULO, titulo);
        cv.put(DESCRIPCION, descripcion);
        cv.put(IMAGEN, imagen);
        db.insert(TABLE_NAME, "news", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from news", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }

}
