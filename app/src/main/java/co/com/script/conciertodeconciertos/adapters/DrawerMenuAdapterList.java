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
 * Created by User on 08/01/2015.
 */

public class DrawerMenuAdapterList extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;
    private int mImages[];

    public DrawerMenuAdapterList(Context context, String[] mTitles, int mImages[]) {
        mContext = context;
        this.mTitles = mTitles;
        this.mImages = mImages;
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

        View view = inflater.inflate(R.layout.navigation_drawer_list_item,
                parent, false);

        TextView labTitle = (TextView) view
                .findViewById(R.id.drawerMenuItemName);

        ImageView imgLogo = (ImageView) view
                .findViewById(R.id.drawerMenuItemImage);

        labTitle.setTypeface(type);

        labTitle.setText(mTitles[position]);

        imgLogo.setBackgroundResource(mImages[position]);

        return view;
    }

}
