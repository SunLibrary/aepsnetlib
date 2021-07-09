package com.netpaisa.aepsnetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.netpaisa.aepsriseinlib.AepsRiseinActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Intent intentTrans = new Intent(MainActivity.this , AepsRiseinActivity.class);
//        intentTrans.putExtra("agent_id", AppConst.AGENT_OUTLET_ID);
//        intentTrans.putExtra("developer_id", AppConst.RISETECH_DEV_ID);
//        intentTrans.putExtra("password", AppConst.RISEINTECH_PASSWORD);
//        intentTrans.putExtra("bankVendorType", mVendorType);
//        intentTrans.putExtra("clientTransactionId", createMultipleTransactionID());
        intentTrans.putExtra("primary_color", R.color.colorPrimary);
        intentTrans.putExtra("accent_color", R.color.colorAccent);
        intentTrans.putExtra("primary_dark_color", R.color.colorPrimaryDark);
        intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentTrans);

    }
}