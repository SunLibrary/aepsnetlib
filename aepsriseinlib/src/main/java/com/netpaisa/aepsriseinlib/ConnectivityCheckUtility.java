package com.netpaisa.aepsriseinlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

public class ConnectivityCheckUtility {
    Context context;
    ConnectivityManager connectivityManager;
    TelephonyManager telephonyManager;

    @SuppressLint("WrongConstant")
    public ConnectivityCheckUtility(Context context) {
        this.context = context;
        this.connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        this.telephonyManager = (TelephonyManager)context.getSystemService("phone");
    }

    public boolean isInternetAvailable() {
        return this.connectivityManager.getNetworkInfo(0) != null && this.connectivityManager.getNetworkInfo(0).getState() == State.CONNECTED || this.connectivityManager.getNetworkInfo(1) != null && this.connectivityManager.getNetworkInfo(1).getState() == State.CONNECTED;
    }
}
