package com.netpaisa.aepsriseinlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.widget.Toast;

public class AepsRiseinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeps_risein);

        ConnectivityCheckUtility connectivityCheckUtility = new ConnectivityCheckUtility(this);

        if (!connectivityCheckUtility.isInternetAvailable()) {
            Toast.makeText(this,  "Please connect to Internet!", Toast.LENGTH_SHORT).show();
        }

        initView();

//        i = this.getIntent();
//        if (i != null) {
//            Intent retunIntent;
//            if (i.getStringExtra("clientTransactionId") == null) {
//                retunIntent = new Intent();
//                retunIntent.putExtra("statusCode", 1);
//                retunIntent.putExtra("message", "clientTransactionId Not Recieved,Please add");
//                this.setResult(0, retunIntent);
//                this.finish();
//            } else {
//                this.agent_id = i.getStringExtra("agent_id");
//                this.developer_id = i.getStringExtra("developer_id");
//                this.password = i.getStringExtra("password");
//                this.clientTransactionId = i.getStringExtra("clientTransactionId");
//                this.bankVendorType = i.getStringExtra("bankVendorType");
//                if (this.clientTransactionId.length() >= 6 && this.clientTransactionId.length() <= 20) {
//                    Utils.PRIMARY_COLOR = i.getIntExtra("primary_color", Utils.PRIMARY_COLOR);
//                    Utils.ACCENT_COLOR = i.getIntExtra("accent_color", Utils.ACCENT_COLOR);
//                    Utils.PRIMARY_DARK_COLOR = i.getIntExtra("primary_dark_color", Utils.PRIMARY_DARK_COLOR);
//                } else {
//                    retunIntent = new Intent();
//                    retunIntent.putExtra("message", "clientTransactionId Should more then 6 and Less then 20 Characters");
//                    this.setResult(0, retunIntent);
//                    this.finish();
//                }
//            }
//        }
    }


    private void initView() {


    }
}