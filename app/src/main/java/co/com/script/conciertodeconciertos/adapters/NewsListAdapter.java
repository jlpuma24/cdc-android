package co.com.script.conciertodeconciertos.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 06/05/2015.
 */
public class NewsListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;
    private String[] mPictures;

    public NewsListAdapter(Context context, String[] mTitles, String[] mPictures) {
        mContext = context;
        this.mTitles = mTitles;
        this.mPictures = mPictures;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return mTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        Typeface type = Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.rc_light));

        View view = inflater.inflate(R.layout.row_news_layout,
                parent, false);

        TextView labTitle = (TextView) view
            .findViewById(R.id.newsTitle);

        ImageView imgLogo = (ImageView) view
            .findViewById(R.id.imageViewIcon);

        TextView smallDescription = (TextView) view
            .findViewById(R.id.small_detail);

        final ProgressBar progressBarPhoto = (ProgressBar) view
                .findViewById(R.id.progressBarPhoto);

        labTitle.setTypeface(type, Typeface.BOLD);
        smallDescription.setTypeface(type);
        //TODO harcoded bullet icon, move to string after test.
        labTitle.setText("\u2022  " + mTitles[position]);

        Picasso.with(mContext)
                .load(mPictures[position])
                .into(imgLogo, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBarPhoto.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBarPhoto.setVisibility(View.GONE);
                    }
                });

        return view;
   }
}
