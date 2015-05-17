package co.com.script.conciertodeconciertos.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

/**
 * Created by User on 06/05/2015.
 */
public interface BaseActivityMethodsInterface {

    public void buildMenu(ListView listView);
    public void makeTransaction(Fragment fragment, FragmentManager fragmentManager, int resourceView);
    public void openDrawerMenu(View v);

}
