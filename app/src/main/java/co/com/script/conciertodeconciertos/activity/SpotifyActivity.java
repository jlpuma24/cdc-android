package co.com.script.conciertodeconciertos.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.adapters.MyFragmentPagerAdapter;
import co.com.script.conciertodeconciertos.adapters.PlayListAdapter;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.fragments.ScreenSlidePageFragment;

/**
 * Created by User on 18/05/2015.
 */
public class SpotifyActivity extends FragmentActivity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "4e537ad179744dbeb6d9b6674777e17b";
    private static final String REDIRECT_URI = "http://google.com/callback";
    private static final int REQUEST_CODE = 1337;
    private Config playerConfig;
    private Player mPlayer;
    private ViewPager pager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface type = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.rc_light));

        ((TextView) findViewById(R.id.image_banner)).setTypeface(type, Typeface.BOLD);
        ((TextView) findViewById(R.id.textViewAnuncio)).setTypeface(type, Typeface.BOLD);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                ListView lv = (ListView) findViewById(R.id.listViewSongs);
                init();
                lv.setAdapter(new PlayListAdapter(SpotifyActivity.this, ApplicationConstants.getSongsTitles()));
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(SpotifyActivity.this, "clicked: "+String.valueOf(position),Toast.LENGTH_LONG).show();
                        openSong(ApplicationConstants.getCodeSogs()[position]);

                    }
                });
            }
        }
    }

    private void init(){

        pager = (ViewPager) this.findViewById(R.id.pager);

        // Create an adapter with the fragments we show on the ViewPager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(
                getSupportFragmentManager());

        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[0], 0));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[1], 1));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[2], 2));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[3], 3));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[4], 4));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[5], 5));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[6], 6));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[7], 7));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[8], 8));
        adapter.addFragment(ScreenSlidePageFragment.newInstance(ApplicationConstants.getSongsTitles()[9], 9));

        pager.setAdapter(adapter);

        CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.titles);

        titleIndicator.setViewPager(pager);
    }

    public void openSong(final String song){
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                mPlayer.addConnectionStateCallback(SpotifyActivity.this);
                mPlayer.addPlayerNotificationCallback(SpotifyActivity.this);
                mPlayer.play("spotify:track:"+song);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(SpotifyActivity.this, "Existio un error al momento de inicializar la cancion.", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });
    }

    public void stop(){
        mPlayer.pause();
    }


    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", error.getMessage());
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, PlayListActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

}