package com.netpaisa.aepsnetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.netpaisa.aepsriseinlib.AepsRiseinActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

               Intent intentIciciKyc = new Intent(MainActivity.this, AepsRiseinActivity.class);
//                intentIciciKyc.putExtra("agent_id", AppConst.AGENT_OUTLET_ID);
//                intentIciciKyc.putExtra("developer_id", AppConst.RISETECH_DEV_ID);
//                intentIciciKyc.putExtra("password", AppConst.RISEINTECH_PASSWORD);
//                intentIciciKyc.putExtra("mobile", mMobileNo);
//                intentIciciKyc.putExtra("aadhaar", mAadhaarNo);
//                intentIciciKyc.putExtra("email", mEmailId);
//                intentIciciKyc.putExtra("pan", mPanNo);
                intentIciciKyc.putExtra("primary_color", R.color.colorPrimary);
                intentIciciKyc.putExtra("accent_color", R.color.colorAccent);
                intentIciciKyc.putExtra("primary_dark_color", R.color.colorPrimaryDark);
                intentIciciKyc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivityForResult(intentIciciKyc, 1);
                //startActivityForResult(intentIciciKyc, 1);
                startActivity(intentIciciKyc);
    }
}