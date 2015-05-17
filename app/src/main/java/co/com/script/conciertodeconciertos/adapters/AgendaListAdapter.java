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
 * Created by User on 09/05/2015.
 */
public class AgendaListAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;
    private String[] mFirstHours;
    private String[] mSecHours;
    private String[] mIndicatives;

    public AgendaListAdapter(Context context, String[] mTitles, String[] mFirstHours, String[] mSecHours, String[] mIndicatives) {
        mContext = context;
        this.mTitles = mTitles;
        this.mFirstHours = mFirstHours;
        this.mSecHours = mSecHours;
        this.mIndicatives = mIndicatives;
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
        Typeface typeFaceTitle, typeFaceHourBox, typefaceHour;
        TextView labTitle, labHour, labMinute, labIndicative;
        View view;

        typeFaceTitle = Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.rc_light));
        typefaceHour = Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.rc_bold));
        typeFaceHourBox = Typeface.createFromAsset(mContext.getAssets(), mContext.getResources().getString(R.string.yanone_light));

          if (position % 2 != 0) {
              view = inflater.inflate(R.layout.row_agenda_izquierda_layout,
                      parent, false);
          }
          else {
              view = inflater.inflate(R.layout.row_agenda_derecha_layout,
                      parent, false);
          }

        labTitle = (TextView) view
                .findViewById(R.id.title_agenda);

        labHour = (TextView) view
                .findViewById(R.id.primera_hora);

        labMinute = (TextView) view
                .findViewById(R.id.segunda_hora);

        labIndicative = (TextView) view
                .findViewById(R.id.indicativo_hora);

        labTitle.setTypeface(typeFaceTitle);
        labHour.setTypeface(typefaceHour, Typeface.BOLD);
        labMinute.setTypeface(typeFaceTitle);
        labIndicative.setTypeface(typeFaceHourBox);

        labTitle.setText(mTitles[position]);
        labHour.setText(mFirstHours[position] + ":");
        labMinute.setText(mSecHours[position]);
        labIndicative.setText(mIndicatives[position]);

        return view;
    }
}
