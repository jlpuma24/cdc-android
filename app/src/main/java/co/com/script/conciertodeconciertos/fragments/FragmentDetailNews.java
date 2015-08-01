package co.com.script.conciertodeconciertos.fragments;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;

/**
 * Created by User on 07/05/2015.
 */
public class FragmentDetailNews extends Fragment {

    public FragmentDetailNews(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), getActivity().getResources().getString(R.string.rc_light));

        TextView newsTitle = ((TextView) view.findViewById(R.id.newsTitle));

        newsTitle.setTypeface(type, Typeface.BOLD);
        newsTitle.setText(ApplicationConstants.getAnnouncments()[UserSessionManager.getPositionOfSelectedNews(getActivity())]);

        newsTitle.setPaintFlags(newsTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Picasso.with(getActivity())
                .load(ApplicationConstants.getPhotos()[UserSessionManager.getPositionOfSelectedNews(getActivity())])
                .into((ImageView) view.findViewById(R.id.imageViewIcon), new Callback() {
                    @Override
                    public void onSuccess() {
                        ((ProgressBar) view.findViewById(R.id.progressBarPhoto)).setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ((ProgressBar) view.findViewById(R.id.progressBarPhoto)).setVisibility(View.GONE);
                    }
                });

        return view;
    }
}
