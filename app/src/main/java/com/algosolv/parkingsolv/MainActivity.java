package com.algosolv.parkingsolv;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;
    TextView signup;
    EditText mobileno;
    String mobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.loginbutton);
        signup = (TextView) findViewById(R.id.signuptextview);
        mobileno = (EditText) findViewById(R.id.mobileedittext);

        login.setOnClickListener(this);

        signup.setOnClickListener(this);
        // GPS enable settings
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_SHORT).show();
        }
        else
        {
            showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.loginbutton)
        {
            mobilenumber = mobileno.getText().toString();
            if(mobilenumber.length()<9 || mobilenumber=="")
            {
                Toast.makeText(getApplicationContext(),"Enter valid Phone Number",Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
                finish();
            }
        }
        if(v.getId()==R.id.signuptextview)
        {
            Intent intent = new Intent(MainActivity.this,MapsActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void showGPSDisabledAlertToUser() {
        /*
        Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps"))
        {
            //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }*/

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    }
