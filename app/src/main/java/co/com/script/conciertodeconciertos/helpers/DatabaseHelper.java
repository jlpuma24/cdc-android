package co.com.script.conciertodeconciertos.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.database.SQLiteDB;
import co.com.script.conciertodeconciertos.database.agenda;
import co.com.script.conciertodeconciertos.database.artistas;
import co.com.script.conciertodeconciertos.database.news;

/**
 * Created by User on 12/05/2015.
 */
public class DatabaseHelper {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteDB database;
    private artistas artistasTable;
    private news newsTable;
    private agenda agendaTable;
    private static DatabaseHelper mInstance;
    private Context mContext;
    private ArrayList<String> artistasList;
    private ArrayList<String> newsTitleList;
    private ArrayList<String> newsImagesList;
    private ArrayList<String> agendaTitlesList;
    private ArrayList<String> agendaMinutesList;
    private ArrayList<String> agendaHourList;
    private ArrayList<String> agendaIndicativesList;
    private Cursor cursor;
    private String[] artistArr;
    private String[] newsTitleArr;
    private String[] newsImagesArr;
    private String[] agendaTitlesArr;
    private String[] agendaHoursArr;
    private String[] agendaMinutesArr;
    private String[] agendaIndicativesArr;

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

    public void cleanNewsDatabase(){
        makeDatabaseOperation(DatabaseOperation.DELETE);
        newsTable = new news();
        newsTable.delete(sqLiteDatabase);
    }

    public void cleanAgendaDatabase(){
        makeDatabaseOperation(DatabaseOperation.DELETE);
        agendaTable = new agenda();
        agendaTable.delete(sqLiteDatabase);
    }

    public void insertArtista(String nombre){
        makeDatabaseOperation(DatabaseOperation.INSERT);
        artistasTable = new artistas();
        artistasTable.insert(sqLiteDatabase, nombre);
    }

    public void insertNoticia(String titulo, String descripcion, String imagen){
        makeDatabaseOperation(DatabaseOperation.INSERT);
        newsTable = new news();
        newsTable.insert(sqLiteDatabase, titulo, descripcion, imagen);
    }

    public void insertAgenda(String titulo, String hora, String minuto, String indicativo){
        makeDatabaseOperation(DatabaseOperation.INSERT);
        agendaTable = new agenda();
        agendaTable.insert(sqLiteDatabase, titulo, hora, minuto, indicativo);
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

    public String[] getNewsTitleList(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        newsTable = new news();
        cursor = newsTable.consulta(sqLiteDatabase);
        newsTitleList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                newsTitleList.add(cursor.getString(cursor.getColumnIndex(
                        "titulo")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        newsTitleArr = new String[newsTitleList.size()];
        newsTitleArr = newsTitleList.toArray(newsTitleArr);

        return newsTitleArr;
    }

    public String[] getNewsImageList(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        newsTable = new news();
        cursor = newsTable.consulta(sqLiteDatabase);
        newsImagesList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                newsImagesList.add(cursor.getString(cursor.getColumnIndex(
                        "imagen")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        newsImagesArr = new String[newsImagesList.size()];
        newsImagesArr = newsImagesList.toArray(newsImagesArr);

        return newsImagesArr;
    }

    public String[] getAgendatitles(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        agendaTable = new agenda();
        cursor = agendaTable.consulta(sqLiteDatabase);
        agendaTitlesList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                agendaTitlesList.add(cursor.getString(cursor.getColumnIndex(
                        "titulo")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        agendaTitlesArr = new String[agendaTitlesList.size()];
        agendaTitlesArr = agendaTitlesList.toArray(agendaTitlesArr);

        return agendaTitlesArr;
    }

    public String[] getAgendaHours(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        agendaTable = new agenda();
        cursor = agendaTable.consulta(sqLiteDatabase);
        agendaHourList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                agendaHourList.add(cursor.getString(cursor.getColumnIndex(
                        "hora")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        agendaHoursArr = new String[agendaHourList.size()];
        agendaHoursArr = agendaHourList.toArray(agendaHoursArr);

        return agendaHoursArr;
    }

    public String[] getAgendaMinutes(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        agendaTable = new agenda();
        cursor = agendaTable.consulta(sqLiteDatabase);
        agendaMinutesList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                agendaMinutesList.add(cursor.getString(cursor.getColumnIndex(
                        "minuto")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        agendaMinutesArr = new String[agendaMinutesList.size()];
        agendaMinutesArr = agendaMinutesList.toArray(agendaMinutesArr);

        return agendaMinutesArr;
    }

    public String[] getAgendaIndicatives(){

        makeDatabaseOperation(DatabaseOperation.SELECT);
        agendaTable = new agenda();
        cursor = agendaTable.consulta(sqLiteDatabase);
        agendaIndicativesList = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                agendaIndicativesList.add(cursor.getString(cursor.getColumnIndex(
                        "indicativo")));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        agendaIndicativesArr = new String[agendaIndicativesList.size()];
        agendaIndicativesArr = agendaIndicativesList.toArray(agendaIndicativesArr);

        return agendaIndicativesArr;
    }

}
