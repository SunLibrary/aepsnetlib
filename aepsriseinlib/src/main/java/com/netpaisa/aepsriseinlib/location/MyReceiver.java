package com.netpaisa.aepsriseinlib.location;

import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;


public class MyReceiver extends WakefulBroadcastReceiver {

    Location location;

    public MyReceiver(Location location) {
        this.location = location;
    }

    public void onReceive(Context context, Intent intent) {
        System.out.println("Receiver Called <><><><> ---");
        this.location.setLocation(intent.getDoubleExtra("lat", 5.0D), intent.getDoubleExtra("lng", 5.0D), intent.getStringExtra("provider"));
    }
}

