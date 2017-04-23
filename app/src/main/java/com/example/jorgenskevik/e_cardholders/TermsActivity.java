package com.example.jorgenskevik.e_cardholders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


/**
 * The type Terms activity.
 */
public class TermsActivity extends Activity {
    /**
     * The constant CAM_REQUEST_CODE.
     */
    public static final int CAM_REQUEST_CODE = 4545;
    /**
     * The constant maxBuildVersion.
     */
    public static final int maxBuildVersion = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.terms_view);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    /**
     * Open card.
     *
     * @param view the view
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void openCard(View view) {
        String buildVersion = Build.VERSION.RELEASE;
        String firstLetter = String.valueOf(buildVersion.charAt(0));
        int number = Integer.parseInt(firstLetter);
        if(number < maxBuildVersion){
            Intent intent = new Intent(TermsActivity.this, UserActivity.class);
            startActivity(intent);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(TermsActivity.this, UserActivity.class);
            startActivity(intent);
        }else{
            String[] permissionRequest = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissionRequest, CAM_REQUEST_CODE);
            Toast.makeText(this, R.string.GiveAccess, Toast.LENGTH_SHORT).show();

        }
    }
}