package co.com.script.conciertodeconciertos.interfaces;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by User on 16/05/2015.
 */
public interface DialogMethodsInterface {

    public void createLoadingDialog(ProgressDialog progressDialog, String message);
    public void showDialog(ProgressDialog dialog);
    public void stopDialog(ProgressDialog dialog);

}
