package com.netpaisa.aepsriseinlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.JsonObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class MyUtils {

    private static ProgressDialog mProgressDialog;
    public static int PRIMARY_COLOR;
    public static int ACCENT_COLOR;
    public static int PRIMARY_DARK_COLOR;

    public MyUtils() {
    }

    public static Boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    @SuppressLint("NewApi")
    public static String formatQueryParams(Map<String, String> params) {
        return params.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

    @SuppressLint("NewApi")
    public static String formatQueryJsonParams(JsonObject params) {
        return params.entrySet().stream()
                .map(p -> p.getKey() + "=" + p.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

    public static String generateHashWithHmac256(String message, String key) {
        String value="";
        try {
             final String hashingAlgorithm = "HmacSHA256";
            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());
            final String messageDigest = bytesToHex(bytes);
            value=messageDigest;
            //Log.i("TAG", "message digest: " + messageDigest);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = MsgConst.HEXTOBYTE.toCharArray();

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static ProgressDialog showProgressBar(Context context) {
        ProgressDialog p = new ProgressDialog(context);
        p.setMessage("Loading...");
        p.setCancelable(false);
        return p;
    }

    public static void showProgressDialog(final Activity mActivity, final String msg, final boolean isCancelable) {
        try {
            if (mActivity != null && mProgressDialog != null && mProgressDialog.isShowing()) {
                try {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }

            mProgressDialog = null;
            if (mProgressDialog == null && mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        MyUtils.mProgressDialog = new ProgressDialog(mActivity);
                        MyUtils.mProgressDialog.setMessage(msg);
                        MyUtils.mProgressDialog.setCancelable(isCancelable);
                    }
                });
            }

            if (mActivity != null && mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }

    }

    public static String createMultipleTransactionID() {
        String AgentTranID = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSS");
            Date date = new Date();
            String tranID = sdf.format(date);
            int n = 6;
            Random randGen = new Random();
            int startNum = (int)Math.pow(10.0D, (double)(n - 1));
            int range = (int)(Math.pow(10.0D, (double)n) - (double)startNum);
            int randomNum = randGen.nextInt(range) + startNum;
            String ran = String.valueOf(randomNum);
            AgentTranID = tranID + ran;
        } catch (Throwable var10) {
        }

        return AgentTranID;
    }

    public static boolean checkEmail(String email) {
        boolean valid = false;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkPanCard(String text) {
        Pattern p = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]{1}$");
        Matcher m = p.matcher(text);
        return m.find() && m.group().equals(text);
    }

    public static void hideKeyboard(Context ctx) {
        try {
            @SuppressLint("WrongConstant") InputMethodManager inputManager = (InputMethodManager)ctx.getSystemService("input_method");
            View v = ((Activity)ctx).getCurrentFocus();
            if (v == null) {
                return;
            }

            inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception var3) {
        }

    }

    public static void showkeyBoard(Context ctx) {
        try {
            View view = ((Activity)ctx).getCurrentFocus();
            @SuppressLint("WrongConstant") InputMethodManager methodManager = (InputMethodManager)ctx.getSystemService("input_method");
            if (methodManager != null) {
                methodManager.showSoftInput(view, 1);
            }
        } catch (Exception var3) {
        }

    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }

        view.draw(canvas);
        return returnedBitmap;
    }

    static {
        PRIMARY_COLOR =  R.color.colorPrimary;
        ACCENT_COLOR =  R.color.colorAccent;
        PRIMARY_DARK_COLOR =  R.color.colorPrimaryDark;
    }
}
