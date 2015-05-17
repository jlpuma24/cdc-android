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
}
