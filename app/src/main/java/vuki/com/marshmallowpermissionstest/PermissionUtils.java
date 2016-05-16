package vuki.com.marshmallowpermissionstest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mvukosav on 13.5.2016..
 */
public final class PermissionUtils {

    public static final int REQUEST_PERMISSIONS_READ_PHONE_STATE = 1;
    public static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 3;

    /*If the app has the permission, the method returns PackageManager.PERMISSION_GRANTED ==0
    If the app does not have the permission, the method returns PERMISSION_DENIED==-1,
     and the app has to explicitly ask the user for permission.
     */
    public static void permissionCheck( Activity activity, String permission, int callbackCode, int rootLayout ) {
        if( ContextCompat.checkSelfPermission( activity, permission ) != PackageManager.PERMISSION_GRANTED ) {
            //SHOULD I SHOW EXPLANATION?
            if( ActivityCompat.shouldShowRequestPermissionRationale( activity, permission ) ) {
                //SHOW AN EXPLANATION TO THE USER
                showExplanation( activity, permission, callbackCode, rootLayout );
            } else {
                //No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions( activity, new String[]{ permission }, callbackCode );
            }
        }
    }

    public static boolean isPermissionGranted( Activity activity, String permissions ) {
        return ContextCompat.checkSelfPermission( activity, permissions ) == PackageManager.PERMISSION_GRANTED;
    }

    private static void showExplanation( Activity activity, String permission, int callbackCode, int rootLayout ) {
        switch( callbackCode ) {
            case REQUEST_PERMISSIONS_READ_PHONE_STATE:
                displayReason( activity, permission, callbackCode, rootLayout, activity.getResources().getString( R.string.permission_read_phone_state_message ) );
                break;
            case REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE:
                displayReason( activity, permission, callbackCode, rootLayout, activity.getResources().getString( R.string.permission_read_external_storage ) );
                break;
            case REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                displayReason( activity, permission, callbackCode, rootLayout, activity.getResources().getString( R.string.permission_write_external_storage ) );
                break;
            default:
                // throw new PermissionException( "Permission with code " + String.valueOf( callbackCode) + "is not processed." );
        }

    }

    private static void displayReason( final Activity activity, final String permission, final int callbackCode, final int rootLayout, String message ) {
        View rootView = activity.findViewById( rootLayout );
        final View snackView;
        final Snackbar snackbar = Snackbar
                .make( rootView, message, Snackbar.LENGTH_INDEFINITE )
                .setAction( "Ask me again", new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        ActivityCompat.requestPermissions( activity, new String[]{ permission }, callbackCode );
                    }

                } )
                .setActionTextColor( ContextCompat.getColor( activity, R.color.white ) );

        snackView = snackbar.getView();
        TextView snackTextView = (TextView) snackView.findViewById( android.support.design.R.id.snackbar_text );
        snackTextView.setTextColor( Color.YELLOW );
        snackbar.show();

    }

    private static final class PermissionException extends Exception {
        public PermissionException( String detailMessage ) {
            super( detailMessage );
        }
    }

}
