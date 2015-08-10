package co.com.script.conciertodeconciertos.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import java.util.List;

import co.com.script.conciertodeconciertos.CDCApplication;
import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 09/08/2015.
 */
public class DeezerActivity extends Activity {

    private DeezerConnect deezerConnect = null;
    private String applicationID = "162635";
    private AlbumPlayer albumPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer);

        Typeface type = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.rc_light));

        ((TextView) findViewById(R.id.image_banner)).setTypeface(type, Typeface.BOLD);
        ((TextView) findViewById(R.id.textViewAnuncioAlbum)).setTypeface(type, Typeface.BOLD);
        ((TextView) findViewById(R.id.textViewTituloAlbum)).setTypeface(type, Typeface.BOLD);


        deezerConnect = new DeezerConnect(this, applicationID);
        String[] permissions = new String[] {
                Permissions.BASIC_ACCESS,
                Permissions.MANAGE_LIBRARY,
                Permissions.LISTENING_HISTORY };

        DialogListener listener = new DialogListener() {

            public void onComplete(Bundle values) {

                albumPlayer = null;

                try {
                    albumPlayer = new AlbumPlayer(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
                    long albumId = 8619748;
                    albumPlayer.playAlbum(albumId);
                } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
                    tooManyPlayersExceptions.printStackTrace();
                } catch (DeezerError deezerError) {
                    deezerError.printStackTrace();
                }

            }

            public void onCancel() {
                startActivity(new Intent(DeezerActivity.this, PlayListActivity.class));
                finish();
            }

            public void onException(Exception e) {
                Toast.makeText(DeezerActivity.this, "Existio un error al momento de realizar la autenticacion",Toast.LENGTH_LONG).show();
            }
        };

        deezerConnect.authorize(this, permissions, listener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        albumPlayer.stop();
        albumPlayer.release();
        startActivity(new Intent(this, PlayListActivity.class));
        finish();
    }
}
