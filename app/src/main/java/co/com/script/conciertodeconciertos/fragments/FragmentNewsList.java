package co.com.script.conciertodeconciertos.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.activity.DetailNewActivity;
import co.com.script.conciertodeconciertos.adapters.NewsListAdapter;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.helpers.DatabaseHelper;

/**
 * Created by User on 06/05/2015.
 */
public class FragmentNewsList extends Fragment {

    public FragmentNewsList(){}

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_news_list,
                container, false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getResources().getString(R.string.rc_light));

        ((TextView) view.findViewById(R.id.image_banner)).setTypeface(type,Typeface.BOLD);

        ((ListView) view.findViewById(R.id.listViewNews)).setAdapter(new NewsListAdapter(getActivity(),
                DatabaseHelper.getInstance(getActivity()).getNewsTitleList(), DatabaseHelper.getInstance(getActivity()).getNewsImageList()));

        ((ListView) view.findViewById(R.id.listViewNews)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserSessionManager.setSelectedNews(getActivity(),String.valueOf(position));
                startActivity(new Intent(getActivity(), DetailNewActivity.class));
                getActivity().finish();
            }
        });

        return view;

    }

}