package com.netpaisa.aepsriseinlib.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetLocation implements LocationListener {

    Context context;
    public boolean isGPSEnabled = false;
    public boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1L;
    private static final long MIN_TIME_BW_UPDATES = 1L;
    public LocationManager locationManager;

    public GetLocation(Context context) {
        this.context = context;
        this.getLocation();
    }

    @SuppressLint({"MissingPermission", "WrongConstant"})
    public Location getLocation() {
        try {
            this.locationManager = (LocationManager)this.context.getSystemService("location");
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            Log.v("isGPSEnabled", "=" + this.isGPSEnabled);
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            Log.v("isNetworkEnabled", "=" + this.isNetworkEnabled);
            if (this.isGPSEnabled || this.isNetworkEnabled) {
                this.canGetLocation = true;
                if (this.isNetworkEnabled) {
                    this.location = null;
                    this.locationManager.requestLocationUpdates("network", 1L, 1.0F, this);
                    Log.d("Network", "Network");
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("network");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }

                if (this.isGPSEnabled) {
                    this.location = null;
                    if (this.location == null) {
                        this.locationManager.requestLocationUpdates("gps", 1L, 1.0F, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (this.locationManager != null) {
                            this.location = this.locationManager.getLastKnownLocation("gps");
                            if (this.location != null) {
                                this.latitude = this.location.getLatitude();
                                this.longitude = this.location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Throwable var2) {
        }

        return this.location;
    }

    public void stopUsingGPS() {
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(this);
        }

    }

    public double getLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }

        return this.latitude;
    }

    public double getLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }

        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public boolean isGPSEnabled() {
        return this.isGPSEnabled;
    }

//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
//        alertDialog.setTitle("GPS is settings");
//        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
//        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
//                GetLocation.this.context.startActivity(intent);
//            }
//        });
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alertDialog.show();
//    }

    public void onLocationChanged(Location location) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderDisabled(String provider) {
        this.isGPSEnabled = false;
    }

    public void onProviderEnabled(String provider) {
        this.isGPSEnabled = true;
    }

    public String getAddress(double lat, double langt) {
        String complete_add = "";
        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, langt, 1);
            String address = ((Address)addresses.get(0)).getAddressLine(0);
            String city = ((Address)addresses.get(0)).getLocality();
            String state = ((Address)addresses.get(0)).getAdminArea();
            String country = ((Address)addresses.get(0)).getCountryName();
            String postalCode = ((Address)addresses.get(0)).getPostalCode();
            String knownName = ((Address)addresses.get(0)).getFeatureName();
            complete_add = address + ", " + city + ", " + state + ", " + country + ", " + postalCode + ", " + knownName;
        } catch (IndexOutOfBoundsException var14) {
            var14.printStackTrace();
        } catch (IOException var15) {
            var15.printStackTrace();
        } catch (Exception var16) {
            var16.printStackTrace();
        }

        return complete_add;
    }
}

