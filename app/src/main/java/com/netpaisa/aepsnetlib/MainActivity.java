package com.netpaisa.aepsnetlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.netpaisa.aepsriseinlib.AepsRiseinActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        TextView mGoToAEPS = (TextView) findViewById(R.id.idGoToAEPS);
        mGoToAEPS.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.idGoToAEPS:

                Intent intentTrans = new Intent(MainActivity.this , AepsRiseinActivity.class);
                intentTrans.putExtra("api_access_key", "2755870fa87c2aed97skg43fa9e49db1");
                intentTrans.putExtra("outletid", "16476");
                intentTrans.putExtra("pan_no", "AZRPG6750B");
                intentTrans.putExtra("primary_color", R.color.colorPrimary);
                intentTrans.putExtra("accent_color", R.color.colorAccent);
                intentTrans.putExtra("primary_dark_color", R.color.colorPrimaryDark);
                intentTrans.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentTrans);

                break;
        }
    }
}