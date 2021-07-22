package com.netpaisa.aepsriseinlib.location;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends IntentService implements LocationListener {
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude;
    double longitude;
    LocationManager locationManager;
    public static final String FILTER_ACTION_KEY_FOREGROUND = "FOREGROUND_RECEIVER_ACTION";
    Location location;

    public MyService() {
        super("MyService");
    }

    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra("message");
        intent.setAction("FOREGROUND_RECEIVER_ACTION");
        this.fn_getlocation(intent);
    }

    public void onLocationChanged(Location location) {
        System.out.println("on location Changed ---" + location.getLatitude() + "   , " + location.getLongitude());
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    @SuppressLint("WrongConstant")
    private void fn_getlocation(Intent intent) {
        this.locationManager = (LocationManager)this.getApplicationContext().getSystemService("location");
        this.isGPSEnable = this.locationManager.isProviderEnabled("gps");
        this.isNetworkEnable = this.locationManager.isProviderEnabled("network");
        if (!this.isGPSEnable && !this.isNetworkEnable) {
            this.location = null;
            this.latitude = 0.0D;
            this.longitude = 0.0D;
            intent.putExtra("lat", this.latitude);
            intent.putExtra("lng", this.longitude);
            intent.putExtra("provider", "");
            LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(intent);
        } else {
            if (this.isNetworkEnable) {
                this.location = null;
                if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return;
                }

                this.locationManager.requestLocationUpdates("network", 1000L, 0.0F, this);
                if (this.locationManager != null) {
                    this.location = this.locationManager.getLastKnownLocation("network");
                    if (this.location != null) {
                        Log.e("latitude", this.location.getLatitude() + "");
                        Log.e("longitude", this.location.getLongitude() + "");
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                        intent.putExtra("lat", this.latitude);
                        intent.putExtra("lng", this.longitude);
                        intent.putExtra("provider", "N");
                        (new StringBuilder()).append("location is: latitude--> ").append(this.latitude).append("longitude---->").append(this.longitude).toString();
                        LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(intent);
                        this.locationManager.removeUpdates(this);
                        return;
                    }
                }
            }

            if (this.isGPSEnable) {
                this.location = null;
                if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return;
                }

                this.locationManager.requestLocationUpdates("gps", 1000L, 0.0F, this);
                if (this.locationManager != null) {
                    this.location = this.locationManager.getLastKnownLocation("gps");
                    if (this.location != null) {
                        Log.e("latitude", this.location.getLatitude() + "");
                        Log.e("longitude", this.location.getLongitude() + "");
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                        intent.putExtra("lat", this.latitude);
                        intent.putExtra("lng", this.longitude);
                        intent.putExtra("provider", "G");
                        (new StringBuilder()).append("location is: latitude--> ").append(this.latitude).append("longitude---->").append(this.longitude).toString();
                        LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(intent);
                        this.locationManager.removeUpdates(this);
                        return;
                    }
                }
            }

            if (this.locationManager != null) {
                this.locationManager.removeUpdates(this);
            }
        }

    }
}
