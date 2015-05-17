package co.com.script.conciertodeconciertos.helpers;

import android.app.ProgressDialog;
import android.content.Context;

import co.com.script.conciertodeconciertos.R;
import co.com.script.conciertodeconciertos.interfaces.DialogMethodsInterface;

/**
 * Created by User on 16/05/2015.
 */
public class DialogHelper implements DialogMethodsInterface{

    private static DialogHelper mInstance;
    private Context mContext;

    private DialogHelper(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized DialogHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DialogHelper(context);
        }
        return mInstance;
    }

    @Override
    public void createLoadingDialog(ProgressDialog progressDialog, String message) {
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.anim));
    }

    @Override
    public void showDialog(ProgressDialog dialog) {
        dialog.show();
    }

    @Override
    public void stopDialog(ProgressDialog dialog) {
        dialog.hide();
    }

}
