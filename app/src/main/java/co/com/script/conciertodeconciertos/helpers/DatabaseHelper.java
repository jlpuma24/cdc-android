package co.com.script.conciertodeconciertos.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.database.SQLiteDB;
import co.com.script.conciertodeconciertos.database.artistas;

/**
 * Created by User on 12/05/2015.
 */
public class DatabaseHelper {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteDB database;
    private artistas artistasTable;
    private static DatabaseHelper mInstance;
    private Context mContext;
    private ArrayList<String> artistasList;
    private Cursor cursor;
    private String[] artistArr;

    private DatabaseHelper(Context mContext) {
        this.mContext = mContext;
        database = new SQLiteDB(mContext);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }

    private void makeDatabaseOperation(DatabaseOperation operation){
        database = new SQLiteDB(mContext);
        sqLiteDatabase = (operation == DatabaseOperation.INSERT || operation == DatabaseOperation.DELETE)
                         ? database.getWritableDatabase() : database.getReadableDatabase();
    }

    public void cleanArtistDatabase(){
        makeDatabaseOperation(DatabaseOperation.DELETE);
        artistasTable = new artistas();
        artistasTable.delete(sqLiteDatabase);
    }

    public void insertArtista(String nombre){
        makeDatabaseOperation(DatabaseOperation.INSERT);
        artistasTable = new artistas();
        artistasTable.insert(sqLiteDatabase, nombre);
    }

    public String[] getListArtist(){
        makeDatabaseOperation(DatabaseOperation.SELECT);
        artistasTable = new artistas();
        cursor = artistasTable.consulta(sqLiteDatabase);
        artistasList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                artistasList.add(cursor.getString(cursor.getColumnIndex(
                        mContext.getResources().getString(R.string.json_tag_nombre))));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        artistArr = new String[artistasList.size()];
        artistArr = artistasList.toArray(artistArr);

        return artistArr;
    }

}
