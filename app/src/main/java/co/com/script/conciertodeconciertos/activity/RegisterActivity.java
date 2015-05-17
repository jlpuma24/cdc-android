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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
 * Actividad para el registro regular de usuarios
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText inputMail, inputCel;
    private CheckBox checkTerms;
    private TextView linkTermns;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface type = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.rc_light));

        inputMail = (EditText) findViewById(R.id.input_mail);
        inputCel = (EditText) findViewById(R.id.input_cel);

        checkTerms = (CheckBox) findViewById(R.id.check_terms);

        linkTermns = (TextView) findViewById(R.id.link_terms);
        linkTermns.setOnClickListener(this);

        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(this);

        inputMail.setTypeface(type);
        inputCel.setTypeface(type);
        linkTermns.setTypeface(type);
        buttonRegister.setTypeface(type);
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

            case R.id.link_terms:
                startActivity(new Intent(RegisterActivity.this, TermsActivity.class));
                break;

            case R.id.button_register:
                boolean isCheckedTerms = checkTerms.isChecked();
                if (isCheckedTerms) {
                    sendRegisterData();
                } else {
                    Toast.makeText(this, getString(R.string.error_terms), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void sendRegisterData() {
        String mail = inputMail.getText().toString();
        String cel = inputCel.getText().toString();

        if (registerAttempt(mail, cel)) {

            String url = "http://54.200.219.177:9000/service/usuario/list/";
            JSONObject json = new JSONObject();
            try {
                json.put("nombre", mail);
                json.put("password", mail);
                json.put("correo", mail);
                json.put("telefono", cel);

                Log.i("JSON registro", json.toString());
                volleyPost(url, json);
            } catch (JSONException e) {
                Log.e("JSONException", e.toString());
            }
        }
    }

    /**
     * Comprobacion de datos al intentar registrarse    * @return
     */

    private boolean registerAttempt(String mail, String cel) {

        inputMail.setError(null);
        inputCel.setError(null);

        if (TextUtils.isEmpty(mail)) {
            inputMail.setError(getString(R.string.error_input_empty));
            inputMail.requestFocus();
            return false;

        } else if (isMailValid(mail)) {
            inputMail.setError(getString(R.string.error_mail_incorrect));
            inputMail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(cel)) {
            inputCel.setError(getString(R.string.error_input_empty));
            inputCel.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * validacion simple para comprobar si es un correo electronico
     *
     * @param mail
     * @return
     */
    private boolean isMailValid(String mail) {
        return !mail.contains("@");
    }

    /**
     * Solicitud de los servicios web a traves de un metodo http POST
     *
     * @param url
     * @param json
     */
    private void volleyPost(String url, JSONObject json) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        DialogHelper.getInstance(this).createLoadingDialog(progressDialog, getString(R.string.loader_registro));
        DialogHelper.getInstance(this).showDialog(progressDialog);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String userId = response.getString("id");
                    String mail = response.getString("correo");
                    String cel = response.getString("telefono");
                    UserSessionManager.login(RegisterActivity.this, userId, mail, cel);

                    DialogHelper.getInstance(RegisterActivity.this).showDialog(progressDialog);

                    Intent intent = new Intent(RegisterActivity.this, QuestionaryActivity.class);
                    intent.putExtra(QuestionaryActivity.KEY_QUESTIONARY_NUMBER, QuestionaryActivity.ONE);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                DialogHelper.getInstance(RegisterActivity.this).showDialog(progressDialog);

                if (error instanceof NoConnectionError) {
                    Toast.makeText(RegisterActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 400)
                        Toast.makeText(RegisterActivity.this, getString(R.string.error_existieng_user), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

}
