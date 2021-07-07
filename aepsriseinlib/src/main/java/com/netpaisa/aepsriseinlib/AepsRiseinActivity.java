package com.netpaisa.aepsriseinlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.netpaisa.aepsriseinlib.adapter.AepsMiniStmtAdapter;
import com.netpaisa.aepsriseinlib.model.AepsBalanceModel;
import com.netpaisa.aepsriseinlib.model.AepsInstaBankModel;
import com.netpaisa.aepsriseinlib.model.AepsMiniStmntModel;
import com.netpaisa.aepsriseinlib.model.AepsWithdrawalModel;
//import com.prodevsblog.myutils.RecyclerDialog;
//import com.prodevsblog.myutils.RecyclerItem;
//import com.prodevsblog.myutils.Sort;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.text.Html.fromHtml;


public class AepsRiseinActivity extends AppCompatActivity implements View.OnClickListener {


    private Spinner spinnerTotalFingerCount, spinnerTotalFingerType, spinnerTotalFingerFormat, spinnerEnv;
    private LinearLayout linearFingerCount, linearFingerFormat, linearTimeoutPidVer, linearSelectPosition, linearAdharNo, linearAmount, activityMain, aepsInstaTransLayout;
    private EditText edtxAdharNo, edtxTimeOut, edtxPidVer, edtxMobileNo, edtxAmount;
    private TextView txtSelectPosition, txtOutput, txtDataLabel, txBankName, txTransType;

    private Spinner spnrTransType, spnrDeviceType;

    private Button btnDeviceInfo, btnCapture, btnSubmit, btnCaptureThumb, btnAuthRequest, btnReset;
    private CheckBox chbxUnknown, chbxLeftIndex, chbxLeftMiddle, chbxLeftRing, chbxLeftSmall, chbxLeftThumb, chbxRightIndex,
            chbxRightMiddle, chbxRightRing, chbxRightSmall, chbxRightThumb;

    private int fingerCount = 0;
    //private PidData pidData = null;
    //private Serializer serializer = null;
    private ArrayList<String> positions;

    public String jsonObjCaptured = null;
    private ApiServiceAeps apiServiceAeps;

    private CompositeDisposable disposable = new CompositeDisposable();

    //private List<RecyclerItem> bankList;
   // private RecyclerItem selectedBank;

    private String mSubmitAadharNo = "", mBankID = "", mSubmitMobileNo = "", mSubmitAmount = "0";

    private String mCaptureAadharNo = "", mCaptureBankID = "", mCaptureMobileNo = "", mCaptureAmount = "0";
    private String sHmac = "", sDpId = "", sRdsId = "", sRdsVer = "", sMi = "", sMc = "", sDc = "", sFCount = "",
            sFType = "", spType = "", sNmPoints = "", sICount = "", sPCount = "", sErrCode = "", sErrInfo = "", sQScore = "",
            sPidData = "", sData = "", sPidDataType = "", sPidDataContent = "", sSkey = "", sSkeyCi = "", sSrno = "", sEnv = "P", agent_id = "";

    private String selectedTransType = "", mTransType = "", mOutletIdAEPSInsta = "", mPanNoAEPSInsta = "";


    final String transTypeArray[] = {"Select Transaction Type", "Cash Withdrawal", "Balance Enquiry", "Mini Statement", "Aadhaar Pay"};


    final String deviceTypeArray[] = {"Choose Biometric Device", "Mantra MFS 100", "Morpho MSO 1300", "Startek FM220U"};


    private int requestCodeMantra = 2;
    private int requestCodeMorpho = 3;
    private int requestCodeStartek = 4;
   // private int requestCodeAadhaarImg = 5;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 11;

    private String selectedDeviceType = "", mDeviceType = "";

    //private Uri AEPS_AADHAR_IMAGE;
    //private ImageView mAadhaarSelectedImg;
    //private String agent_id;
    private String password;
    private String developer_id;
    private String user_id;
    private String clientTransactionId;
    private String bankVendorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeps_risein);

        Toolbar toolbar = findViewById(R.id.toolbarAeps);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        apiServiceAeps = ApiClientAeps.getClient(getApplicationContext()).create(ApiServiceAeps.class);

        ConnectivityCheckUtility connectivityCheckUtility = new ConnectivityCheckUtility(this);
        if (!connectivityCheckUtility.isInternetAvailable()) {
            Toast.makeText(this, "Please connect to Internet!", Toast.LENGTH_SHORT).show();
        }

        initView();

//        Intent mIntent = this.getIntent();
//        if (mIntent != null) {
//            if (mIntent.getStringExtra("clientTransactionId") == null) {
//                this.finish();
//            } else {
//                this.agent_id = mIntent.getStringExtra("agent_id");
//                this.developer_id = mIntent.getStringExtra("developer_id");
//                this.password = mIntent.getStringExtra("password");
//                this.clientTransactionId = mIntent.getStringExtra("clientTransactionId");
//                this.bankVendorType = mIntent.getStringExtra("bankVendorType");
//                if (this.clientTransactionId.length() >= 6 && this.clientTransactionId.length() <= 20) {
//                    MyUtils.PRIMARY_COLOR = mIntent.getIntExtra("primary_color", MyUtils.PRIMARY_COLOR);
//                    MyUtils.ACCENT_COLOR = mIntent.getIntExtra("accent_color", MyUtils.ACCENT_COLOR);
//                    MyUtils.PRIMARY_DARK_COLOR = mIntent.getIntExtra("primary_dark_color", MyUtils.PRIMARY_DARK_COLOR);
//                } else {
//
//                }
//            }
//        }

    }


    private void initView() {


        linearAdharNo = (LinearLayout) findViewById(R.id.linearAdharNo);
        linearAdharNo.setVisibility(View.VISIBLE);
        linearAmount = (LinearLayout) findViewById(R.id.linearAmount);
        activityMain = (LinearLayout) findViewById(R.id.activity_main);

        aepsInstaTransLayout = (LinearLayout) findViewById(R.id.aepsInstaTransLayout);
        aepsInstaTransLayout.setVisibility(View.VISIBLE);

        txBankName = (TextView) findViewById(R.id.txBankName);
        txBankName.setOnClickListener(this);
        edtxAdharNo = (EditText) findViewById(R.id.edtxAdharNo);
        edtxMobileNo = (EditText) findViewById(R.id.edtxMobileNo);
        edtxAmount = (EditText) findViewById(R.id.edtxAmount);

        btnCapture = (Button) findViewById(R.id.btnCapture);
        btnCapture.setOnClickListener(this);


        spnrTransType = findViewById(R.id.txTransType);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, transTypeArray);
        spnrTransType.setAdapter(spinnerArrayAdapter);

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


        spnrDeviceType = findViewById(R.id.txDeviceType);
        ArrayAdapter spinnerDeviceAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, deviceTypeArray);
        spnrDeviceType.setAdapter(spinnerDeviceAdapter);

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


        loadBankList();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadBankList();
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

        if (mView.getId() == R.id.txBankName) {
            showBankDialog();
        } else if (mView.getId() == R.id.btnCapture) {
            if (!MyUtils.isConnected(Objects.requireNonNull(AepsRiseinActivity.this))) {
                MyDialog.errorDialog(AepsRiseinActivity.this, "No Internet Connection");
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
            }else if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {
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


    private void sendInstaTransaction(String userId, String latitude, String longitude, String capturedXmlData,  String submitAadharNo, String bankID, String submitMobileNo, String submitAmount) {

        Dialog dialog = MyDialog.getFullWaitDialog(AepsRiseinActivity.this);

        if (!dialog.isShowing()) {
            MyDialog.show(dialog);
        }

        if (submitAmount.isEmpty() || submitAmount.equalsIgnoreCase("")) {
            submitAmount = "0";
        }

        LinkedHashMap<String, String> params = new LinkedHashMap<>();

       // params.put("token", userToken);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("user_agent", userId);

        params.put("agent_trid", getRandomTrnasID());
        params.put("bankiin", bankID);
        params.put("aadhaar_uid", submitAadharNo);
        params.put("mobile", submitMobileNo);
        params.put("amount", submitAmount);
        params.put("trans_type", mTransType);



//        params.put("pidDataType", sPidDataType);
//        params.put("pidData", sPidDataContent);
//        params.put("ci", sSkeyCi);
//        params.put("dc", sDc);
//        params.put("dpId", sDpId);
//        params.put("errCode", sErrCode);
//        params.put("errInfo", sErrInfo);
//        params.put("fCount", sFCount);
//        params.put("tType", "");
//        params.put("hmac", sHmac);
//        params.put("iCount", sICount);
//        params.put("mc", sMc);
//        params.put("mi", sMi);
//        params.put("nmPoints", sNmPoints);
//        params.put("pCount", sPCount);
//        params.put("pType", spType);
//        params.put("qScore", sQScore);
//        params.put("rdsId", sRdsId);
//        params.put("rdsVer", sRdsVer);
//        params.put("sessionKey", sSkey);
//        params.put("srno", sSrno);
        //Log.e("Request Params :", ""+params);

        if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("WAP")) {

            disposable.add(apiServiceAeps.getAepsWithdrawal(params).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AepsWithdrawalModel>() {
                        @Override
                        public void onSuccess(@NotNull AepsWithdrawalModel response) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                if (response.getErrorCode() == 0) {
                                    showWithdrawalDialog(response);
                                } else if (response.getErrorCode() == 1) {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                }

                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                showResponseDialog("Please try again, Server not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                    }));
        } else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {

            disposable.add(apiServiceAeps.getAepsWithdrawal(params).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AepsWithdrawalModel>() {
                        @Override
                        public void onSuccess(@NotNull AepsWithdrawalModel response) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                if (response.getErrorCode() == 0) {
                                    showWithdrawalDialog(response);
                                } else if (response.getErrorCode() == 1) {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                }

                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                showResponseDialog("Please try again, Server not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }
                    }));
        }

        else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("SAP")) { //Mini Statement

            disposable.add(apiServiceAeps.getAepsMiniStatement(params).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AepsMiniStmntModel>() {
                        @Override
                        public void onSuccess(@NotNull AepsMiniStmntModel response) {
                            try {

                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }

                                if (response.getErrorCode() == 0) {
                                    showMiniStatementDialog(response);
                                } else if (response.getErrorCode() == 1) {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                }
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                showResponseDialog("Please try again, Server is not responding..." + e.getMessage() + "");
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }
                        }

                    }));
        } else if (!mTransType.isEmpty() && mTransType.equalsIgnoreCase("BAP")) {
            disposable.add(apiServiceAeps.getAepsInstaBalance(params).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<AepsBalanceModel>() {
                        @Override
                        public void onSuccess(@NotNull AepsBalanceModel response) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
                                if (response.getErrorCode() == 0) {
                                    if (response.getBalance().isEmpty()) {
                                        showResponseDialog("Message :" + response.getMessage() + "\n\n" + "Balance Amount : \u20B9 0.0" + "\n\n");
                                    } else {
                                        showResponseDialog("Message :" + response.getMessage() + "\n\n" + "Balance Amount : \u20B9 " + response.getBalance() + "\n\n");
                                    }
                                } else if (response.getErrorCode() == 1) {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                } else {
                                    showResponseDialog("Message :" + response.getMessage() + "\n\n");
                                }
                            } catch (Exception ex) {
                                Log.e("Server Exception", "" + ex);
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                                if (dialog.isShowing()) {
                                    MyDialog.exit(dialog);
                                }
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
            //finish();
            dialog.dismiss();
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showWithdrawalDialog(AepsWithdrawalModel mAepsWithdrawal) {

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

            String mTransID = mAepsWithdrawal.getData().getTranId() + "";
            if (!mTransID.isEmpty() && mTransID.equalsIgnoreCase("null"))
                mAepsTransId.setText("Transaction Id : " + mTransID);

            mAepsAmount = dialog.findViewById(R.id.idAepsAmount);
            mAepsAmount.setText("Withdraw Amount : \u20B9 " + mAepsWithdrawal.getData().getAmount() + "");
            mAepsBalance = dialog.findViewById(R.id.idAepsBalance);
            mAepsBalance.setText("Balance Amount : \u20B9 " + mAepsWithdrawal.getData().getBalance() + "");
            mAepsAccountNo = dialog.findViewById(R.id.idAepsAccountNo);
            mAepsAccountNo.setText("Account No. : " + mAepsWithdrawal.getData().getAccountNo() + "");
            mAepsMessage = dialog.findViewById(R.id.idAepsMessage);
            mAepsMessage.setText("Message  : " + mAepsWithdrawal.getData().getTranId() + "");
            mAepsStatus = dialog.findViewById(R.id.idAepsStatus);
            mAepsStatus.setText("Status  : " + mAepsWithdrawal.getData().getStatus() + "");

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
    private void showMiniStatementDialog(AepsMiniStmntModel mAepsMiniStmnt) {
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
            mAepsMessage.setText("Message  : " + mAepsMiniStmnt.getMessage() + "");

            mAepsTransId = dialogMiniStatement.findViewById(R.id.idTransIdMiniStmt);
            mAepsTransId.setText("Transaction Id : " + mAepsMiniStmnt.getData().getTranId() + "");

            mAepsBalance = dialogMiniStatement.findViewById(R.id.idBalanceMiniStmt);
            mAepsBalance.setText("Balance Amount : \u20B9 " + mAepsMiniStmnt.getData().getBalance() + "");

            mAepsAccountNo = dialogMiniStatement.findViewById(R.id.idAccountNoMiniStmt);
            mAepsAccountNo.setText("Account No. : " + mAepsMiniStmnt.getData().getAccountNo() + "");

            mAepsStatus = dialogMiniStatement.findViewById(R.id.idStatusMiniStmt);
            mAepsStatus.setText("Status  : " + mAepsMiniStmnt.getData().getStatus() + "");


            mAepsRecycler = dialogMiniStatement.findViewById(R.id.idRecyclerMiniStmt);
            mAepsRecycler.setHasFixedSize(true);
            mAepsRecycler.setLayoutManager(new LinearLayoutManager(this));

            // List<AepsMiniStmntModel.Data.MiniStatement> mMiniStatementList = new AepsMiniStmntModel().getData().getMiniStatement();


            AepsMiniStmtAdapter mAepsMiniStmtAdapter = new AepsMiniStmtAdapter(mAepsMiniStmnt.getData().getMiniStatement(), getApplicationContext());
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

    private void dialogResponseToHome(String messageResponse) {

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

        Dialog dialog = MyDialog.getFullWaitDialog(AepsRiseinActivity.this);
        if (!dialog.isShowing()) {
            MyDialog.show(dialog);
        }

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        //params.put("token", userToken);
        params.put("api_mode", "AadhaarPay_bank_list");

        String myChecksum = MyUtils.generateHashWithHmac256(MyUtils.formatQueryParams(params).trim(), MsgConst.CHECKSUM_KEY.trim());
        params.put("checksum", myChecksum);

        disposable.add(apiServiceAeps.getAepsInstaBankList(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AepsInstaBankModel>() {
                    @Override
                    public void onSuccess(@NotNull AepsInstaBankModel response) {
                        try {
                            MyDialog.exit(dialog);
                            if (response.getErrorCode() == 0) {
                                processBankList(response);
                            } else {
                                MyDialog.errorDialog(AepsRiseinActivity.this, response.getMessage());
                            }
                            return;
                        } catch (Exception ex) {
                            Log.e("Server Exception", "" + ex);
                        }

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        try {
                            MyDialog.exit(dialog);
                            MyDialog.errorDialog(AepsRiseinActivity.this, e.getMessage());
                        } catch (Exception ex) {
                            Log.e("Server Exception", "" + ex);
                        }

                    }
                }));
    }

    private void showBankDialog() {
        //if (bankList != null && bankList.size() > 0) {

//            RecyclerDialog recyclerDialog = new RecyclerDialog(Objects.requireNonNull(AepsRiseinActivity.this), bankList, true);
//            recyclerDialog.show();
//            recyclerDialog.setSortList(Sort.FIRST_TEXT_VALUE, true);
//            recyclerDialog.setCamelCase(false);
//            recyclerDialog.setCancelable(false);
//            recyclerDialog.setTitle(MsgConst.SELECT_BANK);
//            recyclerDialog.setOnItemClickListener((view, obj, position) -> {
//                this.selectedBank = obj;
//                txBankName.setText(obj.getFirstTextValue());
//                //bankName = obj.getFirstTextValue().toUpperCase();
//                //mBankID = obj.getSecondTextValue();
//                mCaptureBankID = obj.getStringId();
//            });
//            recyclerDialog.getScroller().setBubbleColor(0xff8f93d1);
//            recyclerDialog.getScroller().setHandleColor(0xff5e64ce);
       // }
    }

    void processBankList(AepsInstaBankModel bank) {
       // bankList = new ArrayList<>();

//        RecyclerItem recyclerItem;
//        for (AepsInstaBankModel.DATum item : bank.getData()) {
//            recyclerItem = new RecyclerItem();
//            recyclerItem.setFirstTextValue(item.getBankName());
//            recyclerItem.setStringId(item.getBankiin());
//            bankList.add(recyclerItem);
//        }
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


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AepsRiseinActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
            }
        }
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
                                String resultPidData = mrBundle.getString("PID_DATA");  // in this varaible you will get Pid data
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                        jsonObj = XML.toJSONObject(resultPidData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    //  Log.e("CapturedJSONMr", jsonObj.toString());
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
                                   // sErrInfo = mErrInfo;

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
                        Log.e("Error", "Error while deserialze device info", e);
                    }
                }
                break;


            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {
                            Bundle mrBundle = data.getExtras();
                            if (mrBundle != null) {
                                String resultPidData = mrBundle.getString("PID_DATA");  // in this varaible you will get Pid data
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                        jsonObj = XML.toJSONObject(resultPidData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    //  Log.e("CapturedJSONMr", jsonObj.toString());
                                    JSONObject mrPidDataObject = jsonObj.getJSONObject("PidData");
                                    // Log.e("PidData", mrPidDataObject.toString());
                                    JSONObject mrRespObject = mrPidDataObject.getJSONObject("Resp");

                                    String mErrCode = mrRespObject.getString("errCode");
                                    sErrCode = mErrCode;

                                    String mErrInfo = "";
                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        mErrInfo = "Capture Success";

                                    } else {
                                        mErrInfo = mrRespObject.getString("errInfo");
                                    }
                                    sErrInfo = mErrInfo;

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
                                String resultPidData = mrBundle.getString("PID_DATA");  // in this varaible you will get Pid data
                                JSONObject jsonObj = null;
                                try {
                                    try {
                                        jsonObj = XML.toJSONObject(resultPidData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    //  Log.e("CapturedJSONMr", jsonObj.toString());
                                    JSONObject mrPidDataObject = jsonObj.getJSONObject("PidData");
                                    // Log.e("PidData", mrPidDataObject.toString());
                                    JSONObject mrRespObject = mrPidDataObject.getJSONObject("Resp");

                                    String mErrCode = mrRespObject.getString("errCode");
                                    sErrCode = mErrCode;

                                    String mErrInfo = "";
                                    if (mErrCode.equalsIgnoreCase("0")) {
                                        mErrInfo = "Capture Success";

                                    } else {
                                        mErrInfo = mrRespObject.getString("errInfo");
                                    }
                                    sErrInfo = mErrInfo;

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


            case 4:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (data != null) {

                Bundle strBundle = data.getExtras();
                if (strBundle != null) {
                    String PidDataResult = strBundle.getString("PID_DATA");  // in this varaible you will get Pid data
                    JSONObject jsonObj = null;
                    try {
                        try {
                            jsonObj = XML.toJSONObject(PidDataResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        Log.e("CapturedJSONstr", jsonObj.toString());

                        JSONObject strPidDataObject = jsonObj.getJSONObject("PidData");
                        //sPidData = strPidDataObject.toString();
                        //Log.e("PidData", strPidDataObject.toString());
                        JSONObject strRespObject = strPidDataObject.getJSONObject("Resp");

                        String mErrCode = strRespObject.getString("errCode");
                        //sErrCode = mErrCode;

                        String mErrInfo = "";
                        if (mErrCode.equalsIgnoreCase("0")) {
                            mErrInfo = "Capture Success";

                        } else {
                            mErrInfo = strRespObject.getString("errInfo");
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


    private void dialogAadhaarCaptureStatus (String errInfo){

            Dialog dialog = new Dialog(this);
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
            s = "  " + errInfo;

            ((TextView) dialog.findViewById(R.id.aadhaarInfoStatus_txv)).setText(s);

            mSubbmitAadhaarBtn.setOnClickListener(v -> {
                dialog.dismiss();

                if (!MyUtils.isConnected(Objects.requireNonNull(AepsRiseinActivity.this))) {
                    Toast.makeText(AepsRiseinActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }

                mCaptureAadharNo = edtxAdharNo.getText().toString().trim();
                if (mCaptureAadharNo.isEmpty()) {
                    Toast.makeText(AepsRiseinActivity.this, "Please " + MsgConst.AADHAR, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mCaptureBankID.isEmpty()) {
                    Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.BANK_NAME_SELECT, Toast.LENGTH_SHORT).show();
                    return;
                }

                mCaptureMobileNo = edtxMobileNo.getText().toString().trim();
                if (mCaptureMobileNo.isEmpty()) {
                    Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.MOBILE_ENTER, Toast.LENGTH_SHORT).show();
                    return;
                }

                mCaptureAmount = edtxAmount.getText().toString().trim();
                if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("WAP")) {
                    Toast.makeText(AepsRiseinActivity.this, "" + MsgConst.AMOUNT_ENTER, Toast.LENGTH_SHORT).show();
                    return;
                }else if (mCaptureAmount.isEmpty() && mTransType.equalsIgnoreCase("MZZ")) {
                    MyDialog.errorDialog(AepsRiseinActivity.this, "Please " + MsgConst.AMOUNT_ENTER);
                    return;
                }
                // sendInstaTransaction(mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount);
                //sendInstaTransaction(mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount, mLatitude, mLongitude, mUserId);
                sendInstaTransaction("mUserId", "mLatitude", "mLongitude", "mCapturedXmlData", mCaptureAadharNo, mCaptureBankID, mCaptureMobileNo, mCaptureAmount  );

            });

            (dialog.findViewById(R.id.cancelAadhaarCaptureBtn)).setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        }



}
