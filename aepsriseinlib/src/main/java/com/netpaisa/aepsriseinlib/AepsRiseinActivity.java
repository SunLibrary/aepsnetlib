package com.netpaisa.aepsriseinlib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.netpaisa.aepsriseinlib.adapter.AepsMiniStmtAdapter;
import com.netpaisa.aepsriseinlib.adapter.SpinnerAdapter;
import com.netpaisa.aepsriseinlib.location.GetLocation;
import com.netpaisa.aepsriseinlib.model.BalanceAepsModel;
import com.netpaisa.aepsriseinlib.model.BankListAepsModel;
import com.netpaisa.aepsriseinlib.model.MiniStmntAepsModel;
import com.netpaisa.aepsriseinlib.model.WithdrawalAepsModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.XML;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import static android.text.Html.fromHtml;


public class AepsRiseinActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout mAepsMainLayout, mToolbarLayout, mBackLLayout, linearAdharNo, linearAmount, aepsInstaTransLayout, emptyLLayout;
    private EditText edtxAdharNo,  edtxMobileNo, edtxAmount;

    private Spinner spnrTransType, spnrDeviceType, bankNameSpinner;
    private Button  btnCapture, mNoGpsBtn, mYesGpsBtn ;
    private ApiServiceAeps apiServiceAeps;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private  TextView mTitleText ;
    private String mCaptureAadharNo = "", mCaptureBankID = "", mCaptureMobileNo = "", mCaptureAmount = "0";

    private String selectedTransType = "", mTransType = "", mOutletIdAEPSInsta = "", mPanNoAEPSInsta = "";


    final String transTypeArray[] = {"Select Transaction Type", "Cash Withdrawal", "Balance Enquiry", "Mini Statement", "Aadhaar Pay"};


    final String deviceTypeArray[] = {"Choose Biometric Device", "Mantra MFS 100", "Morpho MSO 1300", "Startek FM220U"};


    private int requestCodeMantra = 1;
    private int requestCodeMorpho = 2;
    private int requestCodeStartek = 3;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 11;
    private final int REQUEST_LOCATION_STORAGE = 200;

    private String selectedDeviceType = "", mDeviceType = "";


    //private String mApiAccessKey = "2755870fa87c2aed97skg43fa9e49db1", mOutletId = "16476", mPanNo = "AZRPG6750B", mBiometricData = "";
    private String mApiAccessKey = "", mOutletId = "", mPanNo = "",mAppLabel="", mBiometricData = "";

    private List<BankListAepsModel.DATum> bankDataList = new ArrayList(0);
    private ArrayList<String> mBankNameList = null;

    /**************Start*************/
    private GetLocation getLocation = null;
    private ConnectivityCheckUtility networkCheck;
    private String location = "";
    /**************End*************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_aeps_risein);

//        Toolbar toolbar = findViewById(R.id.toolbarAeps);
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(true);
//        }

        mAepsMainLayout = (LinearLayout) findViewById(R.id.idAepsMainLayout);
        if (apiServiceAeps == null) {
            apiServiceAeps = ApiClientAeps.getClient(getApplicationContext()).create(ApiServiceAeps.class);
        }
        if (networkCheck == null) {
            networkCheck = new ConnectivityCheckUtility(AepsRiseinActivity.this);
        }
        if (getLocation == null) {
            getLocation = new GetLocation(AepsRiseinActivity.this);
        }

        onCheckAccessibility();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation = new GetLocation(AepsRiseinActivity.this);
        //Log.e("Resume latitude ", this.getLocation.getLatitude() + "");
        //Log.e("Resume longitude ", this.getLocation.getLongitude() + "");

        if (this.getLocation.getLatitude() > 0.0D && this.getLocation.getLongitude() > 0.0D) {
            aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
            aepsInstaTransLayout.setVisibility(View.VISIBLE);
            emptyLLayout = (LinearLayout) findViewById(R.id.emptyLLayout);
            emptyLLayout.setVisibility(View.GONE);
            if (mBankNameList==null) {
                initView();
                //Log.e("Resume Bank Name Size ", mBankNameList + "");
            }
        } else if (this.getLocation.getLatitude() == 0.0D && this.getLocation.getLongitude() == 0.0D) {
            if (!this.getLocation.isGPSEnabled) {
                aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
                aepsInstaTransLayout.setVisibility(View.GONE);
                emptyLLayout = (LinearLayout) findViewById(R.id.emptyLLayout);
                emptyLLayout.setVisibility(View.VISIBLE);

            }
        }

        if (!this.getLocation.isGPSEnabled) {
            aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
            aepsInstaTransLayout.setVisibility(View.GONE);
            emptyLLayout = (LinearLayout) findViewById(R.id.emptyLLayout);
            emptyLLayout.setVisibility(View.VISIBLE);
        }


    }

    private void onCheckAccessibility() {

        getLocation = new GetLocation(AepsRiseinActivity.this);

        if (!this.networkCheck.isInternetAvailable()) {
            Toast.makeText(AepsRiseinActivity.this, "Please check, Network is not available", Toast.LENGTH_SHORT).show();
        } else if (!this.checkPermission()) {
            this.requestPermission();
        } else if (!this.getLocation.isGPSEnabled) {
             showSettingsAlert();
        } else if (this.getLocation.getLatitude() > 0.0D && this.getLocation.getLongitude() > 0.0D) {
            initView();
        } else {
            initView();
            Toast.makeText(AepsRiseinActivity.this, "Please check Network, Internet seems to be weak! ", Toast.LENGTH_SHORT).show();
        }


       // Log.e("latitude ", this.getLocation.getLatitude() + "");
       // Log.e("longitude ", this.getLocation.getLongitude() + "");

    }


    private void initView() {

        mToolbarLayout = (LinearLayout) findViewById(R.id.idAepsToolbarLayout);
        mBackLLayout = (LinearLayout) findViewById(R.id.idBackLLayout);
        mBackLLayout.setOnClickListener(this);
        mTitleText = (TextView) findViewById(R.id.idTitleText);

        mBankNameList = new ArrayList();

        Intent mIntent = this.getIntent();
        if (mIntent != null) {

            mApiAccessKey = mIntent.getStringExtra("api_access_key");
            mOutletId = mIntent.getStringExtra("outletid");
            mPanNo = mIntent.getStringExtra("pan_no");
            mAppLabel = mIntent.getStringExtra("app_label");
            mTitleText.setText(mAppLabel);
            //Objects.requireNonNull(getSupportActionBar()).setTitle(mAppLabel);
            MyUtils.PRIMARY_DARK_COLOR = mIntent.getIntExtra("primary_dark_color", MyUtils.PRIMARY_DARK_COLOR);
            MyUtils.PRIMARY_COLOR = mIntent.getIntExtra("primary_color", MyUtils.PRIMARY_COLOR);
            mToolbarLayout.setBackgroundColor(MyUtils.PRIMARY_COLOR);
            //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(MyUtils.PRIMARY_COLOR));
            MyUtils.ACCENT_COLOR = mIntent.getIntExtra("accent_color", MyUtils.ACCENT_COLOR);

        }

        if (this.getLocation.getLatitude() > 0.0D && this.getLocation.getLongitude() > 0.0D) {
            aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
            aepsInstaTransLayout.setVisibility(View.VISIBLE);
            emptyLLayout = (LinearLayout) findViewById(R.id.emptyLLayout);
            emptyLLayout.setVisibility(View.GONE);
        } else if (this.getLocation.getLatitude() == 0.0D && this.getLocation.getLongitude() == 0.0D) {
            aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
            aepsInstaTransLayout.setVisibility(View.GONE);
            emptyLLayout = (LinearLayout) findViewById(R.id.emptyLLayout);
            emptyLLayout.setVisibility(View.VISIBLE);
        }


        linearAdharNo = (LinearLayout) findViewById(R.id.linearAdharNo);
        linearAdharNo.setVisibility(View.VISIBLE);


        edtxAdharNo = (EditText) findViewById(R.id.edtxAdharNo);
        edtxMobileNo = (EditText) findViewById(R.id.edtxMobileNo);
        linearAmount = (LinearLayout) findViewById(R.id.linearAmount);
        edtxAmount = (EditText) findViewById(R.id.edtxAmount);

        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(this);

        mNoGpsBtn = (Button) findViewById(R.id.idNoGpsBtn);
        mNoGpsBtn.setOnClickListener(this);
        mYesGpsBtn = (Button) findViewById(R.id.idYesGpsBtn);
        mYesGpsBtn.setOnClickListener(this);


        bankNameSpinner = (Spinner) findViewById(R.id.bank_name_spinner);

        bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("BankList Size ", bankDataList.size() + "");
                if (bankDataList.size() > 0) {
                    String strCashInBankName = parent.getSelectedItem().toString();
                    for (int i = 0; i < bankDataList.size(); ++i) {
                        if ((bankDataList.get(i)).getBankName().equalsIgnoreCase(strCashInBankName)) {
                            mCaptureBankID = (bankDataList.get(i)).getBankiin();
                            Log.e("Capture BankID", mCaptureBankID);
                            break;
                        }
                    }
                }
                bankNameSpinner.setPadding(0, 0, 0, 0);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("Capture BankID", mCaptureBankID);
            }
        });


        spnrDeviceType = findViewById(R.id.txDeviceType);
        ArrayAdapter spinnerDeviceAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, deviceTypeArray);
        spinnerDeviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrDeviceType.setAdapter(spinnerDeviceAdapter);
        spinnerDeviceAdapter.notifyDataSetChanged();

        spnrDeviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedDeviceType = deviceTypeArray[arg2];
                if (selectedDeviceType.equals("Mantra MFS 100")) {
                    mDeviceType = "Mantra MFS 100";
                } else if (selectedDeviceType.equals("Morpho MSO 1300")) {
                    mDeviceType = "Morpho MSO 1300";
                } else if (selectedDeviceType.equals("Startek FM220U")) {
                    mDeviceType = "Startek FM220U";
                } else {
                    mDeviceType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedDeviceType = deviceTypeArray[0];
            }
        });


        spnrTransType = findViewById(R.id.txTransType);
        ArrayAdapter spinnerTransTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, transTypeArray);
        spinnerTransTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTransType.setAdapter(spinnerTransTypeAdapter);
        spinnerTransTypeAdapter.notifyDataSetChanged();

        spnrTransType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                selectedTransType = transTypeArray[arg2];
                if (selectedTransType.equals("Cash Withdrawal")) {
                    mTransType = "WAP";
                    linearAmount.setVisibility(View.VISIBLE);
                } else if (selectedTransType.equals("Balance Enquiry")) {
                    mTransType = "BAP";
                    linearAmount.setVisibility(View.INVISIBLE);
                } else if (selectedTransType.equals("Mini Statement")) {
                    mTransType = "SAP";
                    linearAmount.setVisibility(View.INVISIBLE);
                } else if (selectedTransType.equals("Aadhaar Pay")) {
                    mTransType = "MZZ";
                    linearAmount.setVisibility(View.VISIBLE);
                } else {
                    mTransType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedTransType = transTypeArray[0];
            }
        });

        loadBankList();

        Log.e("init latitude ", this.getLocation.getLatitude() + "");
        Log.e("init longitude ", this.getLocation.getLongitude() + "");

    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AepsRiseinActivity.this, "android.permission.ACCESS_FINE_LOCATION");
        int result1 = ContextCompat.checkSelfPermission(AepsRiseinActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int result2 = ContextCompat.checkSelfPermission(AepsRiseinActivity.this, "android.permission.READ_EXTERNAL_STORAGE");
        int result3 = ContextCompat.checkSelfPermission(AepsRiseinActivity.this, "android.permission.ACCESS_COARSE_LOCATION");
        return result == 0 && result1 == 0 && result2 == 0 && result3 == 0;
    }

    private void requestPermission() {
        try {
            if (
                    ContextCompat.checkSelfPermission(AepsRiseinActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(AepsRiseinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(AepsRiseinActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(AepsRiseinActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(AepsRiseinActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION"}, REQUEST_LOCATION_STORAGE);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(AepsRiseinActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
                break;

            case REQUEST_LOCATION_STORAGE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == 0;
                    boolean readAccepted = grantResults[1] == 0;
                    boolean writeAccepted = grantResults[2] == 0;
                    boolean locFineAccepted = grantResults[3] == 0;
                    if (locationAccepted && readAccepted && writeAccepted && locFineAccepted) {
                        initView();

                        Snackbar.make(mAepsMainLayout, "Permission Granted, Now you can access location data and Write data.", Snackbar.LENGTH_LONG).show();
                    } else {
                        initView();

                        Snackbar.make(mAepsMainLayout, "Permission Denied, You cannot access location data and Write data.", Snackbar.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= 23 && this.shouldShowRequestPermissionRationale("android.permission.ACCESS_FINE_LOCATION")) {
                            this.showMessageOKCancel("You need to allow access to all the permissions", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= 23) {
                                        initView();

                                        AepsRiseinActivity.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE",
                                                "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE"}, REQUEST_LOCATION_STORAGE);
                                    }
                                }
                            });
                            return;
                        }
                    }
                }
            default:
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        initView();
        (new AlertDialog.Builder(AepsRiseinActivity.this)).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create().show();
    }


    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View mView) {

        if (mView.getId() == R.id.idBackLLayout) {
            onBackPressed();
        }

        if (mView.getId() == R.id.idNoGpsBtn) {
            initView();
            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            AepsRiseinActivity.this.startActivity(intent);
        }
        if (mView.getId() == R.id.idYesGpsBtn) {
            initView();
            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            AepsRiseinActivity.this.startActivity(intent);
        }
        if (mView.getId() == R.id.btnCapture) {

            if (!this.networkCheck.isInternetAvailable()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please check, Network is not available.");
                return;
            }

            if (mCaptureBankID.isEmpty()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, MsgConst.BANK_NAME_SELECT);
                return;
            }

            mCaptureAadharNo = edtxAdharNo.getText().toString().trim();
            if (mCaptureAadharNo.isEmpty()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please " + MsgConst.AADHAR);
                return;
            }
            if (mCaptureAadharNo.contains("-")) {
                mCaptureAadharNo = mCaptureAadharNo.replaceAll("-", "").trim();
            }

            if (mCaptureAadharNo.length() != 12) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please enter valid aadhaar number.");
                return;
            }


            mCaptureMobileNo = edtxMobileNo.getText().toString().trim();
            if (mCaptureMobileNo.isEmpty()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, MsgConst.MOBILE_ENTER);
                return;
            }

            if (selectedDeviceType.equals("Mantra MFS 100")) {
                mDeviceType = "Mantra MFS 100";
            } else if (selectedDeviceType.equals("Morpho MSO 1300")) {
                mDeviceType = "Morpho MSO 1300";
            } else if (selectedDeviceType.equals("Startek FM220U")) {
                mDeviceType = "Startek FM220U";
            } else {
                mDeviceType = "";
            }


            if (mDeviceType.isEmpty()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, MsgConst.SELECT_DEVICE_TYPE);
                return;
            }

            if (selectedTransType.equals("Cash Withdrawal")) {
                mTransType = "WAP";
                linearAmount.setVisibility(View.VISIBLE);
            } else if (selectedTransType.equals("Balance Enquiry")) {
                mTransType = "BAP";
                linearAmount.setVisibility(View.INVISIBLE);
            } else if (selectedTransType.equals("Mini Statement")) {
                mTransType = "SAP";
                linearAmount.setVisibility(View.INVISIBLE);
            } else if (selectedTransType.equals("Aadhaar Pay")) {
                mTransType = "MZZ";
                linearAmount.setVisibility(View.VISIBLE);
            } else {
                mTransType = "";
            }

            if (mTransType.isEmpty()) {
                MyDialog.errorDialog(AepsRiseinActivity.this, MsgConst.SELECT_TRANSACTION_TYPE);
                return;
            }

            mCaptureAmount = edtxAmount.getText().toString().trim();
            if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("WAP")) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please " + MsgConst.AMOUNT_ENTER);
                return;
            } else if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please " + MsgConst.AMOUNT_ENTER);
                return;
            }

            if (!mCaptureAmount.isEmpty()) {
                int iCaptureAmount = Integer.parseInt(mCaptureAmount);
                if (iCaptureAmount <= 100) {
                    MyDialog.errorDialog(AepsRiseinActivity.this, " Input Amont must be greater than 100.");
                    return;
                }
                if (iCaptureAmount > 50000) {
                    MyDialog.errorDialog(AepsRiseinActivity.this, " Input Amont must be greater than 100 and less than 50000.");
                    return;
                }
            }


            if (mDeviceType.equals("Mantra MFS 100")) {
                try {
                    String pidOptionMantra = MsgConst.PID_OPTION_1;
                    if (pidOptionMantra != null) {
                        Intent intentMantra = new Intent();
                        intentMantra.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
                        intentMantra.setPackage("com.mantra.rdservice");
                        intentMantra.putExtra("PID_OPTIONS", pidOptionMantra);
                        startActivityForResult(intentMantra, requestCodeMantra);
                    } else {
                        boolean isAppInstall = appInstalledOrNot("com.mantra.rdservice");
                        if (isAppInstall) {
                            Log.i("Application is ", "already installed.");
                        } else {
                            // Redirect to play store
                            final String appPackageName = "com.mantra.rdservice";
                            try {
                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice&hl=en_IN")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice&hl=en_IN")));
                            }
                            return;
                        }
                    }
                } catch (Exception ex) {
                    //Log.e("Error", e.toString());
                    boolean isAppInstall = appInstalledOrNot("com.mantra.rdservice");
                    if (isAppInstall) {
                        Log.i("Application is ", "already installed.");
                    } else {
                        // Redirect to play store
                        final String appPackageName = "com.mantra.rdservice";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice&hl=en_IN")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mantra.rdservice&hl=en_IN")));
                        }
                        return;
                    }
                }
            } else if (mDeviceType.equals("Morpho MSO 1300")) {

                try {
                    String pidOptionMorpho = MsgConst.PID_OPTION_2;
                    if (pidOptionMorpho != null) {
                        Intent intentMorpho = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
                        intentMorpho.setPackage("com.scl.rdservice");
                        intentMorpho.putExtra("PID_OPTIONS", pidOptionMorpho);
                        startActivityForResult(intentMorpho, requestCodeMorpho);

                    } else {
                        boolean isAppInstall = appInstalledOrNot("com.scl.rdservice");
                        if (isAppInstall) {
                            Log.i("Application is ", "already installed.");
                        } else {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice&hl=en_IN")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice&hl=en_IN")));
                            }
                            return;
                        }
                    }
                } catch (Exception ex) {
                    boolean isAppInstall = appInstalledOrNot("com.scl.rdservice");
                    if (isAppInstall) {
                        Log.i("Application is ", "already installed.");
                    } else {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice&hl=en_IN")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.scl.rdservice&hl=en_IN")));
                        }
                        return;
                    }
                }
            } else if (mDeviceType.equals("Startek FM220U")) {
                try {
                    String pidOptionStartek = MsgConst.PID_OPTION_3;
                    if (pidOptionStartek != null) {
                        Intent intentMorpho = new Intent("in.gov.uidai.rdservice.fp.CAPTURE");
                        intentMorpho.setPackage("com.acpl.registersdk");
                        intentMorpho.putExtra("PID_OPTIONS", pidOptionStartek);
                        startActivityForResult(intentMorpho, requestCodeStartek);
                    } else {
                        boolean isAppInstall = appInstalledOrNot("com.acpl.registersdk");
                        if (isAppInstall) {
                            Log.i("Application is ", "already installed.");
                        } else {
                            //Redirect to Play Store
                            final String appPackageName = "com.acpl.registersdk";
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk&hl=en_IN")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk&hl=en_IN")));
                            }
                            return;
                        }
                    }
                } catch (Exception ex) {
                    //Log.e("Error", e.toString());
                    boolean isAppInstall = appInstalledOrNot("com.acpl.registersdk");
                    if (isAppInstall) {
                        Log.i("Application is ", "already installed.");
                    } else {
                        // Redirect to play store
                        final String appPackageName = "com.acpl.registersdk";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk&hl=en_IN")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.acpl.registersdk&hl=en_IN")));
                        }
                        return;
                    }
                }
            } else {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please Choose Biometric Device.");
                return;
            }

        }
    }


    //private void sendInstaTransaction(String userId, String latitude, String longitude, String capturedXmlData, String submitAadharNo, String bankID, String submitMobileNo, String submitAmount) {
    private void sendInstaTransaction(String bankiIn, String aadhaarUID, String mobileNo, String transType, String submitAmount, String biometricData) {

        //Dialog dialog = MyDialog.getFullWaitDialog(AepsRiseinActivity.this);
        MyUtils.showProgressDialog(AepsRiseinActivity.this, "Please wait..", false);

        // if (!dialog.isShowing()) {
// MyDialog.show(dialog);
// }

        if (submitAmount.isEmpty() || submitAmount.equalsIgnoreCase("")) {
            submitAmount = "0";
        }

        LinkedHashMap<String, String> paramsCaptured = new LinkedHashMap<>();
        paramsCaptured.put("api_access_key", mApiAccessKey);
        paramsCaptured.put("outletid", mOutletId);
        paramsCaptured.put("pan_no", mPanNo);
        String mLatitude = this.getLocation.getLatitude() + "";
        paramsCaptured.put("latitude", mLatitude);
        String mLongitude = this.getLocation.getLongitude() + "";
        paramsCaptured.put("longitude", mLongitude);

        Log.e("latitude", this.getLocation.getLatitude() + "");
        Log.e("longitude", this.getLocation.getLongitude() + "");

        paramsCaptured.put("bankiin", bankiIn);
        paramsCaptured.put("aadhaar_uid", aadhaarUID);
        paramsCaptured.put("mobile", mobileNo);
        paramsCaptured.put("trans_type", transType);
        paramsCaptured.put("amount", submitAmount);
        String mCheckSumString = "api_access_key=" + mApiAccessKey + "&" + "outletid=" + mOutletId + "&" + "pan_no=" + mPanNo + "&" + "latitude=" + mLatitude + "&" + "longitude=" + mLongitude + "&" + "bankiin=" + bankiIn + "&" + "aadhaar_uid=" + aadhaarUID + "&" + "mobile=" + mobileNo + "&" + "trans_type=" + transType + "&" + "amount=" + submitAmount;
        Log.e("Checksum String", mCheckSumString);
        //Log.e("Checksum String", MyUtils.formatQueryParams(paramsCaptured).trim());
        String myChecksum = MyUtils.generateHashWithHmac256(mCheckSumString, MsgConst.CHECKSUM_KEY);
        //String myChecksum = MyUtils.generateHashWithHmac256(MyUtils.formatQueryParams(paramsCaptured).trim(), MsgConst.CHECKSUM_KEY);
        paramsCaptured.put("checksum", myChecksum);
        //Log.e("checksum", myChecksum);
        paramsCaptured.put("biometricdata", biometricData);

        if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("WAP")) {

            disposable.add(apiServiceAeps.getAepsWithdrawal(paramsCaptured).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<WithdrawalAepsModel>() {
                        @Override
                        public void onSuccess(@NotNull WithdrawalAepsModel response) {
                            try {

                                MyUtils.hideProgressDialog();

                                if (response.getErrState() == 0) {
                                    showWithdrawalDialog(response);
                                } else if (response.getErrState() == 1) {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                }
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            try {
                                MyUtils.hideProgressDialog();
                                showResponseDialog("Please try again, Server not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                    }));
        } else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {

            disposable.add(apiServiceAeps.getAepsWithdrawal(paramsCaptured).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<WithdrawalAepsModel>() {
                        @Override
                        public void onSuccess(@NotNull WithdrawalAepsModel response) {
                            try {
                                MyUtils.hideProgressDialog();
                                if (response.getErrState() == 0) {
                                    showWithdrawalDialog(response);
                                } else if (response.getErrState() == 1) {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                }

                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                MyUtils.hideProgressDialog();
                                showResponseDialog("Please try again, Server not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                    }));
        } else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("SAP")) { //Mini Statement

            disposable.add(apiServiceAeps.getAepsMiniStatement(paramsCaptured).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<MiniStmntAepsModel>() {
                        @Override
                        public void onSuccess(@NotNull MiniStmntAepsModel response) {
                            try {
                                MyUtils.hideProgressDialog();
                                if (response.getErrState() == 0) {
                                    showMiniStatementDialog(response);
                                } else if (response.getErrState() == 1) {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                }
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                MyUtils.hideProgressDialog();
                                showResponseDialog("Please try again, Server is not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                    }));
        } else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("BAP")) {
            disposable.add(apiServiceAeps.getAepsInstaBalance(paramsCaptured).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<BalanceAepsModel>() {
                        @Override
                        public void onSuccess(@NotNull BalanceAepsModel response) {
                            try {
                                MyUtils.hideProgressDialog();
                                if (response.getErrState() == 0) {
                                    if (response.getRes().getData().getBalance().isEmpty()) {
                                        showResponseDialog("Message :" + response.getMsg() + "\n\n" + "Balance Amount : \u20B9 0.0" + "\n\n");
                                    } else {
                                        showResponseDialog("Message :" + response.getMsg() + "\n\n" + "Balance Amount : \u20B9 " + response.getRes().getData().getBalance() + "\n\n");
                                    }
                                } else if (response.getErrState() == 1) {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMsg() + "\n\n");
                                }
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                MyUtils.hideProgressDialog();
                                showResponseDialog("Please try again, Server not responding..." + e.getMessage());
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                    }));

        } else if (mTransType.isEmpty() || mTransType.equalsIgnoreCase("")) {
            MyDialog.errorDialog(AepsRiseinActivity.this, "Please select transaction type.");
            return;

        }
    }


    private void showResponseDialog(String messageBalance) {
        final Dialog dialog = new Dialog(AepsRiseinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_aeps_balance);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        TextView text = dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(messageBalance + ""));

        (dialog.findViewById(R.id.btn_ok)).setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showWithdrawalDialog(WithdrawalAepsModel mAepsWithdrawal) {
        try {
            TextView mAepsTransType, mAepsTransId, mAepsAmount, mAepsBalance, mAepsAccountNo, mAepsMessage,
                    mAepsStatus;

            final Dialog dialog = new Dialog(AepsRiseinActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_aeps_withdrawal);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            mAepsTransType = dialog.findViewById(R.id.idAepsTransType);
            mAepsTransType.setText("Withdrawal");
            mAepsTransId = dialog.findViewById(R.id.idAepsTransId);

            //String mTransID = mAepsWithdrawal.getData().getTranId() + "";
            String mTransID = mAepsWithdrawal.getRes().getData().getTranId() + "";
            if (!mTransID.isEmpty() && mTransID.equalsIgnoreCase("null"))
                mAepsTransId.setText("Transaction Id : " + mTransID);

            mAepsAmount = dialog.findViewById(R.id.idAepsAmount);
            mAepsAmount.setText("Withdraw Amount : \u20B9 " + mAepsWithdrawal.getRes().getData().getAmount() + "");
            mAepsBalance = dialog.findViewById(R.id.idAepsBalance);
            mAepsBalance.setText("Balance Amount : \u20B9 " + mAepsWithdrawal.getRes().getData().getBalance() + "");
            mAepsAccountNo = dialog.findViewById(R.id.idAepsAccountNo);
            mAepsAccountNo.setText("Account No. : " + mAepsWithdrawal.getRes().getData().getAccountNo() + "");
            mAepsMessage = dialog.findViewById(R.id.idAepsMessage);
            mAepsMessage.setText("Message : " + mAepsWithdrawal.getRes().getData().getTranId() + "");
            mAepsStatus = dialog.findViewById(R.id.idAepsStatus);
            mAepsStatus.setText("Status : " + mAepsWithdrawal.getRes().getData().getStatus() + "");

            (dialog.findViewById(R.id.idAepsCloseImg)).setOnClickListener(view -> {
                        finish();
                        dialog.dismiss();
                    }
            );
            dialog.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            MyDialog.errorDialog(AepsRiseinActivity.this, "Withdrawal Exception :" + ex.getMessage() + "\n\n");

        }

    }

    @SuppressLint("SetTextI18n")
    private void showMiniStatementDialog(MiniStmntAepsModel mAepsMiniStmnt) {
        try {
            TextView mAepsTransType, mAepsTransId, mAepsBalance, mAepsAccountNo, mAepsMessage, mAepsStatus;
            RecyclerView mAepsRecycler;

            final Dialog dialogMiniStatement = new Dialog(AepsRiseinActivity.this);
            dialogMiniStatement.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogMiniStatement.setContentView(R.layout.dialog_aeps_ministmt);

            if (dialogMiniStatement.getWindow() != null) {
                dialogMiniStatement.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            mAepsTransType = dialogMiniStatement.findViewById(R.id.idTransTypeMiniStmt);
            mAepsTransType.setText("Mini Statement");

            mAepsMessage = dialogMiniStatement.findViewById(R.id.idMessageMiniStmt);
            mAepsMessage.setText("Message : " + mAepsMiniStmnt.getMsg() + "");

            mAepsTransId = dialogMiniStatement.findViewById(R.id.idTransIdMiniStmt);
            mAepsTransId.setText("Transaction Id : " + mAepsMiniStmnt.getRes().getData().getTranId() + "");

            mAepsBalance = dialogMiniStatement.findViewById(R.id.idBalanceMiniStmt);
            mAepsBalance.setText("Balance Amount : \u20B9 " + mAepsMiniStmnt.getRes().getData().getBalance() + "");

            mAepsAccountNo = dialogMiniStatement.findViewById(R.id.idAccountNoMiniStmt);
            mAepsAccountNo.setText("Account No. : " + mAepsMiniStmnt.getRes().getData().getAccountNo() + "");

            mAepsStatus = dialogMiniStatement.findViewById(R.id.idStatusMiniStmt);
            mAepsStatus.setText("Status : " + mAepsMiniStmnt.getRes().getData().getStatus() + "");


            mAepsRecycler = dialogMiniStatement.findViewById(R.id.idRecyclerMiniStmt);
            mAepsRecycler.setHasFixedSize(true);
            mAepsRecycler.setLayoutManager(new LinearLayoutManager(this));

            // List<AepsMiniStmntModel.Data.MiniStatement> mMiniStatementList = new AepsMiniStmntModel().getData().getMiniStatement();


            AepsMiniStmtAdapter mAepsMiniStmtAdapter = new AepsMiniStmtAdapter(mAepsMiniStmnt.getRes().getData().getMiniStatement(), getApplicationContext());
            mAepsRecycler.setAdapter(mAepsMiniStmtAdapter);


            (dialogMiniStatement.findViewById(R.id.idAepsCloseImg)).setOnClickListener(view -> {
                finish();
                dialogMiniStatement.dismiss();
            });
            dialogMiniStatement.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            MyDialog.errorDialog(AepsRiseinActivity.this, "Mini Statement Exception :" + ex.getMessage() + "\n\n");
        }
    }

    private void dialogGoToHome(String messageResponse) {

        final Dialog dialog = new Dialog(AepsRiseinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_aeps_balance);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        TextView text = dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(messageResponse + ""));

        (dialog.findViewById(R.id.btn_ok)).setOnClickListener(view -> {
            finish();
            dialog.dismiss();
        });
        dialog.show();
    }


    void loadBankList() {
        MyUtils.showProgressDialog(AepsRiseinActivity.this, "Please wait..", false);
// Dialog dialog = MyDialog.getFullWaitDialog(AepsRiseinActivity.this);
// if (!dialog.isShowing()) {
// MyDialog.show(dialog);
// }

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        //params.put("token", userToken);
        params.put("api_mode", "AadhaarPay_bank_list");

// String myChecksum = MyUtils.generateHashWithHmac256(MyUtils.formatQueryParams(params).trim(), MsgConst.CHECKSUM_KEY.trim());
// params.put("checksum", myChecksum);

        disposable.add(apiServiceAeps.getAepsInstaBankList(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BankListAepsModel>() {
                    @Override
                    public void onSuccess(@NotNull BankListAepsModel response) {
                        try {
                            MyUtils.hideProgressDialog();
                            if (response.getErrState() == 0) {
                                processBankList(response);
                            } else {
                                MyDialog.errorDialog(AepsRiseinActivity.this, response.getMsg());
                            }
                            return;
                        } catch (Exception ex) {
                            Log.e("Server Exception", "" + ex);
                        }

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        try {
                            MyUtils.hideProgressDialog();
                            MyDialog.errorDialog(AepsRiseinActivity.this, e.getMessage());
                        } catch (Exception ex) {
                            Log.e("Server Exception", "" + ex);
                        }

                    }
                }));
    }


    void processBankList(BankListAepsModel bankList) {



        BankListAepsModel.DATum item = null;
        for (int index = 0; index < bankList.getData().size(); ++index) {
            item = new BankListAepsModel.DATum();
            item.setBankName(bankList.getData().get(index).getBankName());
            item.setBankiin(bankList.getData().get(index).getBankiin());
            bankDataList.add(item);
            mBankNameList.add((bankDataList.get(index)).getBankName());
        }

        ArrayAdapter<String> bankNameAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, mBankNameList);
        bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//17367049
        bankNameSpinner.setAdapter(bankNameAdapter);
        bankNameAdapter.notifyDataSetChanged();

    }


    public static String getRandomTrnasID() {
        Random random = new SecureRandom();
        int CODE_LENGTH = 16;
        final String letters = "0123AbcDE456789";

        String trnasID = "";
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = (int) (random.nextDouble() * letters.length());
            trnasID += letters.substring(index, index + 1);
        }
        return trnasID;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            Bundle mrBundle = data.getExtras();
                            if (mrBundle != null) {
                                String resultPidData = mrBundle.getString("PID_DATA"); // in this varaible you will get Pid data
                                Log.e("BiometricData 1", resultPidData);
                                mBiometricData = resultPidData;
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                        // jsonObj = XML.toJSONObject(resultPidData);
                                          XmlToJson xmlToJson = new XmlToJson.Builder(resultPidData).build();
                                        // convert to a JSONObject
                                          jsonObj = xmlToJson.toJson();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    // Log.e("CapturedJSONMr", jsonObj.toString());
                                    JSONObject mrPidDataObject = jsonObj.getJSONObject("PidData");
                                    // Log.e("PidData", mrPidDataObject.toString());
                                    JSONObject mrRespObject = mrPidDataObject.getJSONObject("Resp");

                                    String mErrCode = mrRespObject.getString("errCode");
                                    //sErrCode = mErrCode;

                                    String mErrInfo = "";
                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        mErrInfo = "Capture Success";

                                    } else {
                                        mErrInfo = mrRespObject.getString("errInfo");
                                    }
                                    //sErrInfo = mErrInfo;

                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        dialogAadhaarCaptureStatus("Capture Success");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("SafetyNet Integrity not passed so please refresh RD Service manually.")) {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("Device not ready")) {
                                        dialogAadhaarCaptureStatus(mErrInfo + "");
                                    } else {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                        return;
                                    }

                                } catch (JSONException var10) {
                                    MyDialog.errorDialog(AepsRiseinActivity.this, var10.getMessage());
                                }
                            }


                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze pid data", e);
                    }
                }
                break;


            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            Bundle mrBundle = data.getExtras();
                            if (mrBundle != null) {
                                String resultPidData = mrBundle.getString("PID_DATA"); // in this varaible you will get Pid data
                                Log.e("BiometricData 2", resultPidData);
                                mBiometricData = resultPidData;
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                         //jsonObj = XML.toJSONObject(resultPidData);
                                          XmlToJson xmlToJson = new XmlToJson.Builder(resultPidData).build();
                                        // convert to a JSONObject
                                          jsonObj = xmlToJson.toJson();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    // Log.e("CapturedJSONMr", jsonObj.toString());
                                    JSONObject mrPidDataObject = jsonObj.getJSONObject("PidData");
                                    // Log.e("PidData", mrPidDataObject.toString());
                                    JSONObject mrRespObject = mrPidDataObject.getJSONObject("Resp");

                                    String mErrCode = mrRespObject.getString("errCode");
                                    //sErrCode = mErrCode;

                                    String mErrInfo = "";
                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        mErrInfo = "Capture Success";

                                    } else {
                                        mErrInfo = mrRespObject.getString("errInfo");
                                    }
                                    //sErrInfo = mErrInfo;

                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        dialogAadhaarCaptureStatus("Capture Success");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("SafetyNet Integrity not passed so please refresh RD Service manually.")) {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("Device not ready")) {
                                        dialogAadhaarCaptureStatus(mErrInfo + "");
                                    } else {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                        return;
                                    }

                                } catch (JSONException var10) {
                                    MyDialog.errorDialog(AepsRiseinActivity.this, var10.getMessage());
                                }
                            }


                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze pid data", e);
                    }
                }
                break;


            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {

                            Bundle mrBundle = data.getExtras();
                            if (mrBundle != null) {
                                String resultPidData = mrBundle.getString("PID_DATA"); // in this varaible you will get Pid data
                                Log.e("BiometricData 3", resultPidData);
                                mBiometricData = resultPidData;
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                         //jsonObj = XML.toJSONObject(resultPidData);
                                          XmlToJson xmlToJson = new XmlToJson.Builder(resultPidData).build();
                                        // convert to a JSONObject
                                        jsonObj = xmlToJson.toJson();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    // Log.e("CapturedJSONMr", jsonObj.toString());
                                    JSONObject mrPidDataObject = jsonObj.getJSONObject("PidData");
                                    // Log.e("PidData", mrPidDataObject.toString());
                                    JSONObject mrRespObject = mrPidDataObject.getJSONObject("Resp");

                                    String mErrCode = mrRespObject.getString("errCode");
                                    //sErrCode = mErrCode;

                                    String mErrInfo = "";
                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        mErrInfo = "Capture Success";

                                    } else {
                                        mErrInfo = mrRespObject.getString("errInfo");
                                    }
                                    //sErrInfo = mErrInfo;

                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        dialogAadhaarCaptureStatus("Capture Success");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("SafetyNet Integrity not passed so please refresh RD Service manually.")) {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                    } else if (!mErrInfo.isEmpty() && mErrInfo.equalsIgnoreCase("Device not ready")) {
                                        dialogAadhaarCaptureStatus(mErrInfo + "");
                                    } else {
                                        dialogAadhaarCaptureStatus("Device not ready.");
                                        return;
                                    }

                                } catch (JSONException var10) {
                                    MyDialog.errorDialog(AepsRiseinActivity.this, var10.getMessage());
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("Error", "Error while deserialze pid data", e);
                    }
                }
                break;




        }
    }


    private void dialogAadhaarCaptureStatus(String errInfo) {

        Dialog dialog = new Dialog(AepsRiseinActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_aadhaar_capture);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        ImageView mAadhaarOKImg = (ImageView) dialog.findViewById(R.id.aadhaarOK_Img);
        ImageView mAadhaarFailureImg = (ImageView) dialog.findViewById(R.id.aadhaarFailure_Img);
        Button mSubbmitAadhaarBtn = (Button) dialog.findViewById(R.id.subbmitAadhaarCaptureBtn);
        mSubbmitAadhaarBtn.setVisibility(View.GONE);
        mAadhaarOKImg.setVisibility(View.GONE);
        mAadhaarFailureImg.setVisibility(View.GONE);


        if (errInfo.equalsIgnoreCase("Capture Success")) {
            mSubbmitAadhaarBtn.setVisibility(View.VISIBLE);
            mAadhaarOKImg.setVisibility(View.VISIBLE);
            mAadhaarFailureImg.setVisibility(View.GONE);
        } else if (errInfo.equalsIgnoreCase("Device not ready.")) {
            mSubbmitAadhaarBtn.setVisibility(View.GONE);
            mAadhaarOKImg.setVisibility(View.GONE);
            mAadhaarFailureImg.setVisibility(View.VISIBLE);
        } else {
            mSubbmitAadhaarBtn.setVisibility(View.GONE);
            mAadhaarOKImg.setVisibility(View.GONE);
            mAadhaarFailureImg.setVisibility(View.VISIBLE);
        }

        String s = "";
        s = " " + errInfo;

        ((TextView) dialog.findViewById(R.id.aadhaarInfoStatus_txv)).setText(s);

        mSubbmitAadhaarBtn.setOnClickListener(v -> {

            dialog.dismiss();

            if (!MyUtils.isConnected(Objects.requireNonNull(AepsRiseinActivity.this))) {
                Toast.makeText(AepsRiseinActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mCaptureBankID.isEmpty()) {
                Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.BANK_NAME_SELECT, Toast.LENGTH_SHORT).show();
                return;
            }

            mCaptureAadharNo = edtxAdharNo.getText().toString().trim();
            if (mCaptureAadharNo.isEmpty()) {
                Toast.makeText(AepsRiseinActivity.this, "Please " + MsgConst.AADHAR, Toast.LENGTH_SHORT).show();
                return;
            }


            mCaptureMobileNo = edtxMobileNo.getText().toString().trim();
            if (mCaptureMobileNo.isEmpty()) {
                Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.MOBILE_ENTER, Toast.LENGTH_SHORT).show();
                return;
            }
            if (mTransType.isEmpty()) {
                Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.SELECT_TRANSACTION_TYPE, Toast.LENGTH_SHORT).show();
                return;
            }
            mCaptureAmount = edtxAmount.getText().toString().trim();
            if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("WAP")) {
                Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.AMOUNT_ENTER, Toast.LENGTH_SHORT).show();
                return;
            } else if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "Please " + MsgConst.AMOUNT_ENTER);
                return;
            }
            // sendInstaTransaction(mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount);
            //sendInstaTransaction(mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount, mLatitude, mLongitude, mUserId);
            //sendInstaTransaction("mUserId", "mLatitude", "mLongitude", "mCapturedXmlData", mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount );
            //sendInstaTransaction( "apiAccessKey", "outletId", "panNo", "latitude", "longitude", mCaptureBankID, mCaptureAadharNo, mCaptureMobileNo, mTransType, mCaptureAmount, "biometricData" ) ;
            if (mBiometricData.isEmpty()) {
                Toast.makeText(AepsRiseinActivity.this, MsgConst.CAPTURED_BIOMETRIC_DATA, Toast.LENGTH_SHORT).show();
                return;
            }
            location = "Address :" + this.getLocation.getAddress(this.getLocation.getLatitude(), this.getLocation.getLongitude()) + "/versionName:" + "" + "/versionCode:" + -1;
            if (location.length() == 0) {
                Toast.makeText(AepsRiseinActivity.this, "Sorry, Unable To fetch Location, Please Enable GPS Location in Mobile Setting.", Toast.LENGTH_SHORT).show();
                return;
            }

            sendInstaTransaction(mCaptureBankID, mCaptureAadharNo, mCaptureMobileNo, mTransType, mCaptureAmount, mBiometricData);
        });

        (dialog.findViewById(R.id.cancelAadhaarCaptureBtn)).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void showSettingsAlert() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(AepsRiseinActivity.this);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                initView();
//                if(mBankNameList==null) {
//                    loadBankList();
//                }
                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                AepsRiseinActivity.this.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                initView();
//                if(mBankNameList==null) {
//                    loadBankList();
//                }
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Closing AEPS");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Intent i = new Intent();
                //i.putExtra("message", "");
                //AepsRiseinActivity.super.setResult(0, i);
                AepsRiseinActivity.super.finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!AepsRiseinActivity.this.isFinishing()) {
                    dialog.dismiss();
                }

            }
        });
        builder.create().show();
    }

}
