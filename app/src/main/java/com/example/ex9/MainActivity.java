package com.example.ex9;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final NetworkBroadcastReceiver br= new NetworkBroadcastReceiver();
    private static final String TAG = "MainActivity";

    Button btn;
    /**ADDED IN LAB9:
     * a parameter that holds a method - registerForActivityResult.
     * The method will be called after the user answer:
     * ALLOW / DENY, regarding to the permission request*/
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission() , isGranted ->{
                if(isGranted)
                {
                    Log.e(TAG, "onActivityResult: PERMISSION GRANTED");
                }
                else
                {
                    Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**onResume - before the activity is shown*/
    @Override
    protected void onResume() {
        super.onResume();

        /**DYNAMIC REGISTRATION (run-time)*/
        /**to Network availability*/

        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(br, filter);
        /**********************************/

        /**Method of the OS*/
        if(ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            /**'requestPermissionLauncher' is the parameter we defined*/

            /**the line below will show a dialog*/
            requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS);
            /**'requestPermissionLauncher' gets the result of this request*/
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

}