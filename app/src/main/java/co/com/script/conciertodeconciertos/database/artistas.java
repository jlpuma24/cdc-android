package co.com.script.conciertodeconciertos.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 12/05/2015.
 */
public class artistas {

    public static final String TABLE_NAME = "artistas";
    public static final String NOMBRE = "nombre";
    private ContentValues cv;

    public artistas(){

    }

    public void insert(SQLiteDatabase db, String nombre){
        cv = new ContentValues();
        cv.put(NOMBRE, nombre);
        db.insert(TABLE_NAME, "artistas", cv);
    }

    public Cursor consulta(SQLiteDatabase db){
        return db.rawQuery("select * from artistas", null);
    }

    public void delete(SQLiteDatabase db){ db.delete(TABLE_NAME,null,null); }
}
