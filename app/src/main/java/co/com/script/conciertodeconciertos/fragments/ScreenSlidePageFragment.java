package co.com.script.conciertodeconciertos.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.activity.SpotifyActivity;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.interfaces.ReproductorMethods;

/**
 * Created by User on 13/08/2015.
 */
public class ScreenSlidePageFragment extends Fragment implements ReproductorMethods {

    private static final String SONG_TITLE = "song";
    private static final String INDEX = "index";

    private String song;
    private int index;
    private AudioManager audioManager;

    public static ScreenSlidePageFragment newInstance(String song, int index) {

        // Instantiate a new fragment
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putString(SONG_TITLE, song);
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Load parameters when the initial creation of the fragment is done
        this.song = (getArguments() != null) ? getArguments().getString(
                SONG_TITLE) : "";
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.pager_layout, container, false);

        TextView tvIndex = (TextView) rootView.findViewById(R.id.textViewTituloAlbum);
        tvIndex.setText(this.song);

        rootView.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SpotifyActivity)getActivity()).openSong(ApplicationConstants.getCodeSogs()[index]);
            }
        });

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        DiscreteSeekBar dis = (DiscreteSeekBar) rootView.findViewById(R.id.seekbar);
        dis.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int progress, boolean b) {
                //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                  //      progress, 0);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

            }
        });


        return rootView;

    }

    @Override
    public void play(String url) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
