package co.com.script.conciertodeconciertos.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import co.com.script.conciertodeconciertos.R;

/**
 * Created by User on 18/05/2015.
 */
public class Example extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example);
        ((ListView) findViewById(R.id.listView)).setAdapter(new Adapter(this, new String[]{"1", "2", "3", "4", "5", "6", "7"}));
    }

    public class Adapter extends BaseAdapter {

        private Context mContext;
        private String[] mTitles;

        public Adapter(Context context, String[] mTitles) {
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

            View view = inflater.inflate(R.layout.row_example,
                    parent, false);

            final SwipeLayout swipeLayout =  (SwipeLayout) view.findViewById(R.id.swipe);
            final EditText editText = (EditText) view.findViewById(R.id.et);

            swipeLayout.setClickToClose(true);
            swipeLayout.setMotionEventSplittingEnabled(true);


            ((Button) view.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeLayout.open();
                }
            });

            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {

                @Override
                public void onStartOpen(SwipeLayout swipeLayout) {
                    //TODO - is not here, remove this log code
                    Log.i("on-start-opened", "here");
                }

                @Override
                public void onOpen(SwipeLayout swipeLayout) {
                    //TODO - is not here, remove this log code
                    Log.i("on-open", "here");
                }

                @Override
                public void onStartClose(SwipeLayout swipeLayout) {
                    Log.i("on start close", String.valueOf((editText.getText().toString().length() == 0)));
                    if (editText.getText().toString().length() == 0)
                        swipeLayout.open();
                }

                @Override
                public void onClose(SwipeLayout swipeLayout) {
                    Log.i("onClose", String.valueOf((editText.getText().toString().length() == 0)));
                    if (editText.getText().toString().length() == 0)
                        swipeLayout.open();
                }

                @Override
                public void onUpdate(SwipeLayout swipeLayout, int i, int i2) {
                    Log.i("onUpdate", String.valueOf((editText.getText().toString().length() == 0)));
                }

                @Override
                public void onHandRelease(SwipeLayout swipeLayout, float v, float v2) {
                    Log.i("onHandRelaese", String.valueOf((editText.getText().toString().length() == 0)));
                }

            });

            return view;
        }
    }
}
