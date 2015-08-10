package co.com.script.conciertodeconciertos.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.adapters.DrawerMenuAdapterList;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.fragments.FragmentNewsList;
import co.com.script.conciertodeconciertos.interfaces.BaseActivityMethodsInterface;

/**
 * Created by User on 06/05/2015.
 */
public class NewsActivity extends FragmentActivity implements BaseActivityMethodsInterface {

    private DrawerLayout mDrawerLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        buildMenu((ListView) findViewById(R.id.listViewMenu));
        makeTransaction(new FragmentNewsList(),getSupportFragmentManager(),R.id.content_frame);

        ((ListView) findViewById(R.id.listViewMenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 2: startActivity(new Intent(getApplicationContext(),AgendaActivity.class));
                            finish();
                            break;

                    case 3: startActivity(new Intent(getApplicationContext(),PlayListActivity.class));
                        finish();
                        break;

                    case 4: startActivity(new Intent(getApplicationContext(),ColorMomentActivity.class));
                        finish();
                        break;

                    case 5: createDialog();
                            break;
                }

            }
        });

    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.text_close_session));
        builder.setPositiveButton(getResources().getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserSessionManager.logout(getApplicationContext());
                startActivity(new Intent(getApplicationContext(),UserSessionActivity.class));
                finish();
            }
        });

        builder.setNegativeButton(getResources().getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public void openDrawerMenu(View v){
        mDrawerLayout.openDrawer((ListView) findViewById(R.id.listViewMenu));
    }

    @Override
    public void buildMenu(ListView listView) {
        listView.setAdapter(new DrawerMenuAdapterList(this,
                ApplicationConstants.getTextListOfMenu(this),
                ApplicationConstants.getResourcesOfImagesIconsOfMenu()));
    }

    @Override
    public void makeTransaction(Fragment fragment, FragmentManager fragmentManager, int resourceView) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(resourceView, fragment);
        fragmentTransaction.commit();
    }

}
