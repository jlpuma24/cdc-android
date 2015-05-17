package co.com.script.conciertodeconciertos.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.VolleySingleton;
import co.com.script.conciertodeconciertos.helpers.DialogHelper;

/**
 * Activity para el login regular de usuarios
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    /**
     * Campo de texto para el email del usuario
     */
    private EditText inputMail;
    /**
     * Boton de inicio de sesion
     */
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface type = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.rc_light));

        inputMail = (EditText) findViewById(R.id.input_mail);
        buttonLogin = (Button) findViewById(R.id.button_login);

        inputMail.setTypeface(type);
        buttonLogin.setTypeface(type);

        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:

                String mail = inputMail.getText().toString();
                if (loginAttempt(mail)) {
                    String url = "http://54.200.219.177:9000/service/usuario/detail/" + mail + "/" + mail + "/";
                    volleyGET(url);
                }
                break;
        }
    }

    /**
     * Solicitud de los servicios web a traves de un metodo http GET
     *
     * @param url direccion url del servicio web
     */
    private void volleyGET(String url) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        DialogHelper.getInstance(this).createLoadingDialog(progressDialog, getString(R.string.loader_login));
        DialogHelper.getInstance(this).showDialog(progressDialog);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String userId = response.getString("id");
                    String mail = response.getString("correo");
                    String cel = response.getString("telefono");
                    DialogHelper.getInstance(LoginActivity.this).stopDialog(progressDialog);
                    UserSessionManager.login(LoginActivity.this, userId, mail, cel);
                    setResult(RESULT_OK);
                    finish();

                } catch (JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                DialogHelper.getInstance(LoginActivity.this).stopDialog(progressDialog);

                if (error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 404)
                        Toast.makeText(LoginActivity.this, getString(R.string.error_nouser), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    /**
     * Comprobacion de datos al intentar iniciar sesion     * @return
     */
    private boolean loginAttempt(String mail) {
        boolean isOk = true;
        inputMail.setError(null);

        if (TextUtils.isEmpty(mail)) {
            inputMail.setError(getString(R.string.error_input_empty));
            inputMail.requestFocus();
            isOk = false;
        } else if (isMailValid(mail)) {
            inputMail.setError(getString(R.string.error_mail_incorrect));
            inputMail.requestFocus();
            isOk = false;
        }

        return isOk;
    }

    /**
     * validacion simple para comprobar un correo electronico
     *
     * @param mail
     * @return
     */
    private boolean isMailValid(String mail) {
        return !mail.contains("@");
    }

}
