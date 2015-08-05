package co.com.script.conciertodeconciertos.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.VolleySingleton;
import co.com.script.conciertodeconciertos.helpers.DatabaseHelper;
import co.com.script.conciertodeconciertos.helpers.DialogHelper;
import co.com.script.conciertodeconciertos.helpers.NetworkHelper;

public class QuestionaryActivity extends Activity implements View.OnClickListener {
    public static final String KEY_QUESTIONARY_NUMBER = "numberQuestionary";
    public static final String ONE = "uno";
    public static final String TWOO = "dos";
    public static final String FIRST_ANSWER = "firstAnswer";
    private ProgressDialog progressDialog;
    private String questionarySelector;
    private int firstAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);
        Bundle extras = getIntent().getExtras();

    try{
        questionarySelector = extras.getString(KEY_QUESTIONARY_NUMBER);
    }catch (NullPointerException e){
        questionarySelector = ONE;
    }

        String question;
        String[] answers;
        progressDialog = new ProgressDialog(this);

        if (questionarySelector.equals(ONE)) {

            DialogHelper.getInstance(this).createLoadingDialog(progressDialog, getString(R.string.loader_descarga));
            DialogHelper.getInstance(this).showDialog(progressDialog);
            NetworkHelper.getArtistList(this);
            DialogHelper.getInstance(this).stopDialog(progressDialog);

            question = getString(R.string.pregunta_uno);
            answers = getResources().getStringArray(R.array.respuestas_uno);
        } else {

            firstAnswer = extras.getInt(FIRST_ANSWER);
            question = getString(R.string.pregunta_dos);
            answers = DatabaseHelper.getInstance(this).getListArtist();

        }

        Typeface type = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.rc_light));
        ((TextView) findViewById(R.id.text_title_questionary)).setText(question);
        ((TextView) findViewById(R.id.text_title_questionary)).setTypeface(type);
        ((Button)findViewById(R.id.button_send_answer)).setTypeface(type);
        createRadioButton(answers);
        findViewById(R.id.button_send_answer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send_answer:

                int indexAnswer = getAnswer();
                Log.i("Actual answer", String.valueOf(indexAnswer));

                if (indexAnswer > 0) {
                    if (questionarySelector.equals(ONE)) {
                        Intent intent = new Intent(QuestionaryActivity.this, QuestionaryActivity.class);
                        intent.putExtra(KEY_QUESTIONARY_NUMBER, TWOO);
                        intent.putExtra(FIRST_ANSWER, indexAnswer);
                        startActivity(intent);
                        finish();
                    } else {
                        sendData(firstAnswer, indexAnswer);
                    }
                } else {
                    Toast.makeText(this, getString(R.string.error_no_answer), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Crea el numero de radio buttons por respuestas entregadas en el string array
     *
     * @param answers
     */
    private void createRadioButton(String[] answers) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        RadioButton[] radioButtons = new RadioButton[answers.length];
        Typeface type = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.rc_light));

        for (int i = 0; i < radioButtons.length; i++) {
            radioButtons[i] = new RadioButton(this);
            //TODO - Hardcoded space.
            radioButtons[i].setText("  "+answers[i]);
            radioButtons[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            radioButtons[i].setPadding(10, 10, 10, 10);
            radioButtons[i].setTypeface(type);
            radioButtons[i].setButtonDrawable(R.drawable.radio_selector);
            radioButtons[i].setTextColor(getResources().getColor(android.R.color.white));
            radioGroup.addView(radioButtons[i]);
        }
    }

    private int getAnswer() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        return radioGroup.indexOfChild(radioButton) + 1;
    }

    private void sendData(int position, int artist) {
        String url = "http://54.200.219.177:9000/service/perfil/list/";
        String userId = UserSessionManager.getUserData(QuestionaryActivity.this,
                UserSessionManager.KEY_USER_ID);
        try {
            JSONArray artists = new JSONArray();
            artists.put(3);

            JSONObject json = new JSONObject();
            json.put("usuario", userId);
            json.put("ubicacion", position);
            json.put("artistas", artists);

            Log.i("JSON sended", json.toString());
            volleyPost(url, json);

        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
    }

    /**
     * Solicitud de los servicios web a traves de un metodo POST
     *
     * @param url
     * @param json
     */
    private void volleyPost(String url, JSONObject json) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(QuestionaryActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(QuestionaryActivity.this, NewsActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);

                if (error instanceof NoConnectionError) {
                    Toast.makeText(QuestionaryActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(QuestionaryActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(QuestionaryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(QuestionaryActivity.this,
                            getString(R.string.error_no_connection), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(QuestionaryActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void showProgress(boolean show) {
        //findViewById(R.id.progress_register).setVisibility(show ? View.VISIBLE : View.GONE);
        //inputMail.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
    }
}
