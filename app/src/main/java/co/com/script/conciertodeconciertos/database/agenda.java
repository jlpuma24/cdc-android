package co.com.script.conciertodeconciertos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 04/08/2015.
 */
public class agenda {

    public static final String TABLE_NAME = "agenda";
    public static final String TITULO = "titulo";
    public static final String HORA = "hora";
    public static final String MINUTO = "minuto";
    public static final String INDICATIVO = "indicativo";
    private ContentValues cv;

    public agenda(){

    }

    public void insert(SQLiteDatabase db, String titulo, String hora, String minuto, String indicativo){
        cv = new ContentValues();
        cv.put(TITULO, titulo);
        cv.put(HORA, hora);
        cv.put(MINUTO, minuto);
        cv.put(INDICATIVO, indicativo);
        db.insert(TABLE_NAME, "agenda", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from agenda", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }

}
