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
import android.widget.ListView;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.UserSessionManager;
import co.com.script.conciertodeconciertos.adapters.DrawerMenuAdapterList;
import co.com.script.conciertodeconciertos.constants.ApplicationConstants;
import co.com.script.conciertodeconciertos.fragments.FragmentDetailNews;
import co.com.script.conciertodeconciertos.interfaces.BaseActivityMethodsInterface;

/**
 * Created by User on 07/05/2015.
 */
public class DetailNewActivity extends FragmentActivity implements BaseActivityMethodsInterface {

    private DrawerLayout mDrawerLayout;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        buildMenu((ListView) findViewById(R.id.listViewMenu));
        makeTransaction(new FragmentDetailNews(),getSupportFragmentManager(),R.id.content_frame);

        ((ListView) findViewById(R.id.listViewMenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0: startActivity(new Intent(getApplicationContext(),NewsActivity.class));
                            finish();
                            break;

                    case 1: startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        finish();
                        break;

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

    @Override
    public void openDrawerMenu(View v){
        mDrawerLayout.openDrawer((ListView) findViewById(R.id.listViewMenu));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,NewsActivity.class));
        finish();
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
