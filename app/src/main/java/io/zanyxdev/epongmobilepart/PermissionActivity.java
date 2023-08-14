package io.zanyxdev.epongmobilepart;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

public class PermissionActivity extends AppCompatActivity {
    private  String TAG = "PermissionActivity";
    private final int REQUEST_CONTACTS_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkContactsPermission();
    }

    private void checkContactsPermission() {
        // Check if permission is not granted
        Log.d(TAG, "Permission for contacts is not granted");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
        ) {
            // Check if permission is not granted
            Log.d(TAG, "Permission for contacts is not granted");

            // This condition only becomes true if the user has denied the permission previously
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_CONTACTS)) {
                showRationaleDialog(
                        getString(R.string.rationale_title),
                        getString(R.string.rationale_desc),
                        android.Manifest.permission.READ_CONTACTS,
                        REQUEST_CONTACTS_STATE
                );
            } else {
                // Perform a permission check
                Log.d(TAG, "Checking permission");
                requestPermissions(
                        new String[] {Manifest.permission.READ_CONTACTS},
                        REQUEST_CONTACTS_STATE
                );
            }
        } else {
            Log.d(TAG, "Permission is already granted, do your magic here!");
        }
    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private void showRationaleDialog(String title, String message, String permission, int requestCode) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, requestCode);
                    }
                });
        builder.create().show();
    }
    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode) {
            case REQUEST_CONTACTS_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PermissionActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PermissionActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
