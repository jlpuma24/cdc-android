package co.com.script.conciertodeconciertos.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 16/05/2015.
 */
public class ApplicationUtilsHelper {

    public String getApplicationPackage(Context mContext){

        try {
            PackageInfo info = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                return Base64.encodeToString(md.digest(),
                    Base64.DEFAULT);

            }
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
        return "";
    }
}
