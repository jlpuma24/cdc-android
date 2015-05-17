package co.com.script.conciertodeconciertos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Maneja y almacena la informacion del usuario
 * Created by Script on 1/05/15.
 */
public class UserSessionManager {
    public static final String PREFER_NAME = "UserSession";

    public static final String KEY_USER_ID = "userId";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_CEL = "cel";
    public static final String KEY_POSITION = "position";
    public static final String KEY_IS_LOGGED = "isUserLogged";

    /**
     * Guarda la informacion del usuario una vez se ha iniciado sesion
     * @param context
     * @param userId
     * @param mail
     * @param cel
     */
    public static void login(Context context, String userId, String mail, String cel) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //guardar informacion del usuario
        editor.putBoolean(KEY_IS_LOGGED, true);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_MAIL, mail);
        editor.putString(KEY_CEL, cel);
        editor.apply();
    }

    /**
     * Borra la informacion del usuario una vez ha cerrado sesion
     * @param context
     */
    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //guardar informacion del usuario
        editor.putBoolean(KEY_IS_LOGGED, false);
        editor.apply();
    }

    /**
     * retona un boolean que indica si el usuario esta logeado o no
     * @param context
     * @return
     */
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_IS_LOGGED, false);
    }

    /**
     * Retorna un dato del usuario segun el key que se le entregue
     * @param context
     * @param key
     * @return
     */
    public static String getUserData(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static void setSelectedNews(Context context, String position) {
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_POSITION, position);
        editor.apply();
    }

    public static int getPositionOfSelectedNews(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        return Integer.parseInt(preferences.getString(KEY_POSITION, "0"));
    }
}
