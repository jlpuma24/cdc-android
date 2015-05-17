package co.com.script.conciertodeconciertos.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.VolleySingleton;
import co.com.script.conciertodeconciertos.helpers.DialogHelper;


public class UserSessionActivity extends Activity implements View.OnClickListener {
    private CallbackManager callBack;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_session);

        Typeface type = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.rc_light));

        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);

        ((Button) findViewById(R.id.button_login)).setTypeface(type);
        ((Button) findViewById(R.id.button_register)).setTypeface(type);
        ((Button) findViewById(R.id.button_facebook)).setTypeface(type);

        facebookSettings();
    }


    private void facebookSettings() {
        //facebook callback
        callBack = CallbackManager.Factory.create();

        manager = LoginManager.getInstance();
        manager.registerCallback(callBack, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                facebookRequest(accessToken);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

        findViewById(R.id.button_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }

    private void facebookLogin() {
        //permisos de facebook
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        manager.logInWithReadPermissions(UserSessionActivity.this, permissions);
    }

    private void facebookRequest(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonResponse, GraphResponse response) {
                        try {
                            String mail = jsonResponse.getString("email");
                            attemptLogin(mail);
                        } catch (JSONException e) {

                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.checkbox_terms, null);

        view.findViewById(R.id.link_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserSessionActivity.this, TermsActivity.class));
            }
        });

        builder.setView(view);

        builder.setPositiveButton(getString(R.string.text_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                facebookLogin();
            }
        });

        builder.setNegativeButton(getString(R.string.text_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    /**
     * Solicitud de los servicios web a traves de un metodo http POST
     *
     * @param url
     * @param json
     */
    private void volleyPost(String url, JSONObject json) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        DialogHelper.getInstance(this).createLoadingDialog(progressDialog, getString(R.string.loader_login));
        DialogHelper.getInstance(this).showDialog(progressDialog);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String userId = response.getString("id");
                    String mail = response.getString("correo");
                    String cel = response.getString("telefono");
                    UserSessionManager.login(UserSessionActivity.this, userId, mail, cel);

                    DialogHelper.getInstance(UserSessionActivity.this).stopDialog(progressDialog);

                    Intent intent = new Intent(UserSessionActivity.this, QuestionaryActivity.class);
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

                DialogHelper.getInstance(UserSessionActivity.this).stopDialog(progressDialog);

                if (error instanceof NoConnectionError) {
                    Toast.makeText(UserSessionActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 400)
                        Toast.makeText(UserSessionActivity.this, getString(R.string.error_existieng_user), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(UserSessionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
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
                    UserSessionManager.login(UserSessionActivity.this, userId, mail, cel);

                    DialogHelper.getInstance(UserSessionActivity.this).stopDialog(progressDialog);

                    startActivity(new Intent(UserSessionActivity.this, NewsActivity.class));
                    finish();

                } catch (JSONException e) {
                    DialogHelper.getInstance(UserSessionActivity.this).stopDialog(progressDialog);
                    Log.e("JSONException", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogHelper.getInstance(UserSessionActivity.this).stopDialog(progressDialog);
                if (error instanceof NoConnectionError) {
                    Toast.makeText(UserSessionActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 404)
                        Toast.makeText(UserSessionActivity.this, getString(R.string.error_nouser), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(UserSessionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void attemptLogin(String mail) {
        String url = "http://54.200.219.177:9000/service/usuario/detail/" + mail + "/" + mail + "/";
        volleyGET(url);
    }

    private void attemptRegister(JSONObject jsonResponse) {
        String url = "http://54.200.219.177:9000/service/usuario/list/";
        JSONObject json = new JSONObject();
        try {
            String mail = jsonResponse.getString("email");

            json.put("nombre", mail);
            json.put("password", mail);
            json.put("correo", mail);
            json.put("telefono", "asdf");

            Log.i("JSON registro", json.toString());
            volleyPost(url, json);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                /*startActivity(new Intent(this, LoginActivity.class));*/
                startActivityForResult(new Intent(this, LoginActivity.class), 1);
                break;

            case R.id.button_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBack.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 1://login result
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, NewsActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
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
}
