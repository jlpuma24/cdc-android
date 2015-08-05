package co.com.script.conciertodeconciertos.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.adapters.AgendaListAdapter;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.helpers.DatabaseHelper;

/**
 * Created by User on 09/05/2015.
 */
public class FragmentAgenda extends Fragment {

    public FragmentAgenda(){}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_agenda,
                container, false);

        /*((ListView) view.findViewById(R.id.listViewAgenda)).setAdapter(new AgendaListAdapter(getActivity(),
                ApplicationConstants.getAgendaTitles(),ApplicationConstants.getFirstHourTitles(),
                ApplicationConstants.getSecondHourTitles(),ApplicationConstants.getIndicativeTitles()));*/

        ((ListView) view.findViewById(R.id.listViewAgenda)).setAdapter(new AgendaListAdapter(getActivity(),
                DatabaseHelper.getInstance(getActivity()).getAgendatitles(),
                DatabaseHelper.getInstance(getActivity()).getAgendaHours(),
                DatabaseHelper.getInstance(getActivity()).getAgendaMinutes(),
                DatabaseHelper.getInstance(getActivity()).getAgendaIndicatives()));

        return view;

    }
}
