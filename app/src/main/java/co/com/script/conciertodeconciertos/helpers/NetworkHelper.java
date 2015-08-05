package co.com.script.conciertodeconciertos.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.VolleySingleton;

/**
 * Created by User on 16/05/2015.
 */
public class NetworkHelper {

    private static JsonArrayRequest getRequest;

    public static void getNewsList(final Context mContext){

        getRequest = new JsonArrayRequest(mContext.getResources().getString(R.string.url_news),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {

                        DatabaseHelper.getInstance(mContext).cleanNewsDatabase();

                        for (int i = 0; i < response.length(); i++){

                            try {

                                JSONObject jo = response.getJSONObject(i);
                                DatabaseHelper.getInstance(mContext).insertNoticia(
                                        jo.getString("titulo"), jo.getString("detalle"), "http://54.200.219.177:9000/" + jo.getString("foto"));

                            } catch (JSONException e){
                                Toast.makeText(mContext,mContext.getString(R.string.error_parseo),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,mContext.getString(R.string.error_descarga),Toast.LENGTH_LONG).show();
                    }
                }
        );

        makeVolleyCall(mContext);

    }

    public static void getAgendaList(final Context mContext){

        getRequest = new JsonArrayRequest(mContext.getResources().getString(R.string.url_concierto),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {

                        DatabaseHelper.getInstance(mContext).cleanAgendaDatabase();

                        for (int i = 0; i < response.length(); i++){

                            try {

                                JSONObject jo = response.getJSONObject(i);

                                Log.i("jo", jo.toString());

                                JSONArray ja = jo.getJSONArray("agendas");

                                  for (int j = 0; j < ja.length(); j++){

                                      JSONObject objectAgenda = ja.getJSONObject(j);

                                      String[] date = objectAgenda.getString("fecha").split(":");

                                      String indicador = (Integer.parseInt(date[2]) > 13) ? "PM" : "AM";
                                      String hora = (Integer.parseInt(date[2]) < 13) ? date[2] : modifyHour(Integer.parseInt(date[2])) ;
                                      String minuto = date[3];

                                        DatabaseHelper.getInstance(mContext).insertAgenda(objectAgenda.getString("titulo"),hora,minuto,indicador);

                                  }

                            } catch (JSONException e){
                                Toast.makeText(mContext,mContext.getString(R.string.error_parseo),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,mContext.getString(R.string.error_descarga),Toast.LENGTH_LONG).show();
                    }
                }
        );

        makeVolleyCall(mContext);

    }


    public static void getArtistList(final Context mContext){

         getRequest = new JsonArrayRequest(mContext.getResources().getString(R.string.url_artistas),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        DatabaseHelper.getInstance(mContext).cleanArtistDatabase();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jo = response.getJSONObject(i);
                                DatabaseHelper.getInstance(mContext).insertArtista(
                                        jo.getString(mContext.getResources().getString(R.string.json_tag_nombre)));
                            } catch (JSONException e){
                                Toast.makeText(mContext,mContext.getString(R.string.error_parseo),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,mContext.getString(R.string.error_descarga),Toast.LENGTH_LONG).show();
                    }
                }
        );

        makeVolleyCall(mContext);

    }

    private static void makeVolleyCall(Context mContext){
        VolleySingleton.getInstance(mContext).addToRequestQueue(getRequest);
    }

    private static String modifyHour(int hour){
        switch (hour){
            case 13: return "01";
            case 14: return "02";
            case 15: return "03";
            case 16: return "04";
            case 17: return "05";
            case 18: return "06";
            case 19: return "07";
            case 20: return "08";
            case 21: return "09";
            case 22: return "10";
            case 23: return "11";
            case 24: return "12";
        }
        return "";
    }
}
