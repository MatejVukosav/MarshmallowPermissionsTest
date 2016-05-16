package vuki.com.marshmallowpermissionstest;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnRequestPermissionsResultCallback {

    Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        context = this;

        findViewById( R.id.phone_state ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                PermissionUtils.permissionCheck( (Activity) context, Manifest.permission.READ_PHONE_STATE, PermissionUtils.REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE, R.id.root );
            }
        } );
        findViewById( R.id.not_exists ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                PermissionUtils.permissionCheck( (Activity) context, Manifest.permission.SEND_SMS, 5, R.id.root );
            }
        } );
        findViewById( R.id.write_external ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                PermissionUtils.permissionCheck( (Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE, PermissionUtils.REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE, R.id.root );
            }
        } );
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if( grantResults.length > 0 ) {
            switch( requestCode ) {
                case PermissionUtils.REQUEST_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                    if( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText( context, "dopustio je external", Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( context, "odbio je external", Toast.LENGTH_SHORT ).show();
                    }
                    break;
                case PermissionUtils.REQUEST_PERMISSIONS_READ_PHONE_STATE:
                    if( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                        Toast.makeText( context, "dopustio je phone_state ", Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( context, "odbio je phone_state", Toast.LENGTH_SHORT ).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
