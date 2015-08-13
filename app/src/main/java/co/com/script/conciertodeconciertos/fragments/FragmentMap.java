package co.com.script.conciertodeconciertos.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 10/08/2015.
 */
public class FragmentMap extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_mapa,
                container, false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getResources().getString(R.string.rc_light));

        ((TextView) view.findViewById(R.id.image_banner)).setTypeface(type,Typeface.BOLD);

        return view;
    }
}
