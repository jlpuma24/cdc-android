package co.com.script.conciertodeconciertos.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.activity.DeezerActivity;
import co.com.script.conciertodeconciertos.activity.DetailNewActivity;
import co.com.script.conciertodeconciertos.activity.SpotifyActivity;
import co.com.script.conciertodeconciertos.adapters.NewsListAdapter;
import co.com.script.conciertodeconciertos.helpers.DatabaseHelper;

/**
 * Created by User on 09/08/2015.
 */
public class FragmentPlaylist extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_select_playlist,
                container, false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getResources().getString(R.string.rc_light));

        ((TextView) view.findViewById(R.id.image_banner)).setTypeface(type,Typeface.BOLD);
        ((TextView) view.findViewById(R.id.textViewAnuncio)).setTypeface(type, Typeface.BOLD);

        ((LinearLayout) view.findViewById(R.id.layoutDeezer)).setClickable(true);
        ((LinearLayout) view.findViewById(R.id.layoutSpotify)).setClickable(true);

        ((LinearLayout) view.findViewById(R.id.layoutSpotify)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SpotifyActivity.class));
                ((Activity) getActivity()).finish();
            }
        });

        ((LinearLayout) view.findViewById(R.id.layoutDeezer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), DeezerActivity.class));
                ((Activity) getActivity()).finish();
            }
        });

        return view;

    }
}
