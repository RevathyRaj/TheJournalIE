package revathi.thejournalie.application;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by revathi on 14/05/18.
 */

public class JournalIeApplication extends Application {

    /* store context*/
    private static Context mContext;
    /* store Application instance */
    private static JournalIeApplication mInstance;
    private static ProgressDialog progressDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
     /*
     * Returns the current activity context
     *
     * @return context
     */

    public static Context getCurrentActivityContext() {
        return mContext;
    }

    /**
     * Sets the current activity contest.
     * <p/>
     * Make sure that you call this in onCreate and onResume of an activity
     *
     * @param context current activity context
     */
    public static void setCurrentActivityContext(Context context) {

        mContext = context;
    }


    /**
     * @return application instance
     */
    public static synchronized JournalIeApplication getInstance() {
        return mInstance;
    }

    /**
     * shows progress dialog with the given message
     *
     * @param message
     */
    public void showProgressDialog(String message) {

        if (progressDialog != null)
            progressDialog.dismiss();

        progressDialog = new ProgressDialog(getCurrentActivityContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * Show toast message with the given message
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }

    /**
     * cancels progress dialog if shown
     */
    public void hideProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing())
                progressDialog.dismiss();
    }

    /**
     * Checks the Internet connection
     *
     * @return boolean
     */
    public boolean isConnectedToInterNet() {
        ConnectivityManager cManager = (ConnectivityManager) getCurrentActivityContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cManager != null) {
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            boolean isConnected = nInfo != null &&
                    nInfo.isConnectedOrConnecting();
            return isConnected;
        }
        return false;
    }
}
