package co.com.script.conciertodeconciertos.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 09/08/2015.
 */
public class PlayListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;

    public PlayListAdapter(Context context, String[] mTitles) {
        mContext = context;
        this.mTitles = mTitles;
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

        Typeface type = Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.rc_bold));

        View view = inflater.inflate(R.layout.row_song,
                parent, false);

        TextView labTitle = (TextView) view
                .findViewById(R.id.drawerMenuItemName);

        labTitle.setTypeface(type);

        labTitle.setText(mTitles[position]);


        return view;
    }

}
